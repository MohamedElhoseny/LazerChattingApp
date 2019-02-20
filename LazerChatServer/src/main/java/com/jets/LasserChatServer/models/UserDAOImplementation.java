package com.jets.LasserChatServer.models;

import com.jets.LazerChatCommonService.models.entity.User;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.rowset.serial.SerialBlob;


public class UserDAOImplementation implements UserDAO
{
    private static UserDAOImplementation instance;
    private String dbName = "projdb";
    private String dbURL = "jdbc:mysql://localhost:3333/" + dbName;
    private String dbUsername = "root";
    private String dbPassword = "12345678";

    private Connection connection = null;
    private String query = null;
    private PreparedStatement statement=null;
    private ResultSet resultList = null;


    //Constructor
    private UserDAOImplementation() {
        try {
            new com.mysql.cj.jdbc.Driver();
            connection = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Getters and Setters
    public static UserDAOImplementation getInstance() {
        //Singleton Class
        if (instance == null) {
            instance = new UserDAOImplementation();
        }

        return instance;
    }

    @Override
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();
        String sql = "select * from User";

        try {

            statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            while (result.next()){
                User user  = new User ();
                user.setPhone(result.getString("phone"));
                user.setName(result.getString("name"));
                user.setPassword(result.getString("password"));
                user.setGender(result.getString("gender"));
                user.setBio(result.getString("bio"));
                Blob blob = result.getBlob("picture");
                byte [] image = blob.getBytes(1l, (int) blob.length());
                user.setPicture(image);
                user.setDate(result.getDate("birthdate").toString());
                user.setEmail(result.getString("email"));

                user.setCountry(result.getString("Country"));
                users.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return users;
    }

    @Override
    public void registerNewUser(User user) {
        query="insert into user values ( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? ) ";
        try {
            statement=connection.prepareStatement(query);

            statement.setString(2,user.getPhone());
            statement.setString(3,user.getPassword());
            statement.setString(4,user.getName());
            statement.setString(5,user.getEmail());
            statement.setBinaryStream(6,new ByteArrayInputStream(user.getPicture()));
            statement.setString(7,user.getGender());
            statement.setString(8,user.getCountry());
            statement.setString(9,user.getDate());
            statement.setString(10,user.getBio());
            statement.setInt(11,1);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error while registering new user : "+e.getMessage());

        }

    }
    //need Update
    @Override
    public void updateUser(User user) {

        String sql="UPDATE User SET Bio="+user.getBio()+" , Name="+user.getName()
                +" , Password="+user.getPassword()+" , Gender="+user.getGender()
                +" , Email="+user.getEmail()+" , Picture="+user.getPicture()
                +" , Date"+user.getDate()
                +" , Country="+user.getCountry()
                +" WHERE Phone= "+user.getPhone();
        try {
            statement = connection.prepareStatement(sql);
            statement.executeQuery();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }


    }

    @Override
    public User getUser(String phone) {
        User user=null;
        query="select * from user where phone="+phone;
        try {
            statement = connection.prepareStatement(query);
            resultList=statement.executeQuery();
            if (resultList!=null){
                resultList.first();
               user=new User();
                user.setId(resultList.getInt(1));
                System.out.println(user.getId());
                user.setPhone(resultList.getString(2));
                user.setPassword(resultList.getString(3));
                user.setName(resultList.getString(4));
                user.setEmail(resultList.getString(5));
                Blob blob= resultList.getBlob(6);
                user.setPicture(blob.getBytes(1,(int)blob.length()));
                user.setGender(resultList.getString(7));
                user.setCountry(resultList.getString(8));
                user.setDate(resultList.getString(9));
                user.setBio(resultList.getString(10));
            }
        }catch (SQLException ex){
            System.err.println("Error while get user : "+ex.getMessage());


        }
        return user;


    }
        //UnComplete
    @Override
    public List<User> getUserFriends(String phone) {

        List<User> Friends = new ArrayList<>();
        try {
            //query = "select * from  where ;
            statement = connection.prepareStatement(query);
            resultList=statement.executeQuery();

            while(resultList.next()){
                User user  = new User ();
                user.setPhone(resultList.getString("phone"));
                user.setName(resultList.getString("name"));
                user.setPassword(resultList.getString("password"));
                user.setGender(resultList.getString("gender"));
                user.setBio(resultList.getString("bio"));
                Blob blob = resultList.getBlob("picture");
                byte [] image = blob.getBytes(1l, (int) blob.length());
                user.setPicture(image);
                user.setDate(resultList.getDate("birthdate").toString());
                user.setEmail(resultList.getString("email"));

                user.setCountry(resultList.getString("Country"));
                Friends.add(user);
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return Friends;
    }



}