package com.jets.LasserChat.views.controllers;

import com.jets.LasserChat.models.entity.Session;
import com.jets.LazerChatCommonService.models.entity.User;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
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

    public FriendChatViewController()
    {

    }

    public FriendChatViewController(ChatRoomViewController chatRoomViewController)
    {
        this.chatRoomViewController = chatRoomViewController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        //to make user choose only one element at time
        friendListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        friendListView.setStyle("-fx-border-color: #40444A; -fx-background-color: #40444A;" +
                "-fx-control-inner-background : #40444A");


        //Simple customization
        friendListView.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
            @Override
            public ListCell<User> call(ListView<User> param) {
                // Return ListCell after customizing its view
                return new ListCell<User>()
                {
                    FriendItemPane contactNode;

                    @Override
                    protected void updateItem(User item, boolean empty)
                    {
                        super.updateItem(item, empty);

                        if (item != null)
                        {
                            if (!empty)
                            {
                                Platform.runLater(() -> {
                                    contactNode = new FriendItemPane(item);

                                    //Update cell data item
                                    this.setUserData(item);

                                    //Update cell graphic Node
                                    this.setGraphic(contactNode);
                                });
                            }else
                            {
                                this.contactNode = null;
                                this.setUserData(null);
                                this.setGraphic(null);
                            }
                        }else{
                            this.contactNode = null;
                            this.setUserData(null);
                            this.setGraphic(null);
                        }

                    }

                    @Override
                    public void updateSelected(boolean selected)
                    {
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

        friendListView.setOnMouseClicked((mouseEvent) -> {
            User selectedUser = friendListView.getSelectionModel().getSelectedItem();
            System.out.println("User selected to chatting : "+selectedUser);
            Session session = chatRoomViewController.loadSessionData(selectedUser);
            chatRoomViewController.displaySessionData(session);
        });


        //Get user friend list to display it
        friendList = chatRoomViewController.getUserFriendList();

        friendListView.setItems(friendList);
    }

    private class FriendItemPane extends AnchorPane
    {
        final Circle profileImg21;
        final Label label;
        final Circle circle;
        final Label label0;
        final Label label1;

        public FriendItemPane(User user)
        {
            profileImg21 = new Circle();
            label = new Label();
            circle = new Circle();
            label0 = new Label();
            label1 = new Label();

            setPrefHeight(72.0);
            setPrefWidth(232.0);

            profileImg21.setFill(javafx.scene.paint.Color.WHITE);
            profileImg21.setLayoutX(39.0);
            profileImg21.setLayoutY(36.0);
            profileImg21.setRadius(25.0);
            profileImg21.setStroke(javafx.scene.paint.Color.BLACK);
            profileImg21.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

            label.setLayoutX(73.0);
            label.setLayoutY(14.0);
            label.setPrefHeight(26.0);
            label.setPrefWidth(138.0);
            label.setText(user.getName());
            label.setTextFill(javafx.scene.paint.Color.WHITE);
            label.setFont(new Font("Arial Black", 12.0));

            circle.setFill(javafx.scene.paint.Color.valueOf("#da8b31"));
            circle.setLayoutX(79.0);
            circle.setLayoutY(48.0);
            circle.setRadius(6.0);
            circle.setStroke(javafx.scene.paint.Color.BLACK);
            circle.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

            label0.setLayoutX(92.0);
            label0.setLayoutY(36.0);
            label0.setPrefHeight(24.0);
            label0.setPrefWidth(49.0);
            label0.setText("Busy");
            label0.setTextFill(javafx.scene.paint.Color.WHITE);
            label0.setFont(new Font("Arial", 12.0));

            AnchorPane.setRightAnchor(label1, 20.0);
            label1.setAlignment(javafx.geometry.Pos.CENTER);
            label1.setContentDisplay(javafx.scene.control.ContentDisplay.CENTER);
            label1.setLayoutX(211.0);
            label1.setLayoutY(32.0);
            label1.setPrefHeight(21.0);
            label1.setPrefWidth(22.0);
            label1.setStyle("-fx-background-color: #9dff92; -fx-border-radius: 20; -fx-background-radius: 20;");
            label1.setText("80");
            label1.setTextFill(javafx.scene.paint.Color.valueOf("#205b2b"));
            label1.setFont(new Font("System Bold", 12.0));
            setCursor(Cursor.HAND);

            getChildren().add(profileImg21);
            getChildren().add(label);
            getChildren().add(circle);
            getChildren().add(label0);
            getChildren().add(label1);

        }
    }
}
