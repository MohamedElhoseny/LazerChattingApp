package com.jets.LazerChatCommonService.models.dao;

import com.jets.LazerChatCommonService.models.entity.Message;
import com.jets.LazerChatCommonService.models.entity.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HandshakeServices extends Remote
{
    void register(User user, HandshakeServices clientInterface) throws RemoteException;

    void unregister(User user) throws RemoteException;

    void receive(Message receivedMessage) throws RemoteException;

    void serverStop() throws RemoteException;
}
