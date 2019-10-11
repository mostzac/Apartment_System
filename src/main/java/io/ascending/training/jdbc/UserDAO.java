package io.ascending.training.jdbc;

import io.ascending.training.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDAO {

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
            sql =  "select * from user";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                long resident_id = rs.getLong("resident_id");

                User user = new User();
                user.setId(id);
                user.setName(name);
                user.setPassword(password);
                user.setResident_id(resident_id);
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
            String sql = "insert into user (name,password,resident_id) values" + "('"+user.getName()+"','"+user.getPassword()+"','"+user.getResident_id()+"')";
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

    public boolean delete (String username){
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
            String sql = "delete from user where name = " + "'"+username+"'";
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
