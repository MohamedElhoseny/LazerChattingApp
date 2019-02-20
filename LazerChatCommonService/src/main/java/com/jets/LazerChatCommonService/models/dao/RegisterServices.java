package com.jets.LazerChatCommonService.models.dao;

import com.jets.LazerChatCommonService.models.entity.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RegisterServices extends Remote
{
    void register(User user, HandshakeServices clientInterface) throws RemoteException;

    void unregister(User user) throws RemoteException;
}
