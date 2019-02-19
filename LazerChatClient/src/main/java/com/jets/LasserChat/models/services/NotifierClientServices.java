package com.jets.LasserChat.models.services;

import com.jets.LasserChat.models.services.NotifierServices;
import com.jets.LazerChatCommonService.models.entity.User;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class NotifierClientServices extends UnicastRemoteObject implements NotifierServices
{
    public NotifierClientServices() throws RemoteException
    { }

    @Override
    public void notifyMessage(User fromUser) {

    }

    @Override
    public void notifyStatus(User fromUserStatus) {

    }

    @Override
    public void notifyFileRequest(User fromUser, File senderFile) {

    }

    @Override
    public void notifyFriendRequest(User fromUser) {

    }
}
