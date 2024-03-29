package com.jets.LasserChat.models.services;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.jets.LasserChat.models.entity.Session;
import com.jets.LazerChatCommonService.models.dao.HandshakeServices;
import com.jets.LazerChatCommonService.models.entity.Message;
import com.jets.LazerChatCommonService.models.entity.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

/**
 * CLASS TO MAINTAIN HANDSHAKED CLIENT CONTACTS
 * */
public class ChatClientService
{
    private Map<User, HandshakeServices> clientList;
    private ObservableList<User> userObservableList;
    private Map<User, Session> userSessions;

    public ChatClientService() {
        clientList = new HashMap<>();
        userObservableList = FXCollections.observableArrayList();
        userSessions = new HashMap<>();
    }

    public void addClient(User user, HandshakeServices clientInterface) {
        clientList.put(user, clientInterface);
        userObservableList.add(user);
        System.out.println("User ID = "+ user.getId() + " : Registered in Your Friend List");

        Platform.runLater(()->{
            Notifications.create().title("New Client is Connected !").text(user.getName()+" is Online .").showInformation();
        });
    }

    public void removeClient(User user) {
        clientList.remove(user);
        userObservableList.remove(user);
        System.out.println("User ID = "+ user.getId() + " : Un-Registered in Your Friend List");
    }

    public void sendMessageToClients(Message message, User toUser) {
        try {
            //GET USER HANDSHAKING INTERFACE TO SEND MESSAGE
            if (clientList.containsKey(toUser))
                clientList.get(toUser).receiveMessage(message);
        } catch (RemoteException ex)
        {
            ex.printStackTrace();
        }
    }

    public void unregister(User user) {
        clientList.entrySet().stream().forEach(client -> {
            try {
                client.getValue().unregister(user);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });
        Platform.runLater(()->{
            Notifications.create().title("New Client is disconnected !").text(user.getName()+" is Offline .").showInformation();
        });       }

    /**
     * send file to client
     * */
    public void sendFileToClients(User toUser ,RemoteInputStream ristream, String name, String extension)
    {
        try {
            //GET USER HANDSHAKING INTERFACE TO SEND MESSAGE
            if (clientList.containsKey(toUser)) {
                clientList.get(toUser).receiveFile(toUser  ,ristream,  name,  extension);

            } else {
                System.out.println("can't find " + toUser);
            }

        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    public ObservableList<User> getFriendsList()
    {
        return userObservableList;
    }

    public Session lookupSession(User user, User selectedUser) {
        //IF current user already have a session with the selectedUser
        if (userSessions.containsKey(selectedUser))
        {
            System.out.println("User session already found with : " + selectedUser);
            return userSessions.get(selectedUser);
        }else{
            Session newSession = new Session();
            newSession.addParticipant(user);
            newSession.addParticipant(selectedUser);
            userSessions.put(selectedUser, newSession);
            System.out.println("New session created with : " + selectedUser);
            return newSession;
        }
    }

    public void notifyStatus(User user) {
        clientList.entrySet().forEach(client -> {
            try {
                client.getValue().notifyStatus(user);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }
}
