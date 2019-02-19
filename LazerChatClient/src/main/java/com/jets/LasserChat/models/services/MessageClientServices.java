package com.jets.LasserChat.models.services;

import com.jets.LasserChat.models.services.MessageServices;
import com.jets.LazerChatCommonService.models.entity.Message;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MessageClientServices extends UnicastRemoteObject implements MessageServices
{
    public MessageClientServices() throws RemoteException
    { }


    @Override
    public void receive(Message receivedMessage) throws RemoteException {

    }
}
