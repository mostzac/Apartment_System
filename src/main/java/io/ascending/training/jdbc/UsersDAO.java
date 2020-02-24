package io.ascending.training.jdbc;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import io.ascending.training.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsersDAO {

    static final String DB_URL = "jdbc:postgresql://localhost:5431/projectDB";
    static final String USER = "admin";
    static final String PASS = "password";
    private final  Logger logger = LoggerFactory.getLogger(getClass());


    public List<User> getUsers(){
        Connection conn;
        Statement stmt = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql =  "select * from users";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                long id = rs.getLong("id");
                String account = rs.getString("account");
                String password = rs.getString("password");
                String name = rs.getString("name");
                String room = rs.getString("room");
                long apartmentId = rs.getLong("apartmentId");

                User user = new User();
                user.setId(id);
                user.setAccount(account);
                user.setPassword(password);
                user.setName(name);
                user.setRoom(room);
                users.add(user);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean save(User user){
        Connection conn = null;
        Statement stmt = null;
        try {
            System.out.println("Connecting to database......");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            System.out.println("Creating statement");
            stmt = conn.createStatement();
            String sql = "insert into users (account, password, name,room,apartmentId) values" +
                    "('"+user.getAccount()+"','"+user.getPassword()+"','"+user.getName()+"','"+user.getRoom()+"','"+user.getApartment().getId()+"')";
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

    public boolean delete (String useraccount){
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
            String sql = "delete from users where account = " + "'"+useraccount+"'";
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
