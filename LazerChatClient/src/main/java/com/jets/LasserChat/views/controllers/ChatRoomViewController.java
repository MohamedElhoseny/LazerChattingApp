package com.jets.LasserChat.views.controllers;

import com.jets.LasserChat.controllers.ChatRoomMainController;
import com.jets.LasserChat.models.entity.Session;
import com.jets.LasserChat.models.services.NotifierServices;
import com.jets.LasserChat.views.models.userMessagePane;
import com.jets.LazerChatCommonService.models.entity.Message;
import com.jets.LazerChatCommonService.models.entity.User;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.Notifications;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

//must be in main controller
public class ChatRoomViewController implements Initializable, NotifierServices
{
    @FXML
    private Circle loginUserImage;
    @FXML
    private Label loginUserName;
    @FXML
    private Circle loginUserStatus_Circle;
    @FXML
    private JFXComboBox<String> loginUserStatus_CB;

    @FXML
    private JFXTextField searchUserTF;
    @FXML
    private ScrollPane switchableScrollPane;
    @FXML
    private VBox favouritePane;
    @FXML
    private Button friendChatBtn;
    @FXML
    private Button recentChatBtn;
    @FXML
    private Button announcementBtn;
    @FXML
    private Button friendRequestBtn;
    @FXML
    private Button groupChatBtn;
    @FXML
    private Label chattedUserName_R;
    @FXML
    private Label chattedUserBio_R;
    @FXML
    private Label chattedUserLoc_R;
    @FXML
    private Label chattedUserEmail_R;
    @FXML
    private Label chattedUserBirthdate_R;
    @FXML
    private Label chattedUserPhone_R;
    @FXML
    private AnchorPane addContactPane;

    @FXML
    private TextField messageTF;
    @FXML
    private VBox ChatSessionPane;


    private ChatRoomMainController chatRoomMainController;
    private Map<MenuItems, Parent> menuList;
    private User loginUser;
    private Message userMessage;
    private Session userCurrentSession;

    //Constructors
    public ChatRoomViewController(User loginUser)
    {
        this.loginUser = loginUser;
        chatRoomMainController = new ChatRoomMainController(this, loginUser);
        menuList = new HashMap<>();
        userMessage = new Message();


        //init message default setting
        userMessage.setUser(loginUser);

        //init user data methods to UI
        initUserRecentChatList(loginUser);
        initUserFriendList();
        initUserGroupChatList(loginUser);
        initUserFriendRequestList(loginUser);
        initAnouncementList(loginUser);
    }

    public ObservableList<User> getUserFriendList()
    {
        return chatRoomMainController.getClientFriendList();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        //set default menuItem to FriendChat
        switchableScrollPane.setContent(menuList.get(MenuItems.FRIENDCHAT));

        //set status states
        ObservableList<String> status = FXCollections.observableArrayList();
        status.add("Available");
        status.add("Away");
        status.add("Busy");
        status.add("Offline");
        loginUserStatus_CB.setItems(status);

        //set user data
        loginUserName.setText(loginUser.getName());
        Image img = new Image(new ByteArrayInputStream(loginUser.getPicture()));
        loginUserImage.setFill(new ImagePattern(img));
    }

