package com.jets.LasserChat.views.controllers;

import com.jets.LazerChatCommonService.models.entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ChatRoomController implements Initializable
{
    @FXML private Button friendChatBtn;
    @FXML private Button recentChatBtn;
    @FXML private Button announcementBtn;
    @FXML private Button friendRequestBtn;

    @FXML private ScrollPane switchableScrollPane;
    @FXML private FriendChatPaneController friendChatPaneController;
    private Map<MenuItems, Parent> menuList;

    public ChatRoomController() {}

    public ChatRoomController(User loginUser) {
        menuList = new HashMap<>();
        initUserRecentChatList(loginUser);
        initUserFriendList(loginUser);
        initUserGroupChatList(loginUser);
        initUserFriendRequestList(loginUser);
        initAnouncementList(loginUser);
    }

    private void initUserRecentChatList(User loginUser) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        File file = new File("E:\\FCIH\\ITI\\JavaSE\\Project\\Implementation\\LazerChatClient\\src\\main\\java\\com\\jets\\LasserChat\\views\\fxml\\RecentChatPane.fxml");
        RecentChatPaneController recentChatPaneController = new RecentChatPaneController(this);
        fxmlLoader.setController(recentChatPaneController);
        try {
            fxmlLoader.setLocation(file.toURL());
            Parent root = fxmlLoader.load();
            menuList.put(MenuItems.RECENTCHAT, root);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void initAnouncementList(User loginUser){

    }
    private void initUserFriendRequestList(User loginUser) {
    }
    private void initUserGroupChatList(User loginUser){
    }
    private void initUserFriendList(User loginUser) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        friendChatPaneController.injectChatRoomController(this);
    }

    @FXML
    private void switchListPane(ActionEvent event)
    {
        Button btnClicked = (Button) event.getSource();
        switch (btnClicked.getId())
        {
            case "friendChatBtn":
                switchableScrollPane.setContent(menuList.get(MenuItems.FRIENDCHAT));
                break;
            case "recentChatBtn":
                switchableScrollPane.setContent(menuList.get(MenuItems.RECENTCHAT));
                break;
            case "announcementBtn":
                switchableScrollPane.setContent(menuList.get(MenuItems.ANNOUNCEMENT));
                break;
            case "friendRequestBtn":
                switchableScrollPane.setContent(menuList.get(MenuItems.FRIENDREQUEST));
                break;
        }
    }

    private enum MenuItems{RECENTCHAT, FRIENDCHAT, GROUPCHAT, FRIENDREQUEST, ANNOUNCEMENT}
}
