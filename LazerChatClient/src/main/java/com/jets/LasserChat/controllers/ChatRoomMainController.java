package com.jets.LasserChat.controllers;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.jets.LasserChat.models.entity.Session;
import com.jets.LasserChat.models.remote.ServiceLocator;
import com.jets.LasserChat.models.services.*;
import com.jets.LasserChat.views.controllers.ChatRoomViewController;
import com.jets.LazerChatCommonService.models.dao.ContactServices;
import com.jets.LazerChatCommonService.models.dao.HandshakeServices;
import com.jets.LazerChatCommonService.models.dao.UserServices;
import com.jets.LazerChatCommonService.models.entity.Annoncement;
import com.jets.LazerChatCommonService.models.entity.Message;
import com.jets.LazerChatCommonService.models.entity.User;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ChatRoomMainController {
    //To communicate with server for register/unregister handshaking with user contacts
    private ChatServerService chatServerService;

    //for handshaking with contacts used by server to register / unregister new client to this user
    private HandshakeServices handshakeServices;

    //To communicate with contacts 'chatting services' handle handshaked clients of this user
    private ChatClientService chatClientService;

    //private FileServices chatFileService;
    private BackupChatServices backupChatServices;

    private UserServices userServices;

    private ContactServices contactServices;

    private NotifierServerServices notifierServerServices;

    private NotifierServices notifierServices;

    //To communicate with view controller
    private ChatRoomViewController chatRoomViewController;
    private boolean isServerStopped = false;
    private User user;


    public ChatRoomMainController(ChatRoomViewController viewController, User loginUser) {
        try {
            this.user = loginUser;
            handshakeServices = new HandshakeServiceImp(this);
            chatClientService = new ChatClientService();
            chatServerService = new ChatServerService();
            backupChatServices = new BackupChatServicesImp();
            contactServices = (ContactServices) ServiceLocator.getService("ContactServices");
            notifierServerServices = new NotifierServerServices();
            chatRoomViewController = viewController;
            //Calling server to register my handshake with all my online contacts
            chatServerService.register(user, handshakeServices);
            notifierServices = new NotifierClientServices();

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * USED BY VIEW CONTROLLER TO SEND MESSAGE TO ONE OF HANDSHAKED CLIENT
     *
     * @param message MESSAGE OBJECT REPRESENT MESSAGE SEND BY USER TO ONE OF HIS CLIENT
     * @param toUser  THE TARGET USER IN WHICH THE MESSAGE WILL SEND
     */
    public void sendMessage(Message message, User toUser) {
        chatClientService.sendMessageToClients(message, toUser);
    }


    /**
     * Callback receiving a message from one of contacts
     *
     * @param receivedMessage the message received
     * @throws RemoteException for remote problems
     */
    public void receiveMessage(Message receivedMessage) throws RemoteException {
        chatRoomViewController.receiveMessageFromContact(receivedMessage);
    }

    public ObservableList<User> getClientFriendList() {
        return chatClientService.getFriendsList();
    }

    public void addClient(User user, HandshakeServices clientInterface) {
        chatClientService.addClient(user, clientInterface);
    }

    public void removeClient(User user) {
        chatClientService.removeClient(user);
    }

    public void serverStop() {
        isServerStopped = true;
        System.out.println("Server Stopped");
    }

    public Session lookupSession(User user, User selectedUser) {
        return chatClientService.lookupSession(user, selectedUser);
    }

    public void saveSession(List<Message> messages, File sessionXmlFile) {
        try {
            backupChatServices.saveSession(messages, sessionXmlFile);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void reciveFile(User toUser, RemoteInputStream ristream, String name, String extension) {
        chatRoomViewController.receiveFileFromContact(toUser, ristream, name, extension);

    }

    public void sendFile(User toUser, RemoteInputStream export, String name, String extention) {
        chatClientService.sendFileToClients(toUser, export, name, extention);

    }

    public boolean updateServices(User updateUser) {
        this.userServices = (UserServices) ServiceLocator.getService("UserServices");
        boolean isAccepted;
        try {
            isAccepted = userServices.updateProfile(updateUser);
        } catch (RemoteException e) {
            System.err.println("Error occur in userServices : " + e.getMessage());
            isAccepted = false;
        }
        return isAccepted;
    }

    public void unRegister(String status) {
        if (!isServerStopped)
            // if server is running will remove user from server and friends
            chatServerService.unregister(user);
         else
            // if server is not running will remove user from friends
            chatClientService.unregister(user);

        saveUserInformation(status);  //save to file
    }

    private void saveUserInformation(String status) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("userInformation.txt");
            writer.println(user.getPhone());
            writer.println(user.getPassword());
            writer.println(status);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) writer.close();
        }
    }

    public boolean checkIfUserExist(User newContact) {
        try {
            return contactServices.checkIfUserExist(newContact);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addNewContact(User loginUser, User newContact) {
        try {
            return contactServices.addContact(loginUser, newContact);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkIfFriends(User loginUser, User newContact) {
        try {
            return contactServices.isFriend(loginUser, newContact);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void notifyFriendRequest(User fromUser) {
        chatRoomViewController.notifyFriendRequest(fromUser);
    }

    public void notifyFriendsRequest(User loginUser, User newContact) {
        notifierServerServices.notifyFriendsRequest(loginUser, newContact);
    }

    public boolean addFriendRequest(User loginUser, User newContact) {
        try {
            return contactServices.addFriendRequest(loginUser, newContact);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkIfPending(User loginUser, User newContact) {
        try {
            return contactServices.isPending(loginUser, newContact);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void recieveAnnoncement(Annoncement annoncement) {
        chatRoomViewController.recieveAnnoncement(annoncement);
    }

    /**
     * Responsible for displaying bagdet icon number and message sound
     *
     * @param fromUser the user that sent message
     */
    public void notifyMessage(User fromUser) {
        try {
            notifierServices.notifyMessage(user);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void notifyStatus(User fromUserStatus) {
        chatClientService.notifyStatus(user);
    }


    public void notifyStatusRecieve(User user) {
        try {
            notifierServices.notifyStatus(user);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<User> getUserFriendRequests(User loginUser) {
        try {
            return contactServices.getFriendRequests(loginUser);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteFriendRequest(User loginUser, User senderUser) {
        try {
            contactServices.deleteFriendRequest(senderUser, loginUser);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUserFriendList(String phone) {
        try {
            return contactServices.getAllUserFriendList(phone);
        } catch (RemoteException e) {
            return null;
        }
    }
}
