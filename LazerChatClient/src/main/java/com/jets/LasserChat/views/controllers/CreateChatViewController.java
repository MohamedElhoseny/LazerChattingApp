package com.jets.LasserChat.views.controllers;

import Animation.Transition.FadeOutDownBigTransition;
import com.jets.LasserChat.models.entity.Session;
import com.jets.LasserChat.views.models.FriendItemPane;
import com.jets.LazerChatCommonService.models.entity.User;
import com.jfoenix.controls.JFXButton;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateChatViewController implements Initializable
{
    @FXML private VBox ChatSessionPane;
    @FXML private ListView<User> friendItemListPane;
    @FXML private FlowPane addedPeoplePane;

    private ObservableList<User> friendItemList;
    private ArrayList<User> selectedFriends;
    private GroupChatViewController controller;

    public CreateChatViewController(GroupChatViewController controller)
    {
        this.controller = controller;
        //read friend list
        this.friendItemList = FXCollections.observableArrayList(controller.getFriendList());
        this.selectedFriends = new ArrayList<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.friendItemListPane.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //Simple customization
        friendItemListPane.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
            @Override
            public ListCell<User> call(ListView<User> param) {
                // Return ListCell after customizing its view
                return new ListCell<User>()
                {
                    User user = null;
                    CreateChatViewController.FriendItemPane contactNode = null;

                    @Override
                    protected void updateItem(User item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item != null) {
                            if (!empty) {
                                this.user = item;
                                contactNode = new CreateChatViewController.FriendItemPane(user);
                                //Update cell data item
                                this.setUserData(user);
                                this.setItem(user);
                                //Update cell graphic Node
                                Platform.runLater(()-> setGraphic(contactNode));
                            } else {
                                this.setUserData(null);
                                this.setGraphic(null);
                                this.setItem(null);
                            }
                        } else {
                            this.setUserData(null);
                            this.setGraphic(null);
                            this.setItem(null);
                        }

                    }


                    @Override
                    public void updateSelected(boolean selected) {
                        super.updateSelected(selected);
                        addContactToGroup(user);
                        new FadeOutDownBigTransition(contactNode).play();
                    }
                };
            }
        });

        //set friend items
        friendItemListPane.setItems(friendItemList);
    }


    @FXML
    void cancelGroupCreation(ActionEvent event) {
        friendItemList.clear();
    }

    @FXML
    private void createGroup(ActionEvent event)
    {
        Session groupSession = new Session();

        //Add users selected to a group session
        for (User user : friendItemList)
            groupSession.addParticipant(user);

        //call parent caller to add this session to group list
        controller.addNewGroupSession(groupSession);
    }

    private void addContactToGroup(User user)
    {
        this.selectedFriends.add(user);
        this.friendItemList.remove(user);
        Circle profileImg = new Circle();
        profileImg.setFill(javafx.scene.paint.Color.WHITE);
        profileImg.setRadius(20.0);
        profileImg.setStroke(javafx.scene.paint.Color.BLACK);
        profileImg.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        Image image = new Image(new ByteInputStream(user.getPicture(), 0, user.getPicture().length));
        profileImg.setFill(new ImagePattern(image));

        this.addedPeoplePane.getChildren().add(profileImg);
    }

    private class FriendItemPane extends JFXButton
    {
        final Circle profileImg;

        public FriendItemPane(User user)
        {
            profileImg = new Circle();
            setStyle("-fx-border-color: #60d67c; -fx-background-color: #60d67c; -fx-text-fill: white");
            profileImg.setFill(javafx.scene.paint.Color.WHITE);
            profileImg.setRadius(18.0);
            profileImg.setStroke(javafx.scene.paint.Color.BLACK);
            profileImg.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
            setGraphic(profileImg);
            Image image = new Image(new ByteInputStream(user.getPicture(), 0, user.getPicture().length));
            profileImg.setFill(new ImagePattern(image));
            setFont(new Font("Arial", 15.0));
            setText(user.getName());
        }
    }

}
