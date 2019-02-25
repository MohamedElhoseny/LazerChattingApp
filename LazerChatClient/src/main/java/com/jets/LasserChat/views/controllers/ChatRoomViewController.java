package com.jets.LasserChat.views.controllers;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;
import com.jets.LasserChat.controllers.ChatRoomMainController;
import com.jets.LasserChat.controllers.MainController;
import com.jets.LasserChat.models.entity.Session;
import com.jets.LasserChat.models.remote.ServiceLocator;
import com.jets.LasserChat.models.services.ChatBotServices;
import com.jets.LasserChat.models.services.NotifierServices;
import com.jets.LasserChat.views.models.FileMessagePane;
import com.jets.LasserChat.views.models.TextMessagePane;
import com.jets.LazerChatCommonService.models.dao.StatusServices;
import com.jets.LazerChatCommonService.models.entity.Annoncement;
import com.jets.LazerChatCommonService.models.entity.Message;
import com.jets.LazerChatCommonService.models.entity.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.Notifications;

import java.io.*;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//must be in main controller
public class ChatRoomViewController implements Initializable, NotifierServices {
    @FXML
    private Circle loginUserImage;
    @FXML
    private Label loginUserName;
    @FXML
    private Label friendNameL;
    @FXML
    private Label friendStatusL;
    @FXML
    private Circle loginUserStatus_Circle;
    @FXML
    private Circle profileImg_R;
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
    private JFXButton chatBotBtn;
    @FXML
    private TextField messageTF;
    @FXML
    private VBox ChatSessionPane;

    //Customizing message components
    @FXML
    private ToggleButton bold_Btn;
    @FXML
    private ToggleButton italic_Btn;
    @FXML
    private ToggleButton underline_Btn;
    @FXML
    private ComboBox<String> size_CB;
    @FXML
    private ComboBox<String> family_CB;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Button customizeMessage_Btn;
    @FXML
    private JFXButton like_Btn;

    private ChatRoomMainController chatRoomMainController;
    private ServerAnnouncementViewController serverAnnouncementViewController;
    private Map<MenuItems, Parent> menuList;
    private User loginUser;
    private Message userMessage;
    private Session userCurrentSession;
    private StatusServices statusServices;
    private ChatBotServices chatBotServices;
    private boolean isChatBotEnable = false;
    private String name;
    private int currentStatue = 1;

    //Constructors
    public ChatRoomViewController(User loginUser) {
        this.loginUser = loginUser;
        chatRoomMainController = new ChatRoomMainController(this, loginUser);
        chatBotServices = new ChatBotServices();
        this.statusServices = (StatusServices) ServiceLocator.getService("StatuesServices");
        menuList = new HashMap<>();

        //init user data methods to UI
        initUserRecentChatList(loginUser);
        initUserFriendList();
        initUserGroupChatList(loginUser);
        initUserFriendRequestList(loginUser);
        initAnouncementList(loginUser);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //set default menuItem to FriendChat
        switchableScrollPane.setContent(menuList.get(MenuItems.FRIENDCHAT));

        //set status states
        ObservableList<String> status = FXCollections.observableArrayList();
        status.add("Available");
        status.add("Away");
        status.add("Busy");
        status.add("Offline");
        loginUserStatus_CB.setItems(status);

        //set customizing setting
        ObservableList<String> sizes = FXCollections.observableArrayList();
        for (int i = 10; i < 60; i += 5)
            sizes.addAll(String.valueOf(i));

        size_CB.setItems(sizes);
        size_CB.getSelectionModel().select(0);

        ObservableList<String> families = FXCollections.observableArrayList();
        families.addAll("Arial", "Arial Black", "Consolas", "Eras Bold ITC", "Segoe UI", "Tahoma");
        family_CB.setItems(families);
        family_CB.getSelectionModel().select(0);

        colorPicker.setValue(Color.BLACK);
        //set user data
        loginUserName.setText(loginUser.getName());
        Image img = new Image(new ByteArrayInputStream(loginUser.getPicture()));
        loginUserImage.setFill(new ImagePattern(img));

        bold_Btn.setOnAction(e -> {
            StringBuilder stringBuilder = new StringBuilder(messageTF.getStyle());
            if (bold_Btn.isSelected()) stringBuilder.append("-fx-font-weight : bold; ");
            else stringBuilder.append("-fx-font-weight : normal; ");

            messageTF.setStyle(stringBuilder.toString());
        });

        italic_Btn.setOnAction(e -> {
            StringBuilder stringBuilder = new StringBuilder(messageTF.getStyle());
            if (italic_Btn.isSelected()) stringBuilder.append("-fx-font-style : italic; ");
            else stringBuilder.append("-fx-font-style : normal; ");

            messageTF.setStyle(stringBuilder.toString());
        });

        underline_Btn.setOnAction(e -> {
            StringBuilder stringBuilder = new StringBuilder(messageTF.getStyle());
            if (underline_Btn.isSelected()) stringBuilder.append("-fx-underline: true; ");
            else stringBuilder.append("-fx-underline: false; ");

            messageTF.setStyle(stringBuilder.toString());
        });

        size_CB.setOnAction(e -> {
            messageTF.setStyle(messageTF.getStyle() + "-fx-font-size: " + size_CB.getSelectionModel().getSelectedItem() + "; ");
        });

        family_CB.setOnAction(e -> {
            messageTF.setStyle(messageTF.getStyle() + "-fx-font-family: " + family_CB.getSelectionModel().getSelectedItem() + "; ");
        });

        colorPicker.setOnAction(e -> {
            String hex = Integer.toHexString(colorPicker.getValue().hashCode());
            messageTF.setStyle(messageTF.getStyle() + "-fx-text-fill: " + hex + "; ");
        });
    }

    ObservableList<User> getUserFriendList() {
        return chatRoomMainController.getClientFriendList();
    }


    //<editor-fold desc= "Events handling">
    @FXML
    void switchListPane(ActionEvent event)
    {
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
        File file = new File("src/main/java/com/jets/LasserChat/views/fxml/AddContactPane.fxml");
        AddContactViewController addContactViewController = new AddContactViewController(this, loginUser);
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
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showSaveDialog(new Stage());
        if (selectedFile != null) chatRoomMainController.saveSession(userCurrentSession.getSessionMessages(), selectedFile);
    }

    @FXML
    void blockUser(ActionEvent event) {

    }

    @FXML
    void logOut(ActionEvent event) {
        chatRoomMainController.unRegister("false");
        System.exit(0);
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
    private void updateProfile(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        File file = new File("src/main/java/com/jets/LasserChat/views/fxml/UpdateProfilePane.fxml");
        UpdateProfileViewController updateProfileViewController = new UpdateProfileViewController(this, loginUser);
        fxmlLoader.setController(updateProfileViewController);
        try {
            fxmlLoader.setLocation(file.toURL());
            Parent root = fxmlLoader.load();
            Stage primaryStage = new Stage();
            Scene scene = MainController.getDecoratedScene(primaryStage, root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * choose file to send it to  friend
     */
    @FXML
    private void chooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(new Stage());


        if (selectedFile != null) {
            ChatSessionPane.getChildren().add(new FileMessagePane(loginUser, selectedFile.getName(), true));

            String fullName = selectedFile.toString();
            System.out.println("File name " + fullName);
            String fileName = selectedFile.getName();


            ExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

            Runnable task = () -> {

                SimpleRemoteInputStream istream = null;
                try {
                    String extention = fullName.substring(fullName.lastIndexOf(".") + 1);

                    // System.out.println(extention);
                    name = fileName.substring(0, fileName.lastIndexOf("."));

                    System.out.println("Sending file " + name);

                    // setup the remote input stream.  note, the client here is actually
                    // acting as an RMI server (very confusing, i know).  this code sets up an
                    // RMI server in the client, which the RemoteFileServer will then
                    // interact with to get the file data.

                    istream = new SimpleRemoteInputStream(new FileInputStream(fullName));
                    ExecutorService executor = Executors.newSingleThreadScheduledExecutor();


                    // call the remote method on the server.  the server will actually
                    // interact with the RMI "server" we started above to retrieve the
                    // file data

                    for (User userInSession : userCurrentSession.getAvailableUsers()) {
                        if (userInSession.getId() != loginUser.getId()) {

                            chatRoomMainController.sendFile(userInSession, istream.export(), name, extention);
                        }
                    }
                } catch (RemoteException | FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    // always make a best attempt to shutdown RemoteInputStream
                    if (istream != null) istream.close();
                }
                System.out.println("Finished sending file " + name);
            };

            executorService.submit(task);
            executorService.shutdown();

        }

    }

    void updateProfileData(User loginUser) {

        loginUserName.setText(loginUser.getName());
        Image img = new Image(new ByteArrayInputStream(loginUser.getPicture()));
        loginUserImage.setFill(new ImagePattern(img));
    }

    boolean updateUser(User updateUser) {
        return chatRoomMainController.updateServices(updateUser);
    }

    @FXML
    void changeUserStatus(ActionEvent event) {
        String selected = loginUserStatus_CB.getSelectionModel().getSelectedItem();
        switch (selected) {
            case "Available":
                loginUserStatus_Circle.setFill(Color.valueOf("#1fff4e"));
                currentStatue = 1;
                break;
            case "Busy":
                loginUserStatus_Circle.setFill(Color.valueOf("#ED8746"));
                currentStatue = 2;
                break;
            case "Away":
                loginUserStatus_Circle.setFill(Color.valueOf("#DF4735"));
                currentStatue = 3;
                break;
            case "Offline":
                loginUserStatus_Circle.setFill(Color.valueOf("#BCBCBC"));
                currentStatue = 4;
                break;
        }
        //Update server and notify friends

        /**
         * invok when user change his statues
         * and when he is login
         * and must be follow by  @notifyStatus method
         */

        try {
            loginUser.setStatus(currentStatue);

            boolean status = statusServices.setUserStatus(loginUser);
            if (status) {
                notifyStatus(loginUser);
            }
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    //</editor-fold>

    //<editor-fold desc="Messaging">

    @FXML
    private void sendMessage(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            //Read client message from UI
            String input = messageTF.getText().trim();

            if (!input.isEmpty()) {
                //set style for current message
                setMessageStyle(input);

                //Add message to sessionMessageList
                userCurrentSession.addMessageToSession(userMessage);
                System.out.println("Your message saved to the current session see Session details : ");
                System.err.println(userCurrentSession);

                //Update UI, must be called !
                Platform.runLater(() -> {
                    ChatSessionPane.getChildren().add(new TextMessagePane(userMessage, true));
                    messageTF.clear();
                });

                //Sending message to all users in session
                System.out.println("Sending new message to session users ...");
                for (User userInSession : userCurrentSession.getAvailableUsers())
                    chatRoomMainController.sendMessage(userMessage, userInSession);

            }
        }
    }

    /**
     * Used to set attribute style to message
     *
     * @param messageString represent message
     */
    private void setMessageStyle(String messageString) {
        userMessage = new Message();
        userMessage.setMessageString(messageString);
        userMessage.setState(Message.MessageState.UNDELIVERED);
        userMessage.setUser(loginUser);

        //Define style
        Message.MessageStyle messageStyle = userMessage.new MessageStyle();

        if (bold_Btn.isSelected()) messageStyle.setBold(true);

        if (italic_Btn.isSelected()) messageStyle.setItalic(true);

        if (underline_Btn.isSelected()) messageStyle.setUnderline(true);

        String hex = Integer.toHexString(colorPicker.getValue().hashCode());
        messageStyle.setColor(hex);
        messageStyle.setFontFamily(family_CB.getSelectionModel().getSelectedItem());
        System.out.println("size = " + size_CB.getSelectionModel().getSelectedItem());
        messageStyle.setSize(Integer.parseInt(size_CB.getSelectionModel().getSelectedItem()));

        //set style selected to message
        System.out.println("Style customized to current message : " + messageStyle);
        userMessage.setMessageStyle(messageStyle);
    }

    /**
     * Called when the current user received a message from one of his friends
     * if there is no session created with this client it must be created and
     * session data will be initialized with data received
     * else append messages to the session related to this sender
     * <p>
     * and chack id chat bot is enable to auto rreplay
     *
     * @param newMessage the new delivered message
     */
    public void receiveMessageFromContact(Message newMessage) {
        //Lookup session for sender
        Session senderSession = chatRoomMainController.lookupSession(loginUser, newMessage.getUser());
        //add received message to sender session
        senderSession.addMessageToSession(newMessage);

        if (userCurrentSession != null) {
            if (senderSession == userCurrentSession) {
                System.out.println("Receiving a text message ..");
                Platform.runLater(() -> {
                    ChatSessionPane.getChildren().add(new TextMessagePane(newMessage, false));
                });
            }
        } else {
            System.out.println("A message received from another user !");
        }

        if (isChatBotEnable) {
            String botResponse = chatBotServices.responseBot(newMessage.getMessageString());
            setMessageStyle(botResponse);
            senderSession.getSessionMessages().add(newMessage);

            if (senderSession.getId() == userCurrentSession.getId()) {
                Platform.runLater(() -> {
                    ChatSessionPane.getChildren().add(new TextMessagePane(userMessage, true));
                });
            }
            chatRoomMainController.sendMessage(userMessage, newMessage.getUser());

        }

    }

    /**
     * to enable chat bot and disable
     */
    @FXML
    private void openCloseChatBot(ActionEvent event) {
        if (isChatBotEnable) {
            isChatBotEnable = false;
            chatBotBtn.setStyle("-fx-background-color: transparent");
            messageTF.setDisable(false);

        } else {
            isChatBotEnable = true;
            chatBotBtn.setStyle("-fx-background-color: #2f2f2f");
            messageTF.setDisable(true);

        }
    }

    /**
     * send file to client
     */

    public void receiveFileFromContact(User toUser, RemoteInputStream ristream, String name, String extension) {

        Session senderSession = chatRoomMainController.lookupSession(loginUser, toUser);

        if (userCurrentSession != null) {
            System.out.println("I am Here in session " + senderSession.getId() + ">>>>>" + userCurrentSession.getId());
            //  if (senderSession.getId() == userCurrentSession.getId()) {

            FileMessagePane fileMessagePane = new FileMessagePane();

            Platform.runLater(() -> {
                ChatSessionPane.getChildren().add(new FileMessagePane(toUser, name + "." + extension, false));
            });
            while (!fileMessagePane.isResponce()) {
            }
            if (fileMessagePane.getDirectory() == null) {
                System.out.println("Rejected");
            } else {
                InputStream istream = null;
                try {
                    System.out.println("start traansfer file  ");
                    // istream is instance varable to use it in lamda expresion
                    istream = RemoteInputStreamClient.wrap(ristream);
                    FileOutputStream ostream = null;
                    try {
                        File file = new File(fileMessagePane.getDirectory());
                        File tempFile = File.createTempFile(name, ".".concat(extension), file);
                        ostream = new FileOutputStream(tempFile);
                        System.out.println("Writing file " + tempFile);

                        byte[] buf = new byte[1024];

                        int bytesRead = 0;
                        while ((bytesRead = istream.read(buf)) >= 0) {
                            ostream.write(buf, 0, bytesRead);
                        }
                        ostream.flush();

                        System.out.println("Finished writing file " + tempFile);

                    } finally {
                        try {
                            istream.close();
                        } finally {
                            if (ostream != null) {
                                ostream.close();
                            }
                        }
                    }

                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        if (istream != null) {
                            istream.close();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        } else {
            System.out.println("A file received from another user !");
        }

    }

    //</editor-fold>

    //<editor-fold desc="Session">
    Session loadSessionData(User selectedUser) {
        this.userCurrentSession = chatRoomMainController.lookupSession(loginUser, selectedUser);
        loadFriendData(selectedUser); //RISKY
        return userCurrentSession;
    }

    /**
     * Responsible for displaying user session message to chatSession
     *
     * @param selectedUserSession the user selected session
     */
    void displaySessionData(Session selectedUserSession) {
        ChatSessionPane.getChildren().clear();

        System.err.println(selectedUserSession);

        List<Message> sessionMessages = selectedUserSession.getSessionMessages();

        for (int i = 0; i < sessionMessages.size(); i++) {
            Message message = sessionMessages.get(i);
            if (message.getUser() == loginUser) Platform.runLater(() -> {
                ChatSessionPane.getChildren().add(new TextMessagePane(message, true));
            });
            else Platform.runLater(() -> {
                ChatSessionPane.getChildren().add(new TextMessagePane(message, false));
            });
        }
    }

    //</editor-fold>

    //<editor-fold desc="Notification Services">

    /**
     * Responsible for displaying bagdet icon number and message sound
     *
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

    //</editor-fold>

    //<editor-fold desc="Initializing User info panes Methods">
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
        serverAnnouncementViewController = new ServerAnnouncementViewController(this);
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
        GroupChatViewController groupChatViewController = new GroupChatViewController(this, loginUser);
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
    //</editor-fold>

    private void loadFriendData(User selectedUser) {
        friendNameL.setText(selectedUser.getName());
        //friendStatusL.setText(selectedUser.getStatus().toString());
        chattedUserName_R.setText(selectedUser.getName());
        chattedUserBio_R.setText(selectedUser.getBio());
        chattedUserLoc_R.setText(selectedUser.getCountry());
        chattedUserEmail_R.setText(selectedUser.getEmail());
        chattedUserBirthdate_R.setText(selectedUser.getDate());
        chattedUserPhone_R.setText(selectedUser.getPhone());
        Image img = new Image(new ByteArrayInputStream(selectedUser.getPicture()));
        profileImg_R.setFill(new ImagePattern(img));
    }

    public void closeChatRoomPane() {
        chatRoomMainController.unRegister("true");
    }

    void closeAddContactPane() {
        addContactPane.setVisible(false);
    }

    boolean checkIfUserExist(User newContact) {
        return chatRoomMainController.checkIfUserExist(newContact);
    }

    boolean addNewContact(User loginUser, User newContact) {
        return chatRoomMainController.addNewContact(loginUser, newContact);
    }

    boolean checkIfFriends(User loginUser, User newContact) {
        return chatRoomMainController.checkIfFriends(loginUser, newContact);
    }

    void notifyFriendsRequest(User loginUser, User newContact) {
        chatRoomMainController.notifyFriendsRequest(loginUser, newContact);
    }

    boolean addFriendRequest(User loginUser, User newContact) {
        return chatRoomMainController.addFriendRequest(loginUser, newContact);
    }

    boolean checkIfPending(User loginUser, User newContact) {
        return chatRoomMainController.checkIfPending(loginUser, newContact);
    }

    public void notifyFriendRequest(User fromUser) {
        System.out.println(fromUser.getName());
    }

    public void recieveAnnoncement(Annoncement annoncement) {
        serverAnnouncementViewController.recieveAnnoncement(annoncement);
    }

    //Inner classes
    private enum MenuItems {
        RECENTCHAT, FRIENDCHAT, GROUPCHAT, FRIENDREQUEST, ANNOUNCEMENT
    }
}
