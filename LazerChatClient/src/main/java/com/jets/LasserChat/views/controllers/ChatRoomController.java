package com.jets.LasserChat.views.controllers;

import com.healthmarketscience.rmiio.SimpleRemoteInputStream;
import com.jets.LasserChat.models.services.FileServices;
import com.jets.LasserChat.models.services.MessageServices;
import com.jets.LasserChat.models.services.NotifierServices;
import com.jets.LazerChatCommonService.models.entity.Message;
import com.jets.LazerChatCommonService.models.entity.User;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ChatRoomController implements Initializable, MessageServices, NotifierServices
{
    @FXML private Circle loginUserImage;
    @FXML private Label loginUserName;
    @FXML private Circle loginUserStatus_Circle;
    @FXML private JFXComboBox<?> loginUserStatus_CB;
    @FXML private JFXTextField searchUserTF;
    @FXML private ScrollPane switchableScrollPane;
    @FXML private VBox favouritePane;
    @FXML private Button friendChatBtn;
    @FXML private Button recentChatBtn;
    @FXML private Button announcementBtn;
    @FXML private Button friendRequestBtn;
    @FXML private Button groupChatBtn;
    @FXML private Label chattedUserName_R;
    @FXML private Label chattedUserBio_R;
    @FXML private Label chattedUserLoc_R;
    @FXML private Label chattedUserEmail_R;
    @FXML private Label chattedUserBirthdate_R;
    @FXML private Label chattedUserPhone_R;
    @FXML private AnchorPane addContactPane;

    private Map<MenuItems, Parent> menuList;
    private FileServices fileServices;
    private User loginUser;

    //Constructors
    public ChatRoomController() {

    }
    public ChatRoomController(User loginUser) {
        this.loginUser = loginUser;
        menuList = new HashMap<>();
        initUserRecentChatList(loginUser);
        initUserFriendList(loginUser);
        initUserGroupChatList(loginUser);
        initUserFriendRequestList(loginUser);
        initAnouncementList(loginUser);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //set default menuItem to FriendChat
        switchableScrollPane.setContent(menuList.get(MenuItems.FRIENDCHAT));
    }

    @FXML void switchListPane(ActionEvent event) {
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
            case "groupChatBtn":
                switchableScrollPane.setContent(menuList.get(MenuItems.GROUPCHAT));
        }
    }
    @FXML void addContacts(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        File file = new File("E:\\FCIH\\ITI\\JavaSE\\Project\\LazerChattingApp\\LazerChatClient\\src\\main\\java\\com\\jets\\LasserChat\\views\\fxml\\AddContactPane.fxml");
        AddContactPaneController addContactPaneController = new AddContactPaneController(this);
        fxmlLoader.setController(addContactPaneController);
        try {
            fxmlLoader.setLocation(file.toURL());
            Parent root = fxmlLoader.load();
            addContactPane.getChildren().clear();
            addContactPane.getChildren().add(root);
            addContactPane.setVisible(true);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML void addFriendToFavourite(ActionEvent event) {

    }
    @FXML void backUpChat(ActionEvent event) {

    }
    @FXML void blockUser(ActionEvent event) {

    }
    @FXML void fileTransferChooser(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File uploadFile = fileChooser.showOpenDialog(new Stage());
        if (uploadFile != null)
            startUploadingFile(uploadFile);
    }
    @FXML void logOut(ActionEvent event) {

    }
    @FXML void startVideoCall(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        File file = new File("E:\\FCIH\\ITI\\JavaSE\\Project\\LazerChattingApp\\LazerChatClient\\src\\main\\java\\com\\jets\\LasserChat\\views\\fxml\\VideoCallPane.fxml");
        VideoCallPaneController videoCallPaneController = new VideoCallPaneController(this);
        fxmlLoader.setController(videoCallPaneController);
        try {
            fxmlLoader.setLocation(file.toURL());
            Parent root = fxmlLoader.load();
            Stage videoCallStage = new Stage();
            videoCallStage.setScene(new Scene(root));
            videoCallStage.setTitle("Live Video call");
            videoCallStage.initStyle(StageStyle.UNDECORATED);
            videoCallStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML void startVoiceCall(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        File file = new File("E:\\FCIH\\ITI\\JavaSE\\Project\\LazerChattingApp\\LazerChatClient\\src\\main\\java\\com\\jets\\LasserChat\\views\\fxml\\VoiceCallPane.fxml");
        VoiceCallPaneController voiceCallPaneController = new VoiceCallPaneController(this);
        fxmlLoader.setController(voiceCallPaneController);
        try {
            fxmlLoader.setLocation(file.toURL());
            Parent root = fxmlLoader.load();
            Stage voiceCallStage = new Stage();
            voiceCallStage.setScene(new Scene(root));
            voiceCallStage.setTitle("Live Voice call");
            voiceCallStage.initStyle(StageStyle.UNDECORATED);
            voiceCallStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML void updateProfile(ActionEvent event) {

    }

    /* MISSING FUNCTION LOCATION : MUST BE IN A CONTROLLER TO CALL MODEL AND RETURN RESULT*/
    private void startUploadingFile(File uploadFile) {
        if (uploadFile != null)
        {
            String fullName = uploadFile.toString();
            String fileName = uploadFile.getName();

            try {
                String extention = fullName.substring(fullName.lastIndexOf(".") + 1);

                // System.out.println(extention);
                String name = fileName.substring(0, fileName.lastIndexOf("."));

                System.out.println("Sending file " + name);

                try (SimpleRemoteInputStream istream =
                             new SimpleRemoteInputStream(new FileInputStream(fullName))) {

                    /* Must lookup for contact fileServices */
                    fileServices.receiveFile(loginUser, uploadFile, istream.export(), name, extention);
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
                System.out.println("Finished sending file " + name);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    //Initializing Panes Methods
    private void initUserRecentChatList(User loginUser)
    {
        FXMLLoader fxmlLoader = new FXMLLoader();
        File file = new File("E:\\FCIH\\ITI\\JavaSE\\Project\\LazerChattingApp\\LazerChatClient\\src\\main\\java\\com\\jets\\LasserChat\\views\\fxml\\RecentChatPane.fxml");
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

    private void initAnouncementList(User loginUser)
    {
        FXMLLoader fxmlLoader = new FXMLLoader();
        File file = new File("E:\\FCIH\\ITI\\JavaSE\\Project\\LazerChattingApp\\LazerChatClient\\src\\main\\java\\com\\jets\\LasserChat\\views\\fxml\\ServerAnnouncementPane.fxml");
        ServerAnnouncementPaneController serverAnnouncementPaneController = new ServerAnnouncementPaneController(this);
        fxmlLoader.setController(serverAnnouncementPaneController);
        try {
            fxmlLoader.setLocation(file.toURL());
            Parent root = fxmlLoader.load();
            menuList.put(MenuItems.ANNOUNCEMENT, root);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void initUserFriendRequestList(User loginUser)
    {
        FXMLLoader fxmlLoader = new FXMLLoader();
        File file = new File("E:\\FCIH\\ITI\\JavaSE\\Project\\LazerChattingApp\\LazerChatClient\\src\\main\\java\\com\\jets\\LasserChat\\views\\fxml\\FriendRequestPane.fxml");
        FriendRequestPaneController friendRequestPaneController = new FriendRequestPaneController(this);
        fxmlLoader.setController(friendRequestPaneController);
        try {
            fxmlLoader.setLocation(file.toURL());
            Parent root = fxmlLoader.load();
            menuList.put(MenuItems.FRIENDREQUEST, root);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void initUserGroupChatList(User loginUser)
    {
        FXMLLoader fxmlLoader = new FXMLLoader();
        File file = new File("E:\\FCIH\\ITI\\JavaSE\\Project\\LazerChattingApp\\LazerChatClient\\src\\main\\java\\com\\jets\\LasserChat\\views\\fxml\\GroupChatPane.fxml");
        GroupChatPaneController groupChatPaneController = new GroupChatPaneController(this);
        fxmlLoader.setController(groupChatPaneController);
        try {
            fxmlLoader.setLocation(file.toURL());
            Parent root = fxmlLoader.load();
            menuList.put(MenuItems.GROUPCHAT, root);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void initUserFriendList(User loginUser)
    {
        FXMLLoader fxmlLoader = new FXMLLoader();
        File file = new File("E:\\FCIH\\ITI\\JavaSE\\Project\\LazerChattingApp\\LazerChatClient\\src\\main\\java\\com\\jets\\LasserChat\\views\\fxml\\FriendChatPane.fxml");
        FriendChatPaneController friendChatPaneController = new FriendChatPaneController(this);
        fxmlLoader.setController(friendChatPaneController);
        try {
            fxmlLoader.setLocation(file.toURL());
            Parent root = fxmlLoader.load();
            menuList.put(MenuItems.FRIENDCHAT, root);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    @Override
    public void receive(Message newMessage) {
        notifyMessage(newMessage.getUser());
    }

    @Override
    public void notifyMessage(User fromUser) {

    }

    @Override
    public void notifyStatus(User fromUserStatus) {

    }

    @Override
    public void notifyFileRequest(User fromUser, File senderFile) {

    }

    @Override
    public void notifyFriendRequest(User fromUser) {

    }

    private enum MenuItems
    {RECENTCHAT, FRIENDCHAT, GROUPCHAT, FRIENDREQUEST, ANNOUNCEMENT}
}
