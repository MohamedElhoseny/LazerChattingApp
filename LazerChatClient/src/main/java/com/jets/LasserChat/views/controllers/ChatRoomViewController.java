package com.jets.LasserChat.views.controllers;

import com.jets.LasserChat.controllers.ChatRoomMainController;
import com.jets.LasserChat.models.entity.Session;
import com.jets.LasserChat.models.services.NotifierServices;
import com.jets.LazerChatCommonService.models.entity.Message;
import com.jets.LazerChatCommonService.models.entity.User;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
//must be in main controller
public class ChatRoomViewController implements Initializable, NotifierServices  {
    @FXML
    private Circle loginUserImage;
    @FXML
    private Label loginUserName;
    @FXML
    private Circle loginUserStatus_Circle;
    @FXML
    private JFXComboBox<?> loginUserStatus_CB;

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


    public Session loadSessionData(User selectedUser)
    {
        this.userCurrentSession = chatRoomMainController.lookupSession(loginUser, selectedUser);
        return userCurrentSession;
    }

    public void displaySessionData(Session selectedUserSession)
    {
        ChatSessionPane.getChildren().clear();

        List<Message> sessionMessages = selectedUserSession.getSessionMessages();
        System.out.println("Number of messages in this session = "+sessionMessages.size());
        System.out.println(selectedUserSession.getAvailableUsers());

        for (Message message: sessionMessages)
        {
            if (message.getUser() == loginUser)
            {
                Platform.runLater(()->{
                    ChatSessionPane.getChildren().add(new userMessagePane(message.getMessageString(), true));
                });
            }else {
                Platform.runLater(()->{
                    ChatSessionPane.getChildren().add(new userMessagePane(message.getMessageString(), false));
                });
            }
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //set default menuItem to FriendChat
        switchableScrollPane.setContent(menuList.get(MenuItems.FRIENDCHAT));

        loginUserName.setText(loginUser.getName());
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
                userCurrentSession.getSessionMessages().add(userMessage);

                //Send and update UI of other friend
                for (User userInSession: userCurrentSession.getAvailableUsers())
                    chatRoomMainController.sendMessage(userMessage, userInSession);

                //Update UI
                ChatSessionPane.getChildren().add(new userMessagePane(userMessage.getMessageString(), true));


                //clear textfield
                messageTF.clear();
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
        Session session = loadSessionData(newMessage.getUser());
        session.getSessionMessages().add(newMessage);

        Platform.runLater(()->
        {
            ChatSessionPane.getChildren().add(new userMessagePane(newMessage.getMessageString(), false));
        });
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
        File file = new File("E:\\FCIH\\ITI\\JavaSE\\Project\\LazerChattingApp\\LazerChatClient\\src\\main\\java\\com\\jets\\LasserChat\\views\\fxml\\RecentChatPane.fxml");
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
        File file = new File("E:\\FCIH\\ITI\\JavaSE\\Project\\LazerChattingApp\\LazerChatClient\\src\\main\\java\\com\\jets\\LasserChat\\views\\fxml\\ServerAnnouncementPane.fxml");
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
        File file = new File("E:\\FCIH\\ITI\\JavaSE\\Project\\LazerChattingApp\\LazerChatClient\\src\\main\\java\\com\\jets\\LasserChat\\views\\fxml\\FriendRequestPane.fxml");
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
        File file = new File("E:\\FCIH\\ITI\\JavaSE\\Project\\LazerChattingApp\\LazerChatClient\\src\\main\\java\\com\\jets\\LasserChat\\views\\fxml\\GroupChatPane.fxml");
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

    private void initUserFriendList() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        File file = new File("E:\\FCIH\\ITI\\JavaSE\\Project\\LazerChattingApp\\LazerChatClient\\src\\main\\java\\com\\jets\\LasserChat\\views\\fxml\\FriendChatPane.fxml");
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

    private class userMessagePane extends AnchorPane {
        protected final Circle profileImg;
        protected final VBox vBox;
        protected final Label label;
        protected final HBox hBox;
        protected final FontAwesomeIconView fontAwesomeIconView;
        protected final Label label0;

        public userMessagePane(String messageString, boolean isSentbyMe) {

            profileImg = new Circle();
            vBox = new VBox();
            label = new Label();
            hBox = new HBox();
            fontAwesomeIconView = new FontAwesomeIconView();
            label0 = new Label();

            if (isSentbyMe)
                setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            else
                setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);

            setPickOnBounds(false);
            setPrefWidth(510.0);

            AnchorPane.setLeftAnchor(profileImg, 17.0);
            AnchorPane.setTopAnchor(profileImg, 0.0);
            profileImg.setLayoutX(35.0);
            profileImg.setLayoutY(18.0);
            profileImg.setRadius(18.0);
            profileImg.setStroke(javafx.scene.paint.Color.BLACK);
            profileImg.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

            AnchorPane.setLeftAnchor(vBox, 60.0);
            AnchorPane.setRightAnchor(vBox, -3.0);
            AnchorPane.setTopAnchor(vBox, 2.0);
            vBox.setLayoutX(60.0);
            vBox.setLayoutY(7.0);
            vBox.setPrefWidth(453.0);

            label.setMaxWidth(200.0);
            label.setStyle("-fx-background-color: #9CCC65; -fx-border-color: gray; -fx-border-radius: 10; -fx-background-radius: 10;");
            label.setText(messageString);
            label.setTextFill(javafx.scene.paint.Color.WHITE);
            label.setWrapText(true);
            label.setFont(new Font("MT Extra", 14.0));
            label.setPadding(new Insets(10.0));


            label0.setAlignment(javafx.geometry.Pos.CENTER);
            label0.setText("4 : 30 PM");
            label0.setPadding(new Insets(0.0, 0.0, 0.0, 10.0));
            hBox.setPadding(new Insets(2.0, 0.0, 0.0, 5.0));
            VBox.setMargin(hBox, new Insets(0.0));

            getChildren().add(profileImg);
            vBox.getChildren().add(label);
            hBox.getChildren().add(fontAwesomeIconView);
            hBox.getChildren().add(label0);
            vBox.getChildren().add(hBox);
            getChildren().add(vBox);

        }
    }
}
