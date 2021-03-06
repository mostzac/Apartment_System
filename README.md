# Apartment_System
#### Overview
* Project Intention:

People shop online and packages are delivered everyday. Apartment will get massive deliveries everyday and residences will have to pick them up in person at the front desk.   
In order to provide better service for the residence, apartment can deliver residences’ packages to door if the requests are made.   
Apartment package deliver-to-door system helps apartment deliver the  packages to residences.
* Project Technical Overview:

The system is running in Linux system, 

This system is developed in Spring Framework by using Spring Boot, Hibernate, Spring RESTful Web Services, JWT, Mockito, Postman, Maven, Postgres, Docker, Web Socket, Amazon SQS and Amazon S3.
* Project Business Rules:

1. Object: User, Apartment, Package, Role
2. Relationships: 
   1. One apartment could have many users.
   1. One user could have many packages.
   1. One user could have many Roles.
3. Project Approach:
   1. Created User, Apartment, Package, Role models.
   1. Used Hibernate for Object-Relational-Mapping(ORM) that maps the object to the data stored in the database.
   1. Used Postgres Database and Maven Flyway for database schema migration.
   1. Used JWT for authentication.
   1. Implemented repository, service and did unit tests.
   1. Implement controllers and REST APIs.
   1. Integrated with AWS S3 and SQS services and did unit tests using Mockito.
   1. Used Postman to test APIs.
   1. Build Docker images and deploy the project.
#### Postgres docker configuration
```
# Install postgres and start service with configuration
docker pull postgres

docker run --name ${PostgresContainerName} -e POSTGRES_USER=${username} -e POSTGRES_PASSWORD=${password} -e POSTGRES_DB=${databaseName} -p ${hostport}:${containerport} -d postgres
```
#### Project Deployment
##### Environment Configuration
```
#For IntelliJ IDEA using the JVM Option

Template:
-Ddatabase.driver=org.postgresql.Driver
-Ddatabase.dialect=org.hibernate.dialect.PostgreSQL9Dialect
-Ddatabase.url=jdbc:postgresql://${DB_URL}:${port}/${DB_NAME}
-Ddatabase.user=${DB_USER}
-Ddatabase.password=${DB_PASSWORD}
-Daws.accessKeyId=${AWS_ID}
-Daws.secretKey=${AWS_KEY}
-Daws.region=${AWS_REGION}
-Daws.s3.bucket=${AWS_S3_NAME}
-Daws.sqs.name=${AWS_SQS_NAME}
-Dlogging.level.org.springframework=INFO"
```
* Local Tomcat in Ubuntu:
     
