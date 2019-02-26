package com.jets.LasserChat.views.models;

import com.jets.LasserChat.views.controllers.ChatRoomViewController;
import com.jets.LazerChatCommonService.models.entity.User;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.io.ByteArrayInputStream;

public class FriendRequestItem extends AnchorPane {
    private final Circle profileImg;
    private final Label label;
    private final Label label0;
    private final HBox hBox;
    private final JFXButton jFXButton;
    private final JFXButton jFXButton0;

    User senderUser;
    ChatRoomViewController chatRoomViewController;

    public FriendRequestItem(User senderUser, ChatRoomViewController chatRoomViewController) {
        this.chatRoomViewController = chatRoomViewController;
        this.senderUser = senderUser;
        profileImg = new Circle();
        label = new Label();
        label0 = new Label();
        hBox = new HBox();
        jFXButton = new JFXButton();
        jFXButton0 = new JFXButton();

        setPrefHeight(85.0);
        setPrefWidth(246.0);

        profileImg.setLayoutX(39.0);
        profileImg.setLayoutY(36.0);
        profileImg.setRadius(25.0);
        profileImg.setStroke(javafx.scene.paint.Color.BLACK);
        profileImg.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
        Image image = new Image(new ByteArrayInputStream(senderUser.getPicture()));
        profileImg.setFill(new ImagePattern(image));

        label.setLayoutX(73.0);
        label.setLayoutY(14.0);
        label.setPrefHeight(26.0);
        label.setPrefWidth(115.0);
        label.setText(senderUser.getPhone());
        label.setTextFill(javafx.scene.paint.Color.WHITE);
        label.setFont(new Font("Arial Black", 12.0));

        label0.setLayoutX(174.0);
        label0.setLayoutY(15.0);
        label0.setPrefHeight(24.0);
        label0.setPrefWidth(68.0);
        label0.setText(senderUser.getName());
        label0.setTextFill(javafx.scene.paint.Color.WHITE);
        label0.setFont(new Font("Arial", 12.0));

        AnchorPane.setBottomAnchor(hBox, 10.0);
        AnchorPane.setLeftAnchor(hBox, 73.0);
        hBox.setLayoutX(73.0);
        hBox.setLayoutY(60.0);
        hBox.setSpacing(10.0);
        jFXButton.setText("Accept");
        jFXButton.setOnAction(this::acceptRequest);
        jFXButton.setStyle("-fx-background-color: #79C952;");
        jFXButton.setFont(new Font("System Bold", 12.0));

        jFXButton0.setText("Reject");
        jFXButton0.setOnAction(this::rejectRequest);
        jFXButton0.setStyle("-fx-background-color: #D53F46;");
        jFXButton0.setFont(new Font("System Bold", 12.0));
        setCursor(Cursor.HAND);

        getChildren().add(profileImg);
        getChildren().add(label);
        getChildren().add(label0);
        hBox.getChildren().add(jFXButton);
        hBox.getChildren().add(jFXButton0);
        getChildren().add(hBox);

    }

    public void acceptRequest(ActionEvent event) {
        if (chatRoomViewController.addNewContact(senderUser)) {
            jFXButton.setVisible(false);
            jFXButton0.setVisible(false);
            Label label = new Label("Accepted");
            label.setLayoutX(73.0);
            label.setLayoutY(60.0);
            getChildren().add(label);
        }
    }

    public void rejectRequest(ActionEvent event) {
        chatRoomViewController.deleteFriendRequest(senderUser);
        jFXButton.setVisible(false);
        jFXButton0.setVisible(false);
        Label label = new Label("Rejected");
        label.setLayoutX(73.0);
        label.setLayoutY(60.0);
        getChildren().add(label);
    }
}