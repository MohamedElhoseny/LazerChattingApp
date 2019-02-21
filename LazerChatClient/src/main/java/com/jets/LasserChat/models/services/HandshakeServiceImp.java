package com.jets.LasserChat.models.services;

import com.jets.LasserChat.controllers.ChatRoomMainController;
import com.jets.LazerChatCommonService.models.dao.HandshakeServices;
import com.jets.LazerChatCommonService.models.entity.Message;
import com.jets.LazerChatCommonService.models.entity.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * THIS CLASS USED BY SERVER TO REGISTER CONTACT WITH THIS CLIENT TO APPLY CONCEPT OF HANDSHAKING
 */
public class HandshakeServiceImp extends UnicastRemoteObject implements HandshakeServices
{
    ChatRoomMainController chatRoomMainController;

    public HandshakeServiceImp(ChatRoomMainController chatRoomController) throws RemoteException
    {
        super();
        this.chatRoomMainController = chatRoomController;
    }

    @Override
    public void register(User user, HandshakeServices clientInterface) throws RemoteException {
        chatRoomMainController.addClient(user, clientInterface);
    }

    @Override
    public void unregister(User user) throws RemoteException {
        chatRoomMainController.removeClient(user);
    }

    @Override
    public void receive(Message message) throws RemoteException {
        chatRoomMainController.receiveMessage(message);
    }

    @Override
    public void serverStop() throws RemoteException {
        chatRoomMainController.serverStop();
    }
}
