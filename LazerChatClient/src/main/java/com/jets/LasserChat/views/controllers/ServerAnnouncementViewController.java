package com.jets.LasserChat.views.controllers;

import com.jets.LasserChat.models.entity.Session;
import com.jets.LasserChat.views.models.AnnouncementItemPane;
import com.jets.LasserChat.views.models.GroupItemPane;
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

public class ServerAnnouncementViewController implements Initializable
{
    @FXML
    private JFXListView<String> announceListView;

    private ChatRoomViewController chatRoomViewController;
    private ObservableList announcements;

    public ServerAnnouncementViewController(){}
    public ServerAnnouncementViewController(ChatRoomViewController chatRoomViewController){
        this.chatRoomViewController = chatRoomViewController;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        //to make user choose only one element at time
        announceListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        announceListView.setStyle("-fx-border-color: #40444A; -fx-background-color: #40444A;" + "-fx-control-inner-background : #40444A");
        //Simple customization
        announceListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                // Return ListCell after customizing its view
                return new ListCell<String>()
                {
                    AnnouncementItemPane announceNode = null;

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item != null) {
                            if (!empty) {
                                announceNode = new AnnouncementItemPane(item, null);

                                //Update cell data item
                                this.setUserData(item);
                                this.setItem(item);
                                //Update cell graphic Node
                                Platform.runLater(()-> setGraphic(announceNode));
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
                            this.announceNode.setStyle("-fx-background-color: RED");
                        } else {
                            this.announceNode.setStyle("-fx-background-color: #40444A");
                            this.setStyle("-fx-background-color:   #40444A");
                        }
                    }
                };
            }
        });
        announceListView.setItems(announcements);
    }

    public void addAnnouncementPane(String announceString) {
        announcements.add(announceString);
    }
}
