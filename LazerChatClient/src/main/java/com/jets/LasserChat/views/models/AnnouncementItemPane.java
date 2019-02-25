package com.jets.LasserChat.views.models;

import com.jets.LazerChatCommonService.models.entity.Annoncement;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;

import java.io.ByteArrayInputStream;

public class AnnouncementItemPane extends AnchorPane
{
    protected final Label announceText;
    protected final StackPane stackPane;
    protected final ImageView announceImg;

    public AnnouncementItemPane(Annoncement announcement)
    {
        announceText = new Label();
        stackPane = new StackPane();
        announceImg = new ImageView();

        setMaxHeight(253.0);
        setMaxWidth(241.0);
        setMinHeight(253.0);
        setMinWidth(241.0);
        setStyle("-fx-border-color: transparent  gray gray transparent;");

        AnchorPane.setBottomAnchor(announceText, 0.0);
        AnchorPane.setLeftAnchor(announceText, 26.0);
        AnchorPane.setRightAnchor(announceText, 16.0);
        AnchorPane.setTopAnchor(announceText, 154.0);
        announceText.setAlignment(javafx.geometry.Pos.TOP_LEFT);
        announceText.setLayoutX(27.0);
        announceText.setLayoutY(156.0);
        announceText.setPrefHeight(98.0);
        announceText.setPrefWidth(212.0);
        announceText.setText(announcement.getAnnoncementText());
        Image img = new Image(new ByteArrayInputStream(announcement.getImage()));
        announceImg.setImage(img);
        announceText.setTextFill(javafx.scene.paint.Color.WHITE);
        announceText.setWrapText(true);
        announceText.setFont(new Font("Arial Black", 12.0));

        AnchorPane.setLeftAnchor(stackPane, 20.0);
        AnchorPane.setRightAnchor(stackPane, 20.0);
        AnchorPane.setTopAnchor(stackPane, 14.0);
        stackPane.setLayoutX(27.0);
        stackPane.setLayoutY(14.0);
        stackPane.setPrefHeight(128.0);
        stackPane.setPrefWidth(195.0);
        stackPane.setStyle("-fx-border-color: silver;");

        //Image img = new Image(new ByteArrayInputStream(announcePic));
        //announceImg.setImage(img);
        announceImg.setFitHeight(126.0);
        announceImg.setFitWidth(188.0);
        announceImg.setPickOnBounds(true);
        announceImg.setPreserveRatio(false);
        setCursor(Cursor.HAND);

        getChildren().add(announceText);
        stackPane.getChildren().add(announceImg);
        getChildren().add(stackPane);

    }
}