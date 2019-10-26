package io.ascending.training.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @Column
    private String account;
    @Column
    private String password;
    @Column
    private String name;
    @Column
    private String room;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "apartmentId")
    private Apartment apartment;

//    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE,fetch = FetchType.LAZY)
    private Set<Package> packages;


    public User(String account, String password,String name,String room) {
        this.account = account;
        this.password = password;
        this.name = name;
        this.room = room;
    }

    public User() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public Set<Package> getPackages() {
        try {
            int size = packages.size();
        }
        catch (Exception e){
            return  null;
        }
        return packages;
    }

    public void setPackages(Set<Package> packages) {
        this.packages = packages;
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        String str = null;
        try {
            str = objectMapper.writeValueAsString(this);
        }
        catch(JsonProcessingException jpe) {
            jpe.printStackTrace();
        }

        return str;
    }
}