```
# 1. Set Tomcat local environment configuration in setenv.sh
location: /usr/share/tomcat9/bin
-----
Template:
export CATALINA_OPTS="$CATALINA_OPTS -Dlogging.level.root=DEBUG"
export CATALINA_OPTS="$CATALINA_OPTS -Ddatabase.driver=org.postgresql.Driver"
export CATALINA_OPTS="$CATALINA_OPTS -Ddatabase.dialect=org.hibernate.dialect.PostgreSQL9Dialect"
export CATALINA_OPTS="$CATALINA_OPTS -Ddatabase.url=jdbc:postgresql://${DB_URL}:5432/${DB_NAME}"
export CATALINA_OPTS="$CATALINA_OPTS -Ddatabase.user=${DB_USER}"
export CATALINA_OPTS="$CATALINA_OPTS -Ddatabase.password=${DB_PASSWORD}"
export CATALINA_OPTS="$CATALINA_OPTS -Daws.accessKeyId=${AWS_ID}"
export CATALINA_OPTS="$CATALINA_OPTS -Daws.secretKey=${AWS_KEY}"
export CATALINA_OPTS="$CATALINA_OPTS -Daws.region=${AWS_REGION}"
export CATALINA_OPTS="$CATALINA_OPTS -Daws.s3.bucket=${AWS_S3_NAME}"
export CATALINA_OPTS="$CATALINA_OPTS -Daws.sqs.name=${AWS_SQS_NAME}"
export CATALINA_OPTS="$CATALINA_OPTS -Dlogging.level.org.springframework=INFO"

# 2. Generate .war file and deploy it onto the Tomcat(location: /var/lib/tomcat9/webapps)
-----
mvn clean compile package -DskipTests=true

sudo rm -rf ROOT
sudo cp {filename}.war /var/lib/tomcat9/ROOT.war

# 3. Server start
-----
sudo service tomcat9 start
```
* Production deployment in Docker: Files needed: {filename}.war, Dockerfile, setenv.sh, recommend all files in one folder
```
# 1. Tomcat confuguration；
-----
export JAVA_OPTS="$JAVA_OPTS -Dlogging.level.root=DEBUG"
export JAVA_OPTS="$JAVA_OPTS -Ddatabase.driver=org.postgresql.Driver"
export JAVA_OPTS="$JAVA_OPTS -Ddatabase.dialect=org.hibernate.dialect.PostgreSQL9Dialect"
export JAVA_OPTS="$JAVA_OPTS -Ddatabase.url=jdbc:postgresql://${DB_URL}:5432/${DB_NAME}"
export JAVA_OPTS="$JAVA_OPTS -Ddatabase.user=${DB_USER}"
export JAVA_OPTS="$JAVA_OPTS -Ddatabase.password=${DB_PASSWORD}"
export JAVA_OPTS="$JAVA_OPTS -Daws.accessKeyId=${AWS_ID}"
export JAVA_OPTS="$JAVA_OPTS -Daws.secretKey=${AWS_KEY}"
export JAVA_OPTS="$JAVA_OPTS -Daws.region=${AWS_REGION}"
export JAVA_OPTS="$JAVA_OPTS -Daws.s3.bucket=${AWS_S3_NAME}"
export JAVA_OPTS="$JAVA_OPTS -Daws.sqs.name=${AWS_SQS_NAME}"
export JAVA_OPTS="$JAVA_OPTS -Dlogging.level.org.springframework=INFO"

# 2. Build docker image and start the container
-----
docker build -t {imageName}:{Tag} .

docker run -p {localPort}:{containerPort} -e DB_URL=${} -e DB_NAME=${} -e DB_USER=${} -e DB_PASSWORD=${} -e AWS_ID=${} -e AWS_KEY=${} {imageName}:{Tag}
```
#### Flyway Migration
```
mvn compile flyway:migrate -Ddatabase.url=jdbc:postgresql://${DB_URL}:5432/${DB_NAME} -Ddatabase.user=${DB_USER} -Ddatabase.password=${DB_PASSWORD}
```
#### Testing
```
mvn test -Ddatabase.url=jdbc:postgresql://${DB_URL}:5432/${DB_NAME} -Ddatabase.user=${DB_USER} -Ddatabase.password=${DB_PASSWORD} 
-Daws.s3.bucket=${} -Daws.sqs.name=${} -Daws.region=${} 
-Ddatabase.dialect=org.hibernate.dialect.PostgreSQL9Dialect -Ddatabase.driver=org.postgresql.Driver
```
#### Demo
##### User sign up
```
POST - http://localhost:8080/api/users/user?aptName={aptName}
```
Request Body
```
 { 
 	"account": "test",
 	"password": "0",
 	"name": "test",
 	"room": "11"
 }
```
Postman snapshot:
![](https://github.com/mostzac/Apartment_System/blob/master/READMESnapshot/addUser.png)
##### Authentication
```
POST - http://localhost:8080/api/auth
```
Request Body
```
{
	"account": "test",
	"password": "0"
}
```
Response Body
```
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMyIsImlhdCI6MTU3Njk1OTIwOCwiaXNzIjoiaW8uYXNjZW5kaW5nIiwiZXhwIjoxNTc3MDQ1NjA4LCJhbGxvd2VkUmVhZFJlc291cmNlcyI6IiIsImFsbG93ZWRDcmVhdGVSZXNvdXJjZXMiOiIiLCJhbGxvd2VkVXBkYXRlUmVzb3VyY2VzIjoiIiwiYWxsb3dlZERlbGV0ZVJlc291cmNlcyI6IiJ9.rm3XN5JeRdqec7CbB5f2g41nAuppxr9VyqOu1SzSJA4"
}
```
Postman snapshot:
![](https://github.com/mostzac/Apartment_System/blob/master/READMESnapshot/auth.png)
##### Get users
```
Get - http://localhost:8080/api/users/
```
Postman snapshot:
![](https://github.com/mostzac/Apartment_System/blob/master/READMESnapshot/getUsers.png)


   

