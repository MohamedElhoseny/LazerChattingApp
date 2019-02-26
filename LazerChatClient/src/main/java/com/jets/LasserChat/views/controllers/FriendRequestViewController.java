package com.jets.LasserChat.views.controllers;

import com.jets.LasserChat.views.models.FriendRequestItem;
import com.jets.LazerChatCommonService.models.entity.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FriendRequestViewController implements Initializable {
    @FXML
    private ListView<User> friendRequestPane;

    private ChatRoomViewController chatRoomViewController;
    private ObservableList<User> requestedUsers;


    public FriendRequestViewController(ChatRoomViewController chatRoomViewController) {
        this.chatRoomViewController = chatRoomViewController;
        requestedUsers = FXCollections.observableArrayList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //to make user choose only one element at time
        friendRequestPane.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        friendRequestPane.setStyle("-fx-border-color: #40444A; -fx-background-color: #40444A;" + "-fx-control-inner-background : #40444A");
        //Simple customization
        friendRequestPane.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
            @Override
            public ListCell<User> call(ListView<User> param) {
                // Return ListCell after customizing its view
                return new ListCell<User>() {
                    FriendRequestItem contactNode = null;

                    @Override
                    protected void updateItem(User item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item != null) {
                            if (!empty) {
                                contactNode = new FriendRequestItem(item,chatRoomViewController);

                                //Update cell data item
                                this.setUserData(item);
                                this.setItem(item);
                                //Update cell graphic Node
                                Platform.runLater(() -> setGraphic(contactNode));
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
                        if (this.isSelected()) {
                            this.setStyle("-fx-background-color: RED");
                            this.contactNode.setStyle("-fx-background-color: RED");
                        } else {
                            this.contactNode.setStyle("-fx-background-color: #40444A");
                            this.setStyle("-fx-background-color:   #40444A");
                        }
                    }
                };
            }
        });
        //Get user friend list to display it
        ArrayList<User> friendRequests = chatRoomViewController.getUserFriendRequests();
        if (friendRequests != null) {
            requestedUsers.clear();
            requestedUsers.addAll(friendRequests);
        }
        friendRequestPane.setItems(requestedUsers);

    }
}