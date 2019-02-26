package com.jets.LasserChatServer.models;

import com.jets.LasserChatServer.models.UserDAO;
import com.jets.LasserChatServer.models.UserDAOImplementation;
import com.jets.LazerChatCommonService.models.dao.ContactServices;
import com.jets.LazerChatCommonService.models.entity.GroupCategory;
import com.jets.LazerChatCommonService.models.entity.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class ContactServicesImp extends UnicastRemoteObject implements ContactServices {
    private UserDAO userDAO;

    public ContactServicesImp() throws RemoteException {
        userDAO = UserDAOImplementation.getInstance();
    }

    @Override
    public boolean addContact(User user, User newContact) throws RemoteException {
        return userDAO.addFriend(user, newContact);
    }

    @Override
    public boolean isFriend(User user, User user1) throws RemoteException {
        return userDAO.isFriend(user, user1);
    }

    @Override
    public boolean isPending(User fromUser, User toUser) throws RemoteException {
        return userDAO.isPending(fromUser, toUser);
    }

    @Override
    public boolean checkIfUserExist(User user) throws RemoteException {
        return userDAO.isExist(user);
    }

    @Override
    public boolean addFriendRequest(User fromUser, User toUser) throws RemoteException {
        return userDAO.addFriendRequest(fromUser, toUser);
    }

    @Override
    public boolean blockContact(User blockContact) throws RemoteException {
        return false;
    }

    @Override
    public void AddContactToGroup(User Contact, GroupCategory groupType) throws RemoteException {}

    @Override
    public ArrayList<User> getFriendRequests(User user) throws RemoteException {
        return userDAO.getFriendRequests(user);
    }

    @Override
    public void deleteFriendRequest(User fromuser, User touser) throws RemoteException {
        userDAO.deleteFriendRequest(fromuser,touser);
    }

    @Override
    public List<User> getAllUserFriendList(String phone) throws RemoteException {
        return userDAO.getUserFriends(phone);
    }
}