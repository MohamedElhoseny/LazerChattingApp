package com.jets.LazerChatCommonService.models.dao;

import com.jets.LazerChatCommonService.models.entity.User;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.rowset.serial.SerialBlob;

/**
 *
 * @author maimo
 */
public class UserDAOImplementation implements UserDAO
{
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