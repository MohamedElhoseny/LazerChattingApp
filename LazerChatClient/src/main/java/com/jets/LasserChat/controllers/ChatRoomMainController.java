package com.jets.LasserChat.controllers;
import com.jets.LasserChat.models.services.ChatClientService;
import com.jets.LasserChat.models.services.ChatServerService;
import com.jets.LasserChat.models.services.HandshakeServiceImp;
import com.jets.LasserChat.views.controllers.ChatRoomViewController;
import com.jets.LazerChatCommonService.models.dao.HandshakeServices;
import com.jets.LazerChatCommonService.models.entity.Message;
import com.jets.LazerChatCommonService.models.entity.User;

import java.rmi.RemoteException;

public class ChatRoomMainController
{
    //To communicate with server for register/unregister handshaking with user contacts
    private ChatServerService chatServerService;

    //for handshaking with contacts used by server to register / unregister new client to this user
    private HandshakeServices handshakeServices;

    //To communicate with contacts 'chatting services' handle handshaked clients of this user
    private ChatClientService chatClientService;


    //To communicate with view controller
    private ChatRoomViewController chatRoomViewController;
    private boolean isServerStopped = false;
    private User user;

    public ChatRoomMainController(ChatRoomViewController viewController, User loginUser)
    {
        try
        {
            this.user = loginUser;
            handshakeServices = new HandshakeServiceImp(this);
            chatClientService = new ChatClientService();
            chatServerService = new ChatServerService();
            chatRoomViewController = viewController;

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



    /* THIS METHODS CALLED WHEN SERVER (THROUGH HandshakeServiceImp)
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
