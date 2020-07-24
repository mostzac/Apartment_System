package io.ascending.training.postgres.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    @JsonView(Apartment.ApartmentUsersView.class)
    private long id;
    @JsonView(Apartment.ApartmentUsersView.class)
    @Column
    private String account;
    //    @JsonView(Apartment.ApartmentUsersView.class)
    @Column
    private String password;
    @JsonView(Apartment.ApartmentUsersView.class)
    @Column
    private String name;
    @JsonView(Apartment.ApartmentUsersView.class)
    @Column
    private String room;

    //    @JsonIgnore
    @JsonBackReference
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartmentId")
    private Apartment apartment;

    //    @JsonIgnore
//    @JsonManagedReference
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Package> packages;

    @JsonIgnore
//    @JsonView(Apartment.ApartmentUsersView.class)
//    @JsonBackReference
//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles;


    public User(String account, String password, String name, String room) {
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
//        this.password = DigestUtils.md5Hex(password.trim());
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
//        try {
//            int size = packages.size();
//        } catch (Exception e) {
//            return null;
//        }
        return packages;
    }

    public void setPackages(Set<Package> packages) {
        this.packages = packages;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        String str = null;
        try {
            str = objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException jpe) {
            jpe.printStackTrace();
        }

        return str;
    }


}
