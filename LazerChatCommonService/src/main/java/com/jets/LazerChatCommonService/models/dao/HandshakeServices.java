package com.jets.LazerChatCommonService.models.dao;

import com.jets.LazerChatCommonService.models.entity.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HandshakeServices extends Remote
{
    void register(int userId, HandshakeServices clientInterface) throws RemoteException;

    void unregister(int userId) throws RemoteException;

    void receive(Message receivedMessage) throws RemoteException;

    void serverStop() throws RemoteException;
}
