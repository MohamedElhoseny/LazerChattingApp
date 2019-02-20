package com.jets.LasserChat.models.services;

import com.jets.LazerChatCommonService.models.dao.HandshakeServices;
import com.jets.LazerChatCommonService.models.entity.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

/**
 * CLASS TO MAINTAIN HANDSHAKED CLIENT CONTACTS
 * */
public class ChatClientService
{
    private Map<Integer, HandshakeServices> clientList;

    public ChatClientService() {
        clientList = new HashMap<>();
    }

    public void addClient(int userId, HandshakeServices clientInterface) {
        clientList.put(userId, clientInterface);
        System.out.println(userId + ": Registered in My List");
    }

    public void removeClient(int userId) {
        clientList.remove(userId);
        System.out.println(userId + ": Un-Registered from My List");
    }

    public void sendMessageToClients(Message message, int toUserId) {
        try {
            //GET USER HANDSHAKING INTERFACE TO SEND MESSAGE
            if (clientList.containsKey(toUserId)) {
                clientList.get(toUserId).receive(message);   //RISKY
            } else {
                System.out.println("can't find " + toUserId);
            }

        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    public void unregister(int userId) {
        clientList.entrySet().stream().forEach(client -> {
            try {
                client.getValue().unregister(userId);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });
    }

    public ObservableList<Integer> getFriendsList() {
        ObservableList<Integer> users = FXCollections.observableArrayList();
        clientList.forEach((userId, clientInterface) -> {
            users.add(userId);
        });
        return users;
    }

}
