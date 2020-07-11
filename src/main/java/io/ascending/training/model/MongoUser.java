package io.ascending.training.model;

public class MongoUser {
    private String id;
    private String name;
    private int age;

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

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "MongoUser [id=" + id + ", name=" + name + ", age=" + age + "]";
    }
}
