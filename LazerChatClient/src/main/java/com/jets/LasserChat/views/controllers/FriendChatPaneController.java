package com.jets.LasserChat.views.controllers;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class FriendChatPaneController implements Initializable
{
    ChatRoomController chatRoomController;

    void injectChatRoomController(ChatRoomController chatRoomController)
    {
        this.chatRoomController = chatRoomController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