    @FXML
    void switchListPane(ActionEvent event) {
        Button btnClicked = (Button) event.getSource();
        switch (btnClicked.getId()) {
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

    @FXML
    void addContacts(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        File file = new File("E:\\FCIH\\ITI\\JavaSE\\Project\\LazerChattingApp\\LazerChatClient\\src\\main\\java\\com\\jets\\LasserChat\\views\\fxml\\AddContactPane.fxml");
        AddContactViewController addContactViewController = new AddContactViewController(this);
        fxmlLoader.setController(addContactViewController);
        try {
            fxmlLoader.setLocation(file.toURL());
            Parent root = fxmlLoader.load();
            addContactPane.getChildren().clear();
            addContactPane.getChildren().add(root);
            addContactPane.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addFriendToFavourite(ActionEvent event) {

    }

    @FXML
    void backUpChat(ActionEvent event) {

    }

    @FXML
    void blockUser(ActionEvent event) {

    }

    @FXML
    void fileTransferChooser(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File uploadFile = fileChooser.showOpenDialog(new Stage());
        //if (uploadFile != null)

    }

    @FXML
    void logOut(ActionEvent event) {

    }

    @FXML
    void startVideoCall(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        File file = new File("E:\\FCIH\\ITI\\JavaSE\\Project\\LazerChattingApp\\LazerChatClient\\src\\main\\java\\com\\jets\\LasserChat\\views\\fxml\\VideoCallPane.fxml");
        VideoCallViewController videoCallViewController = new VideoCallViewController(this);
        fxmlLoader.setController(videoCallViewController);
        try {
            fxmlLoader.setLocation(file.toURL());
            Parent root = fxmlLoader.load();
            Stage videoCallStage = new Stage();
            videoCallStage.setScene(new Scene(root));
            videoCallStage.setTitle("Live Video call");
            videoCallStage.initStyle(StageStyle.UNDECORATED);
            videoCallStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void startVoiceCall(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        File file = new File("E:\\FCIH\\ITI\\JavaSE\\Project\\LazerChattingApp\\LazerChatClient\\src\\main\\java\\com\\jets\\LasserChat\\views\\fxml\\VoiceCallPane.fxml");
        VoiceCallViewController voiceCallViewController = new VoiceCallViewController(this);
        fxmlLoader.setController(voiceCallViewController);
        try {
            fxmlLoader.setLocation(file.toURL());
            Parent root = fxmlLoader.load();
            Stage voiceCallStage = new Stage();
            voiceCallStage.setScene(new Scene(root));
            voiceCallStage.setTitle("Live Voice call");
            voiceCallStage.initStyle(StageStyle.UNDECORATED);
            voiceCallStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void updateProfile(ActionEvent event) {

    }

    @FXML
    void changeUserStatus(ActionEvent event)
    {
        String selected = loginUserStatus_CB.getSelectionModel().getSelectedItem();
        switch (selected)
        {
            case "Available":
                loginUserStatus_Circle.setFill(Color.valueOf("#1fff4e"));
                break;
            case "Busy":
                loginUserStatus_Circle.setFill(Color.valueOf("#ED8746"));
                break;
            case "Away":
                loginUserStatus_Circle.setFill(Color.valueOf("#DF4735"));
                break;
            case "Offline":
                loginUserStatus_Circle.setFill(Color.valueOf("#BCBCBC"));
                break;
        }
        //Update server and notify friends

    }

    /////////////////////////// Chatting Services /////////////////////////////////////
    /**
     * Used when user want to send a message to the current user session selected by user
     * @param event keyevent when user key pressed on ENTER
     */
    @FXML
    private void sendMessage(KeyEvent event)
    {
        if (event.getCode() == KeyCode.ENTER)
        {
            String input = messageTF.getText().trim();

            if (!input.isEmpty())
            {
                //set style for current message
                setMessageStyle(input);

                //Add message to sessionMessageList
                userCurrentSession.addMessageToSession(userMessage);
                System.out.println("Your message saved to the current session see Session details : ");
                System.err.println(userCurrentSession);

                //Send and update UI of other friend
                //Looping in order to maintain group chat also
                //ChatMainController handle this to send message to others not including this user
                for (User userInSession: userCurrentSession.getAvailableUsers())
                    chatRoomMainController.sendMessage(userMessage, userInSession);

                //Update UI, must be called !
                Platform.runLater(()->{
                    ChatSessionPane.getChildren().add(new userMessagePane(userMessage, true));
                    messageTF.clear();
                });
            }
        }
    }

    private void setMessageStyle(String messageString) {
        userMessage.setMessageString(messageString);
        userMessage.setState(Message.MessageState.UNDELIVERED);
        //userMessage.setMessageStyle();
    }

    /**
     * Called when the current user received a message from one of his friends
     * if there is no session created with this client it must be created and
     * session data will be initialized with data received
     * else append messages to the session related to this sender
     *
     * @param newMessage the new delivered message
     */
    public void receiveMessageFromContact(Message newMessage)
    {
        Session senderSession = chatRoomMainController.lookupSession(loginUser, newMessage.getUser());
        senderSession.addMessageToSession(newMessage);

        if (userCurrentSession != null)
        {
            if (senderSession == userCurrentSession) {
                Platform.runLater(() -> {
                    ChatSessionPane.getChildren().add(new userMessagePane(newMessage, false));
                });
            }
        } else {
            System.out.println("A message received from another user !");
        }
    }

    Session loadSessionData(User selectedUser)
    {
        this.userCurrentSession = chatRoomMainController.lookupSession(loginUser, selectedUser);
        return userCurrentSession;
    }

    /**
     * Responsible for displaying user session message to chatSessionChat
     * @param selectedUserSession the user selected session
     */
    void displaySessionData(Session selectedUserSession)
    {
        ChatSessionPane.getChildren().clear();

        System.err.println(selectedUserSession);

        List<Message> sessionMessages = selectedUserSession.getSessionMessages();

        for (Message current : sessionMessages) {
            if (current.getUser() == loginUser) {
                Platform.runLater(() -> {
                    ChatSessionPane.getChildren().add(new userMessagePane(current, true));
                });
            } else {
                Platform.runLater(() -> {
                    ChatSessionPane.getChildren().add(new userMessagePane(current, false));
                });
            }
        }
    }

    /**
     * Responsible for displaying bagdet icon number and message sound
     * @param fromUser the user that sent message
     */
    @Override
    public void notifyMessage(User fromUser) {
        Platform.runLater(() -> {
            Notifications.create().title("Title Text").text("Hello World 0!").showWarning();
        });
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




    //Initializing Panes Methods
    private void initUserRecentChatList(User loginUser) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        File file = new File("src/main/java/com/jets/LasserChat/views/fxml/RecentChatPane.fxml");
        RecentChatViewController recentChatViewController = new RecentChatViewController(this);
        fxmlLoader.setController(recentChatViewController);
        try {
            fxmlLoader.setLocation(file.toURL());
            Parent root = fxmlLoader.load();
            menuList.put(MenuItems.RECENTCHAT, root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initAnouncementList(User loginUser) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        File file = new File("src/main/java/com/jets/LasserChat/views/fxml/ServerAnnouncementPane.fxml");
        ServerAnnouncementViewController serverAnnouncementViewController = new ServerAnnouncementViewController(this);
        fxmlLoader.setController(serverAnnouncementViewController);
        try {
            fxmlLoader.setLocation(file.toURL());
            Parent root = fxmlLoader.load();
            menuList.put(MenuItems.ANNOUNCEMENT, root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initUserFriendRequestList(User loginUser) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        File file = new File("src/main/java/com/jets/LasserChat/views/fxml/FriendRequestPane.fxml");
        FriendRequestViewController friendRequestViewController = new FriendRequestViewController(this);
        fxmlLoader.setController(friendRequestViewController);
        try {
            fxmlLoader.setLocation(file.toURL());
            Parent root = fxmlLoader.load();
            menuList.put(MenuItems.FRIENDREQUEST, root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initUserGroupChatList(User loginUser) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        File file = new File("src/main/java/com/jets/LasserChat/views/fxml/GroupChatPane.fxml");
        GroupChatViewController groupChatViewController = new GroupChatViewController(this);
        fxmlLoader.setController(groupChatViewController);
        try {
            fxmlLoader.setLocation(file.toURL());
            Parent root = fxmlLoader.load();
            menuList.put(MenuItems.GROUPCHAT, root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initUserFriendList()
    {
        FXMLLoader fxmlLoader = new FXMLLoader();
        File file = new File("src/main/java/com/jets/LasserChat/views/fxml/FriendChatPane.fxml");
        FriendChatViewController friendChatViewController = new FriendChatViewController(this);
        fxmlLoader.setController(friendChatViewController);
        try {
            fxmlLoader.setLocation(file.toURL());
            Parent root = fxmlLoader.load();
            menuList.put(MenuItems.FRIENDCHAT, root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    //Inner classes
    private enum MenuItems {RECENTCHAT, FRIENDCHAT, GROUPCHAT, FRIENDREQUEST, ANNOUNCEMENT}
}
