package io.ascending.training.jdbc;

import io.ascending.training.postgres.model.Apartment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApartmentDAO {

    static final String DB_URL = System.getProperty("database.url");
    static final String USER = System.getProperty("database.user");
    static final String PASS = System.getProperty("database.password");
    private final  Logger logger = LoggerFactory.getLogger(getClass());


    public List<Apartment> gerApartment(){
        Connection conn;
        Statement stmt = null;
        ResultSet rs = null;
        List<Apartment> apartments = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql =  "select * from apartments";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String address = rs.getString("address");

                Apartment apartment = new Apartment();
                apartment.setId(id);
                apartment.setName(name);
                apartment.setAddress(address);
                apartments.add(apartment);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return apartments;
    }

    public boolean save(Apartment apartment){
        Connection conn = null;
        Statement stmt = null;
        try {
            System.out.println("Connecting to database......");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            System.out.println("Creating statement");
            stmt = conn.createStatement();
            String sql = "insert into apartments (name,address) values" + "('"+apartment.getName()+"','"+apartment.getAddress()+"')";
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

    public boolean delete (String apartmentname){
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
            String sql = "delete from apartments where name = " + "'"+apartmentname+"'";
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
