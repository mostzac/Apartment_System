package io.ascending.training.jdbc;

import io.ascending.training.model.Package;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResidentDAO {

    static final String DB_URL = "jdbc:postgresql://localhost:5431/projectDB";
    static final String USER = "admin";
    static final String PASS = "password";
    private final  Logger logger = LoggerFactory.getLogger(getClass());


    public List<Package> getResident(){
        Connection conn;
        Statement stmt = null;
        ResultSet rs = null;
        List<Package> aPackages = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql =  "select * from resident";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String room = rs.getString("room");
                long apartment_id = rs.getLong("apartment_id");

                Package aPackage = new Package();
                aPackage.setId(id);
                aPackage.setName(name);
                aPackage.setRoom(room);
                aPackage.setApartment_id(apartment_id);
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
            String sql = "insert into resident (name,room,apartment_id) values" + "('"+ aPackage.getName()+"','"+ aPackage.getRoom()+"','"+ aPackage.getApartment_id()+"')";
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

    public boolean delete (String residentname){
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
            String sql = "delete from resident where name = " + "'"+residentname+"'";
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
