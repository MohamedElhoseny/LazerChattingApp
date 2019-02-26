package com.jets.LasserChat.models.services;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.jets.LasserChat.controllers.ChatRoomMainController;
import com.jets.LazerChatCommonService.models.dao.HandshakeServices;
import com.jets.LazerChatCommonService.models.entity.Annoncement;
import com.jets.LazerChatCommonService.models.entity.Message;
import com.jets.LazerChatCommonService.models.entity.User;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * THIS CLASS USED BY SERVER TO REGISTER CONTACT WITH THIS CLIENT TO APPLY CONCEPT OF HANDSHAKING
 */
public class HandshakeServiceImp extends UnicastRemoteObject implements HandshakeServices
{
    private ChatRoomMainController chatRoomMainController;

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
    public void receiveMessage(Message message) throws RemoteException {
        chatRoomMainController.receiveMessage(message);
        chatRoomMainController.notifyMessage(message.getUser());
    }

    @Override
    public void receiveFile(User toUser, RemoteInputStream ristream, String name, String extension)  throws RemoteException {
        chatRoomMainController.reciveFile( toUser,  ristream,  name, extension);

    }

    @Override
    public void receiveAnnoncement(Annoncement annoncement) throws RemoteException {
        chatRoomMainController.recieveAnnoncement(annoncement);
    }

    @Override
    public void serverStop() throws RemoteException {
        chatRoomMainController.serverStop();
    }

    @Override
    public void notifyFriendRequest(User user) throws RemoteException {
        chatRoomMainController.notifyFriendRequest(user);
    }

    @Override
    public void notifyStatus(User user) throws RemoteException {
        chatRoomMainController.notifyStatusRecieve(user);
    }
}
