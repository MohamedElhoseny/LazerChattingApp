package com.jets.LasserChat.models.services;

import com.jets.LasserChat.models.remote.ServiceLocator;
import com.jets.LazerChatCommonService.models.dao.FriendRequestNotifierServices;
import com.jets.LazerChatCommonService.models.entity.User;

import java.rmi.RemoteException;

public class NotifierServerServices {

    private FriendRequestNotifierServices friendRequestNotifierServices;

    public NotifierServerServices() {
        friendRequestNotifierServices = (FriendRequestNotifierServices) ServiceLocator.getService("FriendRequestNotifierServices");
    }


    public void notifyFriendsRequest(User loginUser, User newContact) {
        try {
            friendRequestNotifierServices.notifyFriendRequest(loginUser,newContact);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}