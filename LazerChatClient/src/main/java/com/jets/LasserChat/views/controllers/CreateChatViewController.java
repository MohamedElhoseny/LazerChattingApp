package com.jets.LasserChat.views.controllers;

import Animation.Transition.FadeOutDownBigTransition;
import com.jets.LasserChat.models.entity.Session;
import com.jets.LazerChatCommonService.models.entity.User;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Callback;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateChatViewController implements Initializable
{
    @FXML private VBox ChatSessionPane;
    @FXML private FlowPane friendItemListPane;
    @FXML private FlowPane addedPeoplePane;

    private ObservableList<User> friendItemList;
    private ArrayList<FriendItemPane> friendItemPanes;
    private ArrayList<User> selectedFriends;

    private GroupChatViewController controller;

    public CreateChatViewController(GroupChatViewController controller)
    {
        this.controller = controller;
        //read friend list
        this.friendItemList = FXCollections.observableArrayList(controller.getFriendList());
        this.selectedFriends = new ArrayList<>();
        this.friendItemPanes = new ArrayList<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        //init friends item panes
        for (User user: friendItemList) {
            FriendItemPane friendItemPane = new FriendItemPane(user);
            friendItemPane.setOnAction(e->addContactToGroup(friendItemPane));
            friendItemPanes.add(friendItemPane);
        }

        //init list
        friendItemListPane.getChildren().addAll(friendItemPanes);
    }

    /**
     * Called by all button hold user information
     * @param friendItemPane represent a friend
     */
    private void addContactToGroup(FriendItemPane friendItemPane)
    {
        Circle profileImg = new Circle();
        profileImg.setFill(javafx.scene.paint.Color.WHITE);
        profileImg.setRadius(20.0);
        profileImg.setStroke(javafx.scene.paint.Color.BLACK);
        profileImg.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        Image image = new Image(new ByteArrayInputStream(
                friendItemPane.getUser().getPicture(), 0, friendItemPane.getUser().getPicture().length));
        profileImg.setFill(new ImagePattern(image));

        //add user to selected user list inOrder to create session once clicked on create group
        this.selectedFriends.add(friendItemPane.getUser());
        Platform.runLater(()->{
            new FadeOutDownBigTransition(friendItemPane).play();
            this.addedPeoplePane.getChildren().add(profileImg);
        });
    }



    @FXML void cancelGroupCreation(ActionEvent event)
    {
        friendItemPanes.clear();
        selectedFriends.clear();
    }

    @FXML private void createGroup(ActionEvent event)
    {
        Session groupSession = new Session();

        //Add users selected to a group session
        for (User user : selectedFriends)
            groupSession.addParticipant(user);

        //call parent caller to add this session to group list
        controller.addNewGroupSession(groupSession);
    }


    private class FriendItemPane extends JFXButton
    {
        final Circle profileImg;
        final User user;

        public FriendItemPane(User user)
        {
            this.user = user;
            profileImg = new Circle();
            setStyle("-fx-border-color: #60d67c; -fx-background-color: #60d67c; -fx-text-fill: white");
            profileImg.setFill(javafx.scene.paint.Color.WHITE);
            profileImg.setRadius(18.0);
            profileImg.setStroke(javafx.scene.paint.Color.BLACK);
            profileImg.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
            setGraphic(profileImg);
            Image image = new Image(new ByteArrayInputStream(user.getPicture(), 0, user.getPicture().length));
            profileImg.setFill(new ImagePattern(image));
            setFont(new Font("Arial", 15.0));
            setText(user.getName());
        }

        public User getUser() {
            return user;
        }
    }
}
