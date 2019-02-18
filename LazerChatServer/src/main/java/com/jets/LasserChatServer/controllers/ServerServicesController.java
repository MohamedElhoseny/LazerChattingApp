package com.jets.LasserChatServer.controllers;

import com.jets.LasserChatServer.models.dao.daoImp.UserServicesImp;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerServicesController
{
    public static void main(String[] args){
        new ServerServicesController();
    }

    public ServerServicesController()
    {
        try
        {
            UserServicesImp userServices = new UserServicesImp();
            Registry register= LocateRegistry.getRegistry();
            register.rebind("UserServices", userServices);

            System.out.println("Server is started .");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
