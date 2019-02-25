package com.jets.LasserChat.views.models;

import com.jets.LasserChat.models.entity.Session;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.io.ByteArrayInputStream;

public class GroupItemPane extends AnchorPane {

    protected final Label groupIconLabel;
    protected final FontAwesomeIconView fontAwesomeIconView;
    protected final HBox hBox;
    protected final Circle profileImg1;
    protected final Circle profileImg2;
    protected final Circle profileImg3;
    protected final Circle profileImg4;
    protected final Label groupName;

    public GroupItemPane(Session groupSession)
    {

        groupIconLabel = new Label();
        fontAwesomeIconView = new FontAwesomeIconView();
        hBox = new HBox();
        profileImg1 = new Circle();
        profileImg2 = new Circle();
        profileImg3 = new Circle();
        profileImg4 = new Circle();
        groupName = new Label();

        setPrefHeight(77.0);
        setPrefWidth(254.0);
        setStyle("-fx-background-color: transparent;");

        AnchorPane.setBottomAnchor(groupIconLabel, 9.0);
        AnchorPane.setLeftAnchor(groupIconLabel, 10.0);
        AnchorPane.setTopAnchor(groupIconLabel, 11.0);
        groupIconLabel.setAlignment(javafx.geometry.Pos.CENTER);
        groupIconLabel.setContentDisplay(javafx.scene.control.ContentDisplay.GRAPHIC_ONLY);
        groupIconLabel.setLayoutX(14.0);
        groupIconLabel.setLayoutY(11.0);
        groupIconLabel.setPrefHeight(57.0);
        groupIconLabel.setPrefWidth(58.0);
        groupIconLabel.setStyle("-fx-border-color: WHITE; -fx-background-radius: 80; -fx-border-radius: 80; -fx-border-width: 2;");
        groupIconLabel.setTextFill(javafx.scene.paint.Color.WHITE);
        fontAwesomeIconView.setGlyphName(FontAwesomeIcon.GROUP.name());
        fontAwesomeIconView.setSize("20");
        fontAwesomeIconView.setFill(Color.WHITE);
        groupIconLabel.setGraphic(fontAwesomeIconView);

        AnchorPane.setBottomAnchor(hBox, 7.0);
        AnchorPane.setLeftAnchor(hBox, 78.0);
        AnchorPane.setRightAnchor(hBox, 0.0);
        hBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        hBox.setLayoutX(78.0);
        hBox.setPrefHeight(32.0);
        hBox.setPrefWidth(176.0);
        hBox.setSpacing(2.0);
        hBox.setStyle("-fx-background-color: transparent;");

        profileImg1.setFill(javafx.scene.paint.Color.WHITE);
        profileImg1.setRadius(16.0);

        profileImg2.setFill(javafx.scene.paint.Color.WHITE);
        profileImg2.setRadius(16.0);

        profileImg3.setFill(javafx.scene.paint.Color.WHITE);
        profileImg3.setRadius(16.0);


        profileImg4.setFill(javafx.scene.paint.Color.WHITE);
        profileImg4.setLayoutX(90.0);
        profileImg4.setLayoutY(26.0);
        profileImg4.setRadius(16.0);

        Circle[] imgs = {profileImg1,profileImg2,profileImg3,profileImg4};
        for (int i=0; ((i<groupSession.getAvailableUsers().size()) && (i<imgs.length)); i++)
        {
            byte[] userImg = groupSession.getAvailableUsers().get(i).getPicture();
            Image img = new Image(new ByteArrayInputStream(userImg));
            imgs[i].setFill(new ImagePattern(img));
        }


        AnchorPane.setLeftAnchor(groupName, 80.0);
        AnchorPane.setRightAnchor(groupName, 0.0);
        groupName.setLayoutX(78.0);
        groupName.setLayoutY(14.0);
        groupName.setText("Group id : "+groupSession.getId());
        groupName.setTextFill(javafx.scene.paint.Color.WHITE);
        groupName.setFont(new Font("Arial Bold", 12.0));
        setCursor(Cursor.HAND);

        getChildren().add(groupIconLabel);
        hBox.getChildren().add(profileImg1);
        hBox.getChildren().add(profileImg2);
        hBox.getChildren().add(profileImg3);
        hBox.getChildren().add(profileImg4);
        getChildren().add(hBox);
        getChildren().add(groupName);

    }
}
