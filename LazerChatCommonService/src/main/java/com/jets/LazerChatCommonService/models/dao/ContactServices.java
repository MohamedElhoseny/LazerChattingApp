package com.jets.LazerChatCommonService.models.dao;

import com.jets.LazerChatCommonService.models.entity.GroupCategory;
import com.jets.LazerChatCommonService.models.entity.User;
import javafx.collections.ObservableList;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface ContactServices extends Remote {
    /* Server side */
    boolean addContact(User user, User newContact) throws RemoteException;

    /* Server side */
    boolean isFriend(User user1, User user2) throws RemoteException;

    /* Server side */
    boolean isPending(User fromUser, User toUser) throws RemoteException;

    /* Server side */
    boolean addFriendRequest(User fromUser, User toUser) throws RemoteException;

    /* Server side */
    boolean checkIfUserExist(User user) throws RemoteException;

    /* Server side */
    boolean blockContact(User blockContact) throws RemoteException;

    /* Server side */
    void AddContactToGroup(User Contact, GroupCategory groupType) throws RemoteException;

    /* Server side */
    ArrayList<User> getFriendRequests(User user) throws RemoteException;

    /* Server side */
    void deleteFriendRequest(User fromuser, User touser) throws RemoteException;

    List<User> getAllUserFriendList(String phone) throws RemoteException;
}
