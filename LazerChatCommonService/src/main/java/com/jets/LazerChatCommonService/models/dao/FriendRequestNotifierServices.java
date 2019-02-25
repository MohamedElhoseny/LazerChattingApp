package com.jets.LazerChatCommonService.models.dao;

import com.jets.LazerChatCommonService.models.entity.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FriendRequestNotifierServices extends Remote {
    /* Notify user only about receive a new friend request */
    void notifyFriendRequest(User loginUser, User newContact) throws RemoteException;
}
