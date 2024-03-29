package com.jets.LasserChatServer.models;

import com.jets.LazerChatCommonService.models.dao.UserServices;
import com.jets.LazerChatCommonService.models.entity.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserServicesImp extends UnicastRemoteObject implements UserServices {
    private UserDAO userDAO;

    public UserServicesImp() throws RemoteException {
        userDAO = UserDAOImplementation.getInstance();
    }

    @Override
    public User logIn(String phone, String password)
    {
        User loginUser = userDAO.getUser(phone);  //read user as in offline state

        if ((loginUser != null) && loginUser.getPassword().equals(password))
        {
            loginUser.setStatus(1);  //set online state
            userDAO.updateUserStatues(loginUser);
            return loginUser;
        }
        else
            return null;
    }

    @Override
    public void logOut(User user) {
        user.setStatus(4);
        userDAO.updateUserStatues(user);
    }

    @Override
    public boolean register(User user) {

        userDAO.registerNewUser(user);
        return true;

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
