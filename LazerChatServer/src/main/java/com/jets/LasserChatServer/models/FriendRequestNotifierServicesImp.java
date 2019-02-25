package com.jets.LasserChatServer.models;

import com.jets.LazerChatCommonService.models.dao.FriendRequestNotifierServices;
import com.jets.LazerChatCommonService.models.entity.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class FriendRequestNotifierServicesImp extends UnicastRemoteObject implements FriendRequestNotifierServices {

    public FriendRequestNotifierServicesImp() throws RemoteException {
    }

    @Override
    public void notifyFriendRequest(User fromUser, User toUser) {
        RegisterServicesImp.clientList.entrySet().stream().forEach(client -> {
            try {
                // remove all user in this map from user
                if (client.getKey().getPhone().equals(toUser.getPhone())) {
                    client.getValue().notifyFriendRequest(fromUser);
                }
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });
    }
}