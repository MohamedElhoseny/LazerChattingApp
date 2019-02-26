package com.jets.LasserChat.models.services;

import com.jets.LazerChatCommonService.models.entity.User;
import javafx.application.Platform;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class NotifierClientServices extends UnicastRemoteObject implements NotifierServices {

    String statues[] ={"Avalible","Busy","Away"};
    public NotifierClientServices() throws RemoteException {
    }

    @Override
    public void notifyMessage(User fromUser) {
        Platform.runLater(() -> {
            Notifications.create().title("Title Text").text("Hello World 0!").showWarning();
        });
    }

    @Override
    public void notifyStatus(User fromUserStatus) {
        Platform.runLater(() -> {
            Notifications.create().title(fromUserStatus.getName()).text(String.valueOf(statues[fromUserStatus.getStatus()-1])).showInformation();
            System.out.println(fromUserStatus.getName());
        });
    }

    @Override
    public void notifyFileRequest(User fromUser, File senderFile) {

    }

}