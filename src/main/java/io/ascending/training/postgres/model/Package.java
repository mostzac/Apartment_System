package io.ascending.training.postgres.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "packages")
public class Package {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "shipNumber")
    private String shipNumber;

    @Column(name = "shipper")
    private String shipper;

    @Column(name = "deliveredDate")
    private LocalDateTime deliveredDate;

    @Column(name = "description")
    private String description;

    @Column
    private int status;

    @Column
    private LocalDateTime arrangeDate;

    @Column
    private String notes;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public Package(String shipNumber, String shipper, LocalDateTime deliveredDate, String description, int status, LocalDateTime arrangeDate, String notes) {
        this.shipNumber = shipNumber;
        this.shipper = shipper;
        this.deliveredDate = deliveredDate;
        this.description = description;
        this.status = status;
        this.arrangeDate = arrangeDate;
        this.notes = notes;
    }

    public Package() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getShipNumber() {
        return shipNumber;
    }

    public void setShipNumber(String shipNumber) {
        this.shipNumber = shipNumber;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public LocalDateTime getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(LocalDateTime deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDateTime getArrangeDate() {
        return arrangeDate;
    }

    public void setArrangeDate(LocalDateTime arrangeDate) {
        this.arrangeDate = arrangeDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

//    @Override
//    public String toString() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        String str = null;
//        try {
//            str = objectMapper.writeValueAsString(this);
//        }
//        catch(JsonProcessingException jpe) {
//            jpe.printStackTrace();
//        }
//
//        return str;
//    }
}
