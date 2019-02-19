package com.jets.LazerChatCommonService.models.dao;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RegisterServices extends Remote
{
    void register(int userId, HandshakeServices clientInterface) throws RemoteException;

    void unregister(int userId) throws RemoteException;
}
