package com.jets.LasserChat.models.dao.impl;

import com.jets.LasserChat.models.dao.MessageServices;
import com.jets.LasserChat.models.entity.Message;
import com.jets.LazerChatCommonService.models.entity.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MessageClientServices extends UnicastRemoteObject implements MessageServices
{
    public MessageClientServices() throws RemoteException
    { }

    @Override
    public void receiveMessage(User fromUser, Message newMessage) {

    }
}
