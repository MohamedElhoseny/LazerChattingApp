package com.jets.LasserChatServer.models;

import com.jets.LazerChatCommonService.models.dao.StatusServices;
import com.jets.LazerChatCommonService.models.entity.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class StatuesServicesImp  extends UnicastRemoteObject implements StatusServices {

    UserDAOImplementation userDAOImplementation;

    public StatuesServicesImp() throws RemoteException {
        userDAOImplementation=UserDAOImplementation.getInstance();
    }

    @Override
    public boolean setUserStatus(User myUser) throws RemoteException {
        return  userDAOImplementation.updateUserStatues(myUser);
    }
}
