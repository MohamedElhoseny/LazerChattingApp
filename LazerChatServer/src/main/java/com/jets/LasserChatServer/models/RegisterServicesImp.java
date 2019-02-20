package com.jets.LasserChatServer.models;

import com.jets.LazerChatCommonService.models.dao.HandshakeServices;
import com.jets.LazerChatCommonService.models.dao.RegisterServices;
import com.jets.LazerChatCommonService.models.entity.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class RegisterServicesImp extends UnicastRemoteObject implements RegisterServices
{
    // ArrayList Contains all registered Clients
    static Map<User, HandshakeServices> clientList;

    public RegisterServicesImp() throws RemoteException {
        super();
        clientList = new HashMap<>();
    }

    /**
     * add client to online clients and handshaking with his/her friends
     * @param user  user id of login user
     * @param clientInterface   clientInterface used by other friends to communicate with this client
     * @throws RemoteException  ..
     */
    @Override
    public void register(User user, HandshakeServices clientInterface) throws RemoteException {
        clientList.entrySet().stream().forEach(client -> {
            try {
                // add all user in this map to new user
                clientInterface.register(client.getKey(), client.getValue());
                // add new user to all user in this map
                client.getValue().register(user, clientInterface);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });
        //add client to online clients
        clientList.put(user, clientInterface);
        System.out.println(user + ": Is Registered on Server");
    }

    @Override
    public void unregister(User user) throws RemoteException {
        clientList.entrySet().stream().forEach(client -> {
            try {
                // remove all user in this map from user
                if (clientList.containsKey(user)) {
                    clientList.get(user).unregister(client.getKey());
                }
                // remove user from all user in this map
                client.getValue().unregister(user);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });
        clientList.remove(user);
        System.out.println(user + ": Is Removed from Server");
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
