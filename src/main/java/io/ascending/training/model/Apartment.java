package io.ascending.training.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "apartments")
public class Apartment {
    public interface ApartmentView {
    }

    public interface ApartmentUsersView extends ApartmentView {
    }

    @JsonView(ApartmentView.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @JsonView(ApartmentView.class)
    @Column(name = "name")
    private String name;

    @JsonView(ApartmentView.class)
    @Column(name = "address")
    private String address;

    //    @JsonIgnore
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonView(ApartmentUsersView.class)
    @OneToMany(mappedBy = "apartment", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<User> users;


    public Apartment(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Apartment() {

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

//    @Override
//    public String toString() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        String str = null;
//        try {
//            str = objectMapper.writeValueAsString(this);
//        } catch (JsonProcessingException jpe) {
//            jpe.printStackTrace();
//        }
//
//        return str;
//    }
//
//    public Set<User> getUsers() {
//        try {
//            int size = users.size();//try catch the exception if the residents are not fetched
//        }
//        catch (Exception e){
//            return null;
//        }
//        return users;
//    }


    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
