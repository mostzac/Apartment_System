package io.ascending.training.postgres.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class Role {
    @JsonView(Apartment.ApartmentUsersView.class)
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @JsonView(Apartment.ApartmentUsersView.class)
    @Column(name = "name")
    private String name;

    @JsonView(Apartment.ApartmentUsersView.class)
    @Column(name = "allowed_resource")
    private String allowedResource;

    @JsonView(Apartment.ApartmentUsersView.class)
    @Column(name = "allowed_read")
    private boolean allowedRead;

    @JsonView(Apartment.ApartmentUsersView.class)
    @Column(name = "allowed_create")
    private boolean allowedCreate;

    @JsonView(Apartment.ApartmentUsersView.class)
    @Column(name = "allowed_update")
    private boolean allowedUpdate;

    @JsonView(Apartment.ApartmentUsersView.class)
    @Column(name = "allowed_delete")
    private boolean allowedDelete;


    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public Role(String name, String allowedResource, boolean allowedRead, boolean allowedCreate, boolean allowedUpdate, boolean allowedDelete) {
        this.name = name;
        this.allowedResource = allowedResource;
        this.allowedRead = allowedRead;
        this.allowedCreate = allowedCreate;
        this.allowedUpdate = allowedUpdate;
        this.allowedDelete = allowedDelete;
    }

    public Role(){}

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

    public String getAllowedResource() {
        return allowedResource;
    }

    public void setAllowedResource(String allowedResource) {
        this.allowedResource = allowedResource;
    }

    public boolean isAllowedRead() {
        return allowedRead;
    }

    public void setAllowedRead(boolean allowedRead) {
        this.allowedRead = allowedRead;
    }

    public boolean isAllowedCreate() {
        return allowedCreate;
    }

    public void setAllowedCreate(boolean allowedCreate) {
        this.allowedCreate = allowedCreate;
    }

    public boolean isAllowedUpdate() {
        return allowedUpdate;
    }

    public void setAllowedUpdate(boolean allowedUpdate) {
        this.allowedUpdate = allowedUpdate;
    }

    public boolean isAllowedDelete() {
        return allowedDelete;
    }

    public void setAllowedDelete(boolean allowedDelete) {
        this.allowedDelete = allowedDelete;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
