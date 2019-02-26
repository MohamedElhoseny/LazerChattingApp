package com.jets.LasserChat.views.models;

import com.jets.LazerChatCommonService.models.entity.Message;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.io.ByteArrayInputStream;

public class TextMessagePane extends AnchorPane implements MessagePane
{
    protected final Circle profileImg;
    protected final VBox vBox;
    protected final Label label;
    protected final HBox hBox;
    protected final FontAwesomeIconView fontAwesomeIconView;
    protected final Label label0;

    public TextMessagePane(Message message, boolean isSentbyMe)
    {
        profileImg = new Circle();
        vBox = new VBox();
        label = new Label();
        hBox = new HBox();
        fontAwesomeIconView = new FontAwesomeIconView();
        label0 = new Label();

        label.setFont(new Font(message.getMessageStyle().getFontFamily(), message.getMessageStyle().getSize()));
        label.setPadding(new Insets(10.0));
        if (message.getMessageStyle().isUnderline())
            label.setUnderline(true);

        StringBuilder customizeStyle = new StringBuilder();

        customizeStyle.append("-fx-border-radius: 10; -fx-background-radius: 10; -fx-text-fill : ")
                .append(message.getMessageStyle().getColor()).append("; ");
        if (message.getMessageStyle().isBold())
            customizeStyle.append("-fx-font-weight: bold; ");

        if (message.getMessageStyle().isItalic())
            customizeStyle.append("-fx-font-style: italic; ");

        if (isSentbyMe)
        {
            setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            customizeStyle.append(" -fx-background-color: #9CCC65; -fx-border-color: gray; ");
        }
        else
        {
            setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            customizeStyle.append("-fx-background-color: White; -fx-border-color: silver; ");
        }
        label.setStyle(customizeStyle.toString());
        setPickOnBounds(false);
        setPrefWidth(510.0);

        AnchorPane.setLeftAnchor(profileImg, 17.0);
        AnchorPane.setTopAnchor(profileImg, 0.0);
        profileImg.setLayoutX(35.0);
        profileImg.setLayoutY(18.0);
        profileImg.setRadius(18.0);
        profileImg.setStroke(javafx.scene.paint.Color.BLACK);
        Image img = new Image(new ByteArrayInputStream(message.getUser().getPicture()));
        profileImg.setFill(new ImagePattern(img));
        AnchorPane.setLeftAnchor(vBox, 60.0);
        AnchorPane.setRightAnchor(vBox, -3.0);
        AnchorPane.setTopAnchor(vBox, 2.0);
        vBox.setLayoutX(60.0);
        vBox.setLayoutY(7.0);
        vBox.setPrefWidth(453.0);
        fontAwesomeIconView.setGlyphName(FontAwesomeIcon.CHECK_CIRCLE.name());
        label.setMaxWidth(200.0);
        label.setText(message.getMessageString());
        label.setWrapText(true);


        label0.setAlignment(javafx.geometry.Pos.CENTER);
        label0.setText(message.getDate_time());
        label0.setPadding(new Insets(0.0, 0.0, 0.0, 10.0));
        hBox.setPadding(new Insets(2.0, 0.0, 0.0, 5.0));
        VBox.setMargin(hBox, new Insets(0.0));

        getChildren().add(profileImg);
        vBox.getChildren().add(label);
        hBox.getChildren().add(fontAwesomeIconView);
        hBox.getChildren().add(label0);
        vBox.getChildren().add(hBox);
        getChildren().add(vBox);

    }

    @Override
    public void setMessageState(Message.MessageState messageState)
    {
        switch (messageState)
        {
            case UNDELIVERED:
                fontAwesomeIconView.setFill(Color.valueOf("#49b9bb"));
                break;

            case SEEN:
                fontAwesomeIconView.setFill(Color.valueOf("#2bc630"));
                break;

            case DELIVERED:
                fontAwesomeIconView.setFill(Color.valueOf("#a2aba2"));
                break;
        }
    }
}
