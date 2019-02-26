package com.jets.LasserChat.views.controllers;

import com.jets.LasserChat.models.entity.Session;
import com.jets.LasserChat.views.models.FriendItemPane;
import com.jets.LazerChatCommonService.models.entity.User;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Callback;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FriendChatViewController implements Initializable
{
    @FXML
    private JFXListView<User> friendListView;

    private ObservableList<User> friendList;
    private ChatRoomViewController chatRoomViewController;
    private User currentUser;

    public FriendChatViewController() {

    }

    public FriendChatViewController(ChatRoomViewController chatRoomViewController)
    {
        this.chatRoomViewController = chatRoomViewController;
        this.currentUser = new User();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        //to make user choose only one element at time
        friendListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        friendListView.setStyle("-fx-border-color: #40444A; -fx-background-color: #40444A;" + "-fx-control-inner-background : #40444A");
        //Simple customization
        friendListView.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
            @Override
            public ListCell<User> call(ListView<User> param) {
                // Return ListCell after customizing its view
                return new ListCell<User>()
                {
                    FriendItemPane contactNode = null;

                    @Override
                    protected void updateItem(User item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item != null) {
                            if (!empty) {
                                contactNode = new FriendItemPane(item);

                                //Update cell data item
                                this.setUserData(item);
                                this.setItem(item);
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
        friendList = chatRoomViewController.getUserFriendList();
        friendListView.setItems(friendList);
    }

    @FXML
    void selectFriendSession(MouseEvent event)
    {
        User selectedUser = friendListView.getSelectionModel().getSelectedItem();

        if (selectedUser != null)
        {
            if (currentUser == selectedUser)
            {
                System.out.println("User select same contact to chat.");
            }else {
                currentUser = selectedUser;
                //Switching to another friend
                Session session = chatRoomViewController.loadSessionData(selectedUser);
                chatRoomViewController.displaySessionData(session);
                System.out.println("User select this contact to chat : "+currentUser);
            }
        }
    }

}
