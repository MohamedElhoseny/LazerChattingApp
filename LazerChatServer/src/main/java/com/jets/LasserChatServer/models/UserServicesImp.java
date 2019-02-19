package com.jets.LasserChatServer.models;

import com.jets.LazerChatCommonService.models.dao.UserServices;
import com.jets.LazerChatCommonService.models.entity.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserServicesImp extends UnicastRemoteObject implements UserServices
{
    private MysqlDB mysqlDB;

    public UserServicesImp() throws RemoteException {
        mysqlDB = MysqlDB.getInstance();
    }

    @Override
    public User logIn(String phone, String password)
    {
        String whereStatement = "phone = '"+phone+"' AND password = '"+password+"'";

        /* Phone must be unique in database ^_^ */
        ArrayList<Object[]> record = null;
        User loginUser = null;

        try {
            record = mysqlDB.select("user", "*", whereStatement);
            if (record.size() > 0)
            {
                //1. Update status

                //2. Read user info
                loginUser = new User();
                loginUser.setId((Integer) record.get(0)[0]);
                loginUser.setPhone((String) record.get(0)[1]);
                loginUser.setPassword((String) record.get(0)[2]);
                loginUser.setName((String) record.get(0)[3]);
                loginUser.setEmail((String) record.get(0)[4]);
                loginUser.setPicture((byte[]) record.get(0)[5]);
                loginUser.setGender((Integer) record.get(0)[6]);
                loginUser.setCountry((String) record.get(0)[7]);
                loginUser.setDate((String) record.get(0)[8]);
                loginUser.setBio((String) record.get(0)[9]);
                loginUser.setStatus((String) record.get(0)[10]);   //return int 'must join to get string value'
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error while user login (phone = "+phone
                                + ", password = "+password+ ") : "+ e.getMessage());
        }

        return loginUser;
    }

    @Override
    public void logOut(User user) {

    }

    @Override
    public boolean register(User user) {
        String attributes = "id, phone, password, name, email, picture, gender, country, birthdate, bio";
        try {
            //Blob userImage = new SerialBlob(user.getPicture());
            String values = user.getId() + "," + "'"+user.getPhone()+"'," + "'"+user.getPassword()+"',"
                    + "'"+user.getName()+"',"+ "'"+user.getEmail()+"'," + user.getPicture() + ","+ user.getGender()
                    + ",'"+user.getCountry()+"',"+ user.getDate()+","+ "'"+user.getBio()+"'";
            mysqlDB.insert("user",attributes, values);
            return true;
        } catch (SQLException e) {
            System.err.println("Error while registering new user : "+e.getMessage());
            return false;
        }
    }

    @Override
    public boolean forgetPassword(String s) {
        return false;
    }

    @Override
    public boolean updateProfile(User user) {
        return false;
    }

    @Override
    public User socialLogin(SocialType socialType) {
        return null;
    }

    @Override
    public void exit(User user) {

    }
}
