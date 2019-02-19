package com.jets.LasserChat.controllers;
import com.jets.LasserChat.models.services.ChatClientService;
import com.jets.LasserChat.models.services.ChatServerService;
import com.jets.LasserChat.models.services.HandshakingService;
import com.jets.LasserChat.views.controllers.ChatRoomController;
import com.jets.LazerChatCommonService.models.dao.HandshakeServices;
import com.jets.LazerChatCommonService.models.entity.Message;
import com.jets.LazerChatCommonService.models.entity.User;

import java.rmi.RemoteException;

public class ChatRoomMainController
{
    //To communicate with server for register/unregister handshaking with user contacts
    ChatServerService chatServerService;

    //for handshaking with contacts used by server to register / unregister new client to this user
    HandshakeServices handshakeServices;

    //To communicate with contacts 'chatting services' handle handshaked clients of this user
    ChatClientService chatClientService;

    //To communicate with view controller
    ChatRoomController chatRoomViewController;

    boolean isServerStopped = false;
    User user;

    public ChatRoomMainController(ChatRoomController viewController)
    {
        try {
            handshakeServices = new HandshakingService(this);
            chatClientService = new ChatClientService();
            chatServerService = new ChatServerService();
            chatRoomViewController = viewController;

            user = new User();
            user.setId(5);
            user.setName("Ahmed22");

            //Calling server to register my handshake with all my online contacts
            chatServerService.register(user.getId(), handshakeServices);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * USED BY VIEW CONTROLLER TO SEND MESSAGE TO ONE OF HANDSHAKED CLIENT
     * @param message MESSAGE OBJECT REPRESENT MESSAGE SEND BY USER TO ONE OF HIS CLIENT
     * @param toUser THE TARGET USER IN WHICH THE MESSAGE WILL SEND
     */
    public void sendMessage(Message message, int toUser) {
        chatClientService.sendMessageToClients(message, toUser);
    }



    /* THIS METHODS CALLED WHEN SERVER (THROUGH HandshakingService)
     WANT TO REGISTER / UNREGISTER HANDSHAKING WITH CLIENT */
    public void displayMessage(Message receivedMessage) {
        chatRoomViewController.receive(receivedMessage);
    }

    public void addClient(int userId, HandshakeServices clientInterface) {
        chatClientService.addClient(userId, clientInterface);
    }

    public void removeClient(int userId) {
        chatClientService.removeClient(userId);
    }

    public void serverStop() {
        isServerStopped = true;
        System.out.println("Server Stopped");
    }
}
