package com.jets.LasserChat.views.controllers;

import com.jets.LasserChat.models.entity.Session;
import com.jets.LasserChat.views.models.FriendItemPane;
import com.jets.LasserChat.views.models.GroupItemPane;
import com.jets.LazerChatCommonService.models.entity.User;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class GroupChatViewController implements Initializable
{

    @FXML private JFXListView<Session> groupListView;

    private ChatRoomViewController chatRoomViewController;
    private ObservableList<Session> groups;
    private Session currentSession;

    public GroupChatViewController()
    {}

    public GroupChatViewController(ChatRoomViewController chatRoomViewController)
    {
        this.chatRoomViewController = chatRoomViewController;
        currentSession = new Session();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        //to make user choose only one element at time
        groupListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        groupListView.setStyle("-fx-border-color: #40444A; -fx-background-color: #40444A;" + "-fx-control-inner-background : #40444A");
        //Simple customization
        groupListView.setCellFactory(new Callback<ListView<Session>, ListCell<Session>>() {
            @Override
            public ListCell<Session> call(ListView<Session> param) {
                // Return ListCell after customizing its view
                return new ListCell<Session>()
                {
                    GroupItemPane groupNode = null;

                    @Override
                    protected void updateItem(Session item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item != null) {
                            if (!empty) {
                                groupNode = new GroupItemPane(item);

                                //Update cell data item
                                this.setUserData(item);
                                this.setItem(item);
                                //Update cell graphic Node
                                Platform.runLater(()-> setGraphic(groupNode));
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
                            this.groupNode.setStyle("-fx-background-color: RED");
                        } else {
                            this.groupNode.setStyle("-fx-background-color: #40444A");
                            this.setStyle("-fx-background-color:   #40444A");
                        }
                    }
                };
            }
        });

        groupListView.setItems(groups);
    }

    @FXML
    void selectFriendSession(MouseEvent event)
    {
        Session selectedSession = groupListView.getSelectionModel().getSelectedItem();
        if (selectedSession != null)
        {
            if (currentSession == selectedSession)
            {
                System.out.println("User select same group to chat.");
            }else{
                currentSession = selectedSession;
                chatRoomViewController.displaySessionData(selectedSession);
                System.out.println("The current session group selected : "+selectedSession);
            }
        }
    }

    public void addNewGroupSession(Session newGroupSession)
    {
        this.groups.add(newGroupSession);
    }
}
