package com.jets.LasserChat.models.services;

import com.jets.LasserChat.controllers.ChatRoomMainController;
import com.jets.LazerChatCommonService.models.dao.HandshakeServices;
import com.jets.LazerChatCommonService.models.entity.Message;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * THIS CLASS USED BY SERVER TO REGISTER CONTACT WITH THIS CLIENT TO APPLY CONCEPT OF HANDSHAKING
 */
public class HandshakeServiceImp extends UnicastRemoteObject implements HandshakeServices
{
    ChatRoomMainController controller;

    public HandshakeServiceImp(ChatRoomMainController chatRoomController) throws RemoteException
    {
        super();
        this.controller = chatRoomController;
    }

    @Override
    public void register(int userId, HandshakeServices clientInterface) throws RemoteException {
        controller.addClient(userId, clientInterface);
    }

    @Override
    public void unregister(int userId) throws RemoteException {
        controller.removeClient(userId);
    }

    @Override
    public void receive(Message message) throws RemoteException {
        controller.displayMessage(message);
    }

    @Override
    public void serverStop() throws RemoteException {
        controller.serverStop();
    }
}
