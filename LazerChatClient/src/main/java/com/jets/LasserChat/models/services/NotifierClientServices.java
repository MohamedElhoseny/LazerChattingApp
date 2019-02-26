package com.jets.LasserChat.models.services;

import com.jets.LazerChatCommonService.models.entity.User;
import javafx.application.Platform;
import javafx.scene.media.AudioClip;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class NotifierClientServices extends UnicastRemoteObject implements NotifierServices {

    String statues[] ={"Avalible","Busy","Away"};
    public NotifierClientServices() throws RemoteException {
    }

    @Override
    public void notifyMessage(User fromUser) {
        Platform.runLater(() -> {
            Notifications.create().title("New message received !").text(fromUser.getName()+" send a message.").showWarning();
            File audio = new File("src/main/resources/sounds/messageSound.wav");
            AudioClip audioClip = null;
            try {
                audioClip = new javafx.scene.media.AudioClip(audio.toURL().toExternalForm());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            audioClip.play();
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