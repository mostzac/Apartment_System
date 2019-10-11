package io.ascending.training.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    private long resident_id;

    public User(String name, String password,long resident_id) {
        this.name = name;
        this.password = password;
        this.resident_id = resident_id;
    }

    public User() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getResident_id() {
        return resident_id;
    }

    public void setResident_id(long resident_id) {
        this.resident_id = resident_id;
    }
}
