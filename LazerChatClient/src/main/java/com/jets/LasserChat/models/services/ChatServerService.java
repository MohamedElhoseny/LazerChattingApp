package com.jets.LasserChat.models.services;

import com.jets.LasserChat.models.dao.ServiceLocator;
import com.jets.LazerChatCommonService.models.dao.HandshakeServices;
import com.jets.LazerChatCommonService.models.dao.RegisterServices;
import java.rmi.RemoteException;

/**
 * RESPONSIBLE TO INJECT SERVER TO REGISTER AND UNREGISTER USER HANDSHAKING WITH HIS/HER CONTACTS
 */
public class ChatServerService
{
    RegisterServices registerServices;

    public ChatServerService()
    {
        registerServices = (RegisterServices) ServiceLocator.getService("RegisterServices");
    }

    public void register(int userId, HandshakeServices clientService) {
        try {
            registerServices.register(userId, clientService);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    public void unregister(int userId) {
        try {
            registerServices.unregister(userId);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    public void addServer(RegisterServices server) {
        registerServices = server;
        System.out.println("Server back alive");
    }

}
