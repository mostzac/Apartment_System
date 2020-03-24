FROM tomcat:9.0.30-jdk13-openjdk-oracle

EXPOSE 8080

ENV DB_URL=""
ENV DB_NAME=""
ENV DB_USER=""
ENV DB_PASSWORD=""
ENV AWS_ID=""
ENV AWS_KEY=""


RUN rm -rf /usr/local/tomcat/webapps/ROOT
COPY ./*.war /usr/local/tomcat/webapps/ROOT.war
COPY ./setenv.sh /usr/local/tomcat/bin/setenv.sh
