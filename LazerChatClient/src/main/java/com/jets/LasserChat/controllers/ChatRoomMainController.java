package com.jets.LasserChat.controllers;
import com.healthmarketscience.rmiio.RemoteInputStream;
import com.jets.LasserChat.models.entity.Session;
import com.jets.LasserChat.models.remote.ServiceLocator;
import com.jets.LasserChat.models.services.*;
import com.jets.LasserChat.views.controllers.ChatRoomViewController;
import com.jets.LazerChatCommonService.models.dao.HandshakeServices;
import com.jets.LazerChatCommonService.models.dao.UserServices;
import com.jets.LazerChatCommonService.models.entity.Message;
import com.jets.LazerChatCommonService.models.entity.User;
import javafx.collections.ObservableList;

import java.io.File;
import java.rmi.RemoteException;
import java.util.List;

public class ChatRoomMainController
{
    //To communicate with server for register/unregister handshaking with user contacts
    private ChatServerService chatServerService;

    //for handshaking with contacts used by server to register / unregister new client to this user
    private HandshakeServices handshakeServices;

    //To communicate with contacts 'chatting services' handle handshaked clients of this user
    private ChatClientService chatClientService;

    //private FileServices chatFileService;
    private BackupChatServices backupChatServices;

    private UserServices userServices;

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
            backupChatServices = new BackupChatServicesImp();
            chatRoomViewController = viewController;
            //Calling server to register my handshake with all my online contacts
            chatServerService.register(user, handshakeServices);

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * USED BY VIEW CONTROLLER TO SEND MESSAGE TO ONE OF HANDSHAKED CLIENT
     * @param message MESSAGE OBJECT REPRESENT MESSAGE SEND BY USER TO ONE OF HIS CLIENT
     * @param toUser THE TARGET USER IN WHICH THE MESSAGE WILL SEND
     */
    public void sendMessage(Message message, User toUser)
    {
        chatClientService.sendMessageToClients(message, toUser);
    }


    /**
     * Callback receiving a message from one of contacts
     * @param receivedMessage the message received
     * @throws RemoteException for remote problems
     */
    public void receiveMessage(Message receivedMessage) throws RemoteException
    {
        chatRoomViewController.receiveMessageFromContact(receivedMessage);
    }

    public ObservableList<User> getClientFriendList()
    {
        return chatClientService.getFriendsList();
    }

    public void addClient(User user, HandshakeServices clientInterface)
    {
        chatClientService.addClient(user, clientInterface);
    }

    public void removeClient(User user) {
        chatClientService.removeClient(user);
    }

    public void serverStop() {
        isServerStopped = true;
        System.out.println("Server Stopped");
    }

    public Session lookupSession(User user, User selectedUser)
    {
        return chatClientService.lookupSession(user, selectedUser);
    }

    public void saveSession (List<Message> messages, File sessionXmlFile)
    {
        try {
            backupChatServices.saveSession(messages,sessionXmlFile);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void reciveFile(User toUser, RemoteInputStream ristream, String name, String extension) {
        chatRoomViewController.receiveFileFromContact(toUser,ristream,name,extension);

    }
    public void sendFile( User toUser, RemoteInputStream export, String name, String extention) {
        chatClientService.sendFileToClients(toUser,export,name,extention);

    }
    public boolean updateServices(User updateUser)
    {   this.userServices = (UserServices) ServiceLocator.getService("UserServices");
        boolean isAccepted;
        try {
            isAccepted = userServices.updateProfile(updateUser);
        } catch (RemoteException e) {
            System.err.println("Error occur in userServices : "+e.getMessage());
            isAccepted = false;
        }
        return isAccepted;
    }
}
