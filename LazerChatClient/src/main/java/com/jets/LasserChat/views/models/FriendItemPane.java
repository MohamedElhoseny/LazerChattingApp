package com.jets.LasserChat.views.models;

import com.jets.LazerChatCommonService.models.entity.User;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.io.ByteArrayInputStream;

public class FriendItemPane extends AnchorPane {
    final Circle profileImg;
    final Label label;
    final Circle circle;
    final Label state;
    final Label label1;

    public FriendItemPane(User user)
    {
        profileImg = new Circle();
        label = new Label();
        circle = new Circle();
        state = new Label();
        label1 = new Label();

        setPrefHeight(72.0);
        setPrefWidth(232.0);

        profileImg.setFill(javafx.scene.paint.Color.WHITE);
        profileImg.setLayoutX(39.0);
        profileImg.setLayoutY(36.0);
        profileImg.setRadius(25.0);

        Image img = new Image(new ByteArrayInputStream(user.getPicture()));
        profileImg.setFill(new ImagePattern(img));

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

        state.setLayoutX(92.0);
        state.setLayoutY(36.0);
        state.setPrefHeight(24.0);
        state.setPrefWidth(49.0);
        state.setText("Busy");
        state.setTextFill(javafx.scene.paint.Color.WHITE);
        state.setFont(new Font("Arial", 12.0));

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

        /* Customizing pane*/
        getChildren().add(profileImg);
        getChildren().add(label);
        getChildren().add(circle);
        getChildren().add(state);
        getChildren().add(label1);

    }

    public void setUserState(int state)
    {
        switch (state){
            case 1:
                this.state.setText("available");
                break;
            case 2:
                this.state.setText("busy");
                break;
            case 3:
                this.state.setText("away");
                break;
            case 4:
                break;
        }
    }
}
