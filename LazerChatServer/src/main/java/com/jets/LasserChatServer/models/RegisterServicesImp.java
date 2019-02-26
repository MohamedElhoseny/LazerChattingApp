package com.jets.LasserChatServer.models;

import com.jets.LasserChatServer.controllers.ServerServicesController;
import com.jets.LazerChatCommonService.models.dao.HandshakeServices;
import com.jets.LazerChatCommonService.models.dao.RegisterServices;
import com.jets.LazerChatCommonService.models.entity.Annoncement;
import com.jets.LazerChatCommonService.models.entity.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterServicesImp extends UnicastRemoteObject implements RegisterServices
{
    // ArrayList Contains all registered Clients
    static Map<User, HandshakeServices> clientList;
    private List<User> friendsList;
    private UserDAOImplementation userDAOImplementation;
    private ServerServicesController mainController;

    public RegisterServicesImp(ServerServicesController controller) throws RemoteException
    {
        super();
        clientList = new HashMap<>();
        friendsList=new ArrayList<>();
        userDAOImplementation=UserDAOImplementation.getInstance();
        mainController = controller;
    }

    /**
     * add client to online clients and handshaking with his/her friends
     * @param user  user id of login user
     * @param clientInterface   clientInterface used by other friends to communicate with this client
     * @throws RemoteException  ..
     */
    @Override
    public void register(User user, HandshakeServices clientInterface) throws RemoteException
    {
        System.out.println("Registering");
        friendsList = userDAOImplementation.getUserFriends(user.getPhone());

        for (User user1 : friendsList)
            System.out.println( user.getName() +" friend with "+user1.getName());

        clientList.entrySet().stream().forEach(client -> {
            try {
                //check if this client in friend list or not
                if(friendsList.contains(client.getKey()))
                {
                    System.out.println(user+" checkhand with "+client.getKey());

                    // add all user in this map to new user
                    clientInterface.register(client.getKey(), client.getValue());
                    // add new user to all user in this map
                    client.getValue().register(user, clientInterface);
                }
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });
        //add client to online clients
        clientList.put(user, clientInterface);
        System.out.println(user + ": Is Registered on Server");
        mainController.incrementOnline();
    }

    @Override
    public void unregister(User user) throws RemoteException
    {
        clientList.entrySet().stream().forEach(client -> {
            try {
                // remove all user in this map from user
                if (clientList.containsKey(user)) {
                    clientList.get(user).unregister(client.getKey());
                }
                // remove user from all user in this map
                client.getValue().unregister(user);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });

        user.setStatus(4);
        userDAOImplementation.updateUserStatues(user);
        clientList.remove(user);
        System.out.println(user + ": Is Removed from Server");
        mainController.decrementOnline();
    }

    public void stopServer()
    {
        // send to all user server is stopped
        clientList.entrySet().stream().forEach((client) -> {
            try {
                client.getValue().serverStop();
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });
    }

    public void startServer() {
        /*// send to all user server is running again
        clientList.entrySet().stream().forEach((client) -> {
            try {
                client.getValue().serverRunningAgain(this);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });*/
    }

    public void broadcast(Annoncement annoncement) {
        File defaultImg = new File("C:\\Users\\omdae\\Desktop\\admin.jpg");
        byte[]img= convertImageToBytes(defaultImg);
        annoncement.setImage(img);
        if (!clientList.isEmpty()){
            clientList.entrySet().stream().forEach(userHandshakeServicesEntry -> {
                try {

                    HandshakeServices clientServices = userHandshakeServicesEntry.getValue();

                    clientServices.receiveAnnoncement(annoncement);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
        }

    }

    private byte[] convertImageToBytes(File choosenImg)
    {
        BufferedImage image = null;
        try {
            image = ImageIO.read(choosenImg);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            return null;
        }
    }
    public void clearMap() {
        clientList.clear();
    }

}
