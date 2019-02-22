package com.jets.LasserChatServer.models;

import com.jets.LazerChatCommonService.models.entity.User;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDAOImplementation implements UserDAO {
    private static UserDAOImplementation instance;
    private String dbName = "projdb";
    private String dbURL = "jdbc:mysql://localhost:3333/" + dbName;
    private String dbUsername = "root";
    private String dbPassword = "12345678";

    private Connection connection = null;
    private String query = null;
    private PreparedStatement statement = null;
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
        String query = "select * from user";

        try {

            statement = connection.prepareStatement(query);
            resultList = statement.executeQuery();
            while (resultList.next()) {
                User user = new User();
                user.setId(resultList.getInt("id"));
                user.setPhone(resultList.getString("phone"));
                user.setName(resultList.getString("name"));
                user.setPassword(resultList.getString("password"));
                user.setGender(resultList.getString("gender"));
                user.setBio(resultList.getString("bio"));
                Blob blob = resultList.getBlob("picture");
                byte[] image = blob.getBytes(1, (int) blob.length());
                user.setPicture(image);
                user.setDate(resultList.getDate("birthdate").toString());
                user.setEmail(resultList.getString("email"));
                user.setCountry(resultList.getString("Country"));

                users.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return users;
    }

    @Override
    public void registerNewUser(User user) {
        query = "insert into user(phone,password,name,email,picture,gender,country,birthdate,bio) values ( ? , ? , ? , ? , ? , ? , ? , ? , ? )";
        try {
            statement = connection.prepareStatement(query);


            statement.setString(1, user.getPhone());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getEmail());
            statement.setBinaryStream(5, new ByteArrayInputStream(user.getPicture()));
            statement.setString(6, user.getGender());
            statement.setString(7, user.getCountry());
            statement.setString(8, user.getDate());
            statement.setString(9, user.getBio());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error while registering new user : " + e.getMessage());


        }

    }


    @Override
    public void updateUser(User user) {

        String query = "update user set password= ? ,name= ? ,email= ? ,picture= ? ,"
                + "gender= ? ,country= ? ,birthdate= ?,bio= ? "
                + "where phone= ? ;";
        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getName());
            statement.setString(3, user.getEmail());
            statement.setBinaryStream(4, new ByteArrayInputStream(user.getPicture()));
            statement.setString(5, user.getGender());
            statement.setString(6, user.getCountry());
            statement.setString(7, user.getDate());
            statement.setString(8, user.getBio());
            statement.setString(9, user.getPhone());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public User getUser(String phone) {
        User user = null;
        query = "select * from user where phone=" + phone;
        try {
            statement = connection.prepareStatement(query);
            resultList = statement.executeQuery();
            if (resultList != null) {
                resultList.first();
                user = new User();
                user.setId(resultList.getInt(1));
                System.out.println(user.getId());
                user.setPhone(resultList.getString(2));
                user.setPassword(resultList.getString(3));
                user.setName(resultList.getString(4));
                user.setEmail(resultList.getString(5));
                Blob blob = resultList.getBlob(6);
                user.setPicture(blob.getBytes(1, (int) blob.length()));
                user.setGender(resultList.getString(7));
                user.setCountry(resultList.getString(8));
                user.setDate(resultList.getString(9));
                user.setBio(resultList.getString(10));
            }
        } catch (SQLException ex) {
            System.err.println("Error while get user : " + ex.getMessage());


        }
        return user;


    }


    @Override
    public List<User> getUserFriends(String phone) {

        List<User> friends = new ArrayList<>();
        try {

            query = "select u.* from user u "
                    + " join groupcontact g "
                    + " on u.id=g.uid "
                    + " or u.id=g.rid "
                    + " join user f "
                    + " on (f.id=g.uid and f.id <> u.id) "
                    + " or (f.id=g.rid and f.id <> u.id) "
                    + " where f.phone= '" + phone + "';";

            statement = connection.prepareStatement(query);
            resultList = statement.executeQuery();

            while (resultList.next()) {
                User user = new User();
                user.setId(resultList.getInt("id"));
                user.setPhone(resultList.getString("phone"));
                user.setName(resultList.getString("name"));
                user.setPassword(resultList.getString("password"));
                user.setGender(resultList.getString("gender"));
                user.setBio(resultList.getString("bio"));
                Blob blob = resultList.getBlob("picture");
                byte[] image = blob.getBytes(1, (int) blob.length());
                user.setPicture(image);
                user.setDate(resultList.getDate("birthdate").toString());
                user.setEmail(resultList.getString("email"));
                user.setCountry(resultList.getString("Country"));
                user.setStatus(resultList.getInt("sid"));
                friends.add(user);

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return friends;
    }

    @Override
    public boolean updateUserStatues(User user) {
        boolean isUpdated = false;
        query = "update user set sid= ? "
                + " where phone= ? ;";

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, user.getStatus());
            statement.setString(2, user.getPhone());

            int check = statement.executeUpdate();
            if (check != 0) {
                isUpdated = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUpdated;

    }


}