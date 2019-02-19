package com.jets.LasserChatServer.models;

import com.jets.LazerChatCommonService.models.dao.HandshakeServices;
import com.jets.LazerChatCommonService.models.dao.RegisterServices;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class RegisterServicesImp extends UnicastRemoteObject implements RegisterServices
{
    // ArrayList Contains all registered Clients
    static Map<Integer, HandshakeServices> clientList;

    public RegisterServicesImp() throws RemoteException {
        super();
        clientList = new HashMap<>();
    }

    @Override
    public void register(int userId, HandshakeServices clientInterface) throws RemoteException {
        clientList.entrySet().stream().forEach(client -> {
            try {
                // add all user in this map to new user
                clientInterface.register(client.getKey(), client.getValue());
                // add new user to all user in this map
                client.getValue().register(userId, clientInterface);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });
        clientList.put(userId, clientInterface);
        System.out.println(userId + ": Is Registered on Server");
    }

    @Override
    public void unregister(int userId) throws RemoteException {
        clientList.entrySet().stream().forEach(client -> {
            try {
                // remove all user in this map from user
                if (clientList.containsKey(userId)) {
                    clientList.get(userId).unregister(client.getKey());
                }
                // remove user from all user in this map
                client.getValue().unregister(userId);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });
        clientList.remove(userId);
        System.out.println(userId + ": Is Removed from Server");
    }




    public void stopServer() {
        // send to all user server is stopped
        clientList.entrySet().stream().forEach((client) -> {
            try {
                client.getValue().serverStop();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void startServer() {
        /*// send to all user server is running again
        clientList.entrySet().stream().forEach((client) -> {
            try {
                client.getValue().serverRunningAgain(this);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });*/
    }

    public void clearMap() {
        clientList.clear();
    }

}
