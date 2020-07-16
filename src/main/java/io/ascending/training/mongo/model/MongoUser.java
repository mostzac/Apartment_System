package io.ascending.training.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class MongoUser {
    @Id
    private String id;
    private String name;
    private int age;
    @DBRef
    private MongoMessage message;


    public MongoMessage getMessage() {
        return message;
    }

    public void setMessage(MongoMessage message) {
        this.message = message;
    }

    public MongoUser(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "MongoUser [id=" + id + ", name=" + name + ", age=" + age + "]";
    }
}
