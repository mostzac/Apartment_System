package io.ascending.training.mongo.model;

import io.ascending.training.mongo.config.annotaion.CascadeSave;
import io.ascending.training.mongo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@Document(collection = "users")
@CompoundIndexes({
        @CompoundIndex(name = "name_age", def = "{'name' : 1, 'age': 1}", unique = true)
})
public class MongoUser implements Serializable {
    @Id
    private String id;
    //    @Indexed(unique = true)
    private String name;
    private int age;
    @DBRef
    @CascadeSave
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

    public void setName(String name) {
        this.name = name;
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
