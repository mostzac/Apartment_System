package io.ascending.training.model;

import java.util.ArrayList;
import java.util.List;

public class Apartment {
    private long id;
    private String name;
    private String address;
    private List<Resident> residentList = new ArrayList<>();

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

    public List<Resident> getResidentList() {
        return residentList;
    }

    public void setResidentList(List<Resident> residentList) {
        this.residentList = residentList;
    }
}
