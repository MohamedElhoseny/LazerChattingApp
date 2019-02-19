package com.jets.LasserChatServer.models;

import com.jets.LazerChatCommonService.models.entity.User;

import java.util.List;


public interface UserDAO
{
    public List<User> getAllUsers();
    public void registerNewUser (User user);
    public void updateUser (User user);
    public User getUser(String phone);
    public User signIn(User user);
}