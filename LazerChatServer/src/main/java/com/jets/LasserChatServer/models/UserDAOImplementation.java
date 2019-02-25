package com.jets.LasserChatServer.models;

import com.jets.LazerChatCommonService.models.entity.User;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDAOImplementation implements UserDAO {
    private static UserDAOImplementation instance;
    private String dbName = "projdb";
    private String dbURL = "jdbc:mysql://localhost:3306/" + dbName;
    private String dbUsername = "root";
    private String dbPassword = "12131234";

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

        String query = "update user set password= ? ,name= ? ,email= ? ,picture= ? ," + "gender= ? ,country= ? ,birthdate= ?,bio= ? " + "where phone= ? ;";
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

            query = "select u.* from user u " + " join groupcontact g " + " on u.id=g.uid " + " or u.id=g.rid " + " join user f " + " on (f.id=g.uid and f.id <> u.id) " + " or (f.id=g.rid and f.id <> u.id) " + " where f.phone= '" + phone + "';";

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
        query = "update user set sid= ? " + " where phone= ? ;";

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

    @Override
    public boolean isExist(User user) {

        boolean isExist = false;
        List<User> users = getAllUsers();
        for (User user1 : users) {
            if (user1.getPhone().equals(user.getPhone())) {
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    @Override
    public boolean addFriend(User user, User newFriend) {
        User user1 = getUser(user.getPhone());
        User user2 = getUser(newFriend.getPhone());
        int check = 0;
        query = "insert into groupcontact( uid , rid ) values( ? , ? )";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, user1.getId());
            statement.setInt(2, user2.getId());

            check = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (check == 1) {
            deletFriendRequest(user1, user2);
            deletFriendRequest(user2, user1);
            return true;
        } else return false;

    }

    @Override
    public boolean isFriend(User user1, User user2) {
        boolean isFriend = false;
        User fristUser = getUser(user1.getPhone());
        User secondUser = getUser(user2.getPhone());

        query = "select uid,rid from groupcontact";
        try {
            statement = connection.prepareStatement(query);
            resultList = statement.executeQuery();
            while (resultList.next()) {
                int uid = resultList.getInt("uid");
                int rid = resultList.getInt("rid");
                if ((uid == fristUser.getId() && rid == secondUser.getId()) || (uid == secondUser.getId() && rid == fristUser.getId())) {
                    isFriend = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isFriend;

    }

    @Override
    public boolean addFriendRequest(User fromUser, User toUser) {
        User secondUser = getUser(toUser.getPhone());
        int check = 0;
        query = "insert into friendrequest( fromUser , toUser , state ) values( ? , ? , ? )";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, fromUser.getId());
            statement.setInt(2, secondUser.getId());
            statement.setInt(3, 1);

            check = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (check == 1) {

            return true;
        } else return false;
    }

    @Override
    public boolean isPending(User fromUser, User toUser) {
        boolean ispending = false;
        User fristUser = getUser(fromUser.getPhone());
        User secondUser = getUser(toUser.getPhone());
        query = "select * from friendrequest";
        try {
            statement = connection.prepareStatement(query);
            resultList = statement.executeQuery();
            while (resultList.next()) {
                int id1 = resultList.getInt("fromUser");
                int id2 = resultList.getInt("toUser");
                int state = resultList.getInt("state");
                if ((fristUser.getId() == id1 && secondUser.getId() == id2)) {
                    if (state == 1) {
                        ispending = true;
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ispending;
    }

    private void deletFriendRequest(User fromuser, User touser) {
        User fristUser = getUser(fromuser.getPhone());
        User secondUser = getUser(touser.getPhone());
        query = "delete from friendrequest where fromUser= ? and toUser= ?";
        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, fristUser.getId());
            statement.setInt(2, secondUser.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}