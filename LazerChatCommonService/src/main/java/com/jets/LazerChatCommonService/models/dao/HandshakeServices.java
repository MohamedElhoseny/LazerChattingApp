package com.jets.LazerChatCommonService.models.dao;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.jets.LazerChatCommonService.models.entity.Message;
import com.jets.LazerChatCommonService.models.entity.User;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface HandshakeServices extends Remote
{
    void register(User user, HandshakeServices clientInterface) throws RemoteException;

    void unregister(User user) throws RemoteException;

    void receiveMessage(Message receivedMessage) throws RemoteException;

    void receiveFile(User toUser , RemoteInputStream ristream, String name, String extension) throws RemoteException;

    void serverStop() throws RemoteException;

    /* Notify user only about receive a new friend request */
    void notifyFriendRequest(User fromUser) throws RemoteException;
}
