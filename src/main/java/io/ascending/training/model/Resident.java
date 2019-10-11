package io.ascending.training.model;

public class Resident {
    private long id;
    private String name;
    private String room;
    private long apartment_id;

    public Resident(String name, String room, long apartment_id) {
        this.name = name;
        this.room = room;
        this.apartment_id = apartment_id;
    }

    public Resident() {

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

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public long getApartment_id() {
        return apartment_id;
    }

    public void setApartment_id(long apartment_id) {
        this.apartment_id = apartment_id;
    }
}
