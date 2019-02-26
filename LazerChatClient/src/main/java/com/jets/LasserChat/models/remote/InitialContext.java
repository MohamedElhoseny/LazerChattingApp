package com.jets.LasserChat.models.remote;


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
            Registry registry = LocateRegistry.getRegistry("10.145.8.37",2000);
            returnedService = registry.lookup(remoteService);

        } catch (RemoteException | NotBoundException ex) {
            System.err.println("Couldn't bind to the following service : "+remoteService);
        }

        return returnedService;
    }
}
