package com.jets.LasserChatServer.models;

import com.jets.LazerChatCommonService.models.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public interface UserDAO {

    public List<User> getAllUsers();

    public void registerNewUser(User user);

    public void updateUser(User user);

    public User getUser(String phone);

    public List<User> getUserFriends(String phone);

    public boolean updateUserStatues(User user);

    public boolean isExist(User user);

    public boolean addFriend(User user, User newFriend);

    public boolean isFriend(User user1, User user2);

    public boolean addFriendRequest(User fromUser, User toUser);

    public boolean isPending(User fromUser, User toUser);

    public ArrayList<User> getFriendRequests(User user);

    public void deleteFriendRequest(User fromuser, User touser);

    public List<User> getOnlineUsers();

    public List<User> getOfflineUsers();

    public Map<String, Integer> getUsersCountry();
}