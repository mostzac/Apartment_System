package io.ascending.training.jdbc;

import io.ascending.training.model.Package;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PackageDAO {

    static final String DB_URL = "jdbc:postgresql://localhost:5431/projectDB";
    static final String USER = "admin";
    static final String PASS = "password";
    private final  Logger logger = LoggerFactory.getLogger(getClass());


    public List<Package> getPackage(){
        Connection conn;
        Statement stmt = null;
        ResultSet rs = null;
        List<Package> aPackages = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql =  "select * from packages";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                long id = rs.getLong("id");
                String shipNumber = rs.getString("shipNumber");
                String shipper = rs.getString("shipper");
                LocalDateTime deliveredDate = rs.getObject(4,LocalDateTime.class);
                String description = rs.getString("description");
                int status = rs.getInt("status");
                LocalDateTime arrangeDate = rs.getObject(7,LocalDateTime.class);
                long userId = rs.getLong("userId");
                String notes = rs.getString("notes");

                Package aPackage = new Package();
                aPackage.setId(id);
                aPackage.setShipNumber(shipNumber);
                aPackage.setShipper(shipper);
                aPackage.setDeliveredDate(deliveredDate);
                aPackage.setDescription(description);
                aPackage.setStatus(status);
                aPackage.setArrangeDate(arrangeDate);
                aPackage.setNotes(notes);
                aPackages.add(aPackage);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aPackages;
    }

    public boolean save(Package aPackage){
        Connection conn = null;
        Statement stmt = null;
        try {
            System.out.println("Connecting to database......");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            System.out.println("Creating statement");
            stmt = conn.createStatement();
            String sql = "insert into packages (shipNumber,shipper,deliveredDate,description,status,arrangeDate,notes,userId) values" +
                    "('"+ aPackage.getShipNumber()+"','"+ aPackage.getShipper()+"','"+ aPackage.getDeliveredDate()+"','"+aPackage.getDescription()+"','"+aPackage.getStatus()+"','"+aPackage.getArrangeDate()+"','"+aPackage.getArrangeDate()+"','"+aPackage.getNotes()+"',1)";
            int i = stmt.executeUpdate(sql);
            if(i==1){
                System.out.println("creating a new record");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(stmt!=null) stmt.close();
                if(conn!=null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    public boolean delete (Package pack){
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
            String sql = "delete from packages where shipNumber = " + "'"+pack.getShipNumber()+"'";
            int i = stmt.executeUpdate(sql);
            if(i==1){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if(stmt!=null) stmt.close();
                if(conn!=null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return false;
    }


}
