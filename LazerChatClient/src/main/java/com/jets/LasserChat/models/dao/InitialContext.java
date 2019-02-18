package com.jets.LasserChat.models.dao;


import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class InitialContext
{
    public static Remote lookup(String remoteService)
    {
        Remote returnedService = null;
        try
        {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1");
            returnedService = registry.lookup(remoteService);

        } catch (RemoteException | NotBoundException ex) {
            System.err.println("Couldn't bind to the following service : "+remoteService);
        }

        return returnedService;
    }
}
