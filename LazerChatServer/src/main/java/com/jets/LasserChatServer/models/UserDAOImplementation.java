package com.jets.LasserChatServer.models;

import com.jets.LazerChatCommonService.models.entity.User;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author maimo
 */
public class UserDAOImplementation implements UserDAO {
    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void registerNewUser(User user) {

    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public User getUser(String phone) {
        return null;
    }

    @Override
    public User signIn(User user) {
        return null;
    }  // last update Arafa

}