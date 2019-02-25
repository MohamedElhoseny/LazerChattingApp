package com.jets.LasserChat.views.models;

import com.jets.LazerChatCommonService.models.entity.Message;
import com.jets.LazerChatCommonService.models.entity.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
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
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;

import javax.management.Notification;
import java.io.ByteArrayInputStream;
import java.io.File;

public class FileMessagePane extends AnchorPane implements MessagePane
{
    Circle profileImg;
    VBox vBox;
    AnchorPane anchorPane;
    Label label;
    FontAwesomeIconView file;
    Label fileName;
    JFXSpinner spinner;
    Label fileState;
    HBox hBox;
    JFXButton acceptBtn;
    FontAwesomeIconView accept;
    JFXButton rejectBtn;
    FontAwesomeIconView reject;
    HBox hBox0;
    FontAwesomeIconView msgState;
    Label label0;


    //members
    User senderUser;
    String sendingFile;

   public static String savePath=null;
   public static boolean responce=false;

  public FileMessagePane(){}

    public FileMessagePane(User user, String senderFile, boolean isSentbyMe)
    {
        profileImg = new Circle();
        vBox = new VBox();
        anchorPane = new AnchorPane();
        label = new Label();
        file = new FontAwesomeIconView();
        fileName = new Label();
        spinner = new JFXSpinner();
        fileState = new Label();
        hBox = new HBox();
        acceptBtn = new JFXButton();
        accept = new FontAwesomeIconView();
        rejectBtn = new JFXButton();
        reject = new FontAwesomeIconView();
        hBox0 = new HBox();
        msgState = new FontAwesomeIconView();
        label0 = new Label();

        setPrefHeight(82.0);
        setPrefWidth(517.0);

        AnchorPane.setLeftAnchor(profileImg, 14.0);
        AnchorPane.setTopAnchor(profileImg, 3.0);
        profileImg.setLayoutX(32.0);
        profileImg.setLayoutY(21.0);
        profileImg.setRadius(18.0);
        profileImg.setStroke(Color.BLACK);
        profileImg.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);

        AnchorPane.setLeftAnchor(vBox, 57.0);
        AnchorPane.setRightAnchor(vBox, 0.0);
        AnchorPane.setTopAnchor(vBox, 3.0);
        vBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        vBox.setLayoutX(57.0);
        vBox.setLayoutY(3.0);
        vBox.setPrefWidth(453.0);

        VBox.setVgrow(anchorPane, javafx.scene.layout.Priority.ALWAYS);
        anchorPane.setMaxWidth(USE_PREF_SIZE);
        anchorPane.setPrefHeight(54.0);
        anchorPane.setPrefWidth(200.0);

        AnchorPane.setBottomAnchor(label, 0.0);
        AnchorPane.setLeftAnchor(label, 0.0);
        AnchorPane.setTopAnchor(label, 0.0);
        file.setGlyphName(FontAwesomeIcon.FILE.name());
        file.setSize("3em");
        file.setFill(Color.SILVER);
        label.setAlignment(javafx.geometry.Pos.CENTER);
        label.setLayoutX(-2.0);
        label.setMaxWidth(200.0);
        label.setPrefHeight(61.0);
        label.setPrefWidth(57.0);
        label.setStyle("-fx-background-color: Transparent; -fx-border-radius: 10; -fx-background-radius: 10;");
        label.setWrapText(true);
        label.setFont(new Font("System Italic", 14.0));
        label.setPadding(new Insets(10.0));

        label.setGraphic(file);

        AnchorPane.setLeftAnchor(fileName, 58.0);
        AnchorPane.setRightAnchor(fileName, 0.0);
        AnchorPane.setTopAnchor(fileName, 2.0);
        fileName.setLayoutX(51.0);
        fileName.setLayoutY(2.0);
        fileName.setPrefHeight(25.0);
        fileName.setPrefWidth(138.0);
        fileName.setText(senderFile);
        fileName.setFont(new Font(16.0));

        AnchorPane.setBottomAnchor(spinner, 12.0);
        AnchorPane.setRightAnchor(spinner, 10.0);
        AnchorPane.setTopAnchor(spinner, 14.0);
        spinner.setLayoutX(149.0);
        spinner.setLayoutY(15.0);
        spinner.setPrefSize(44,26);
        spinner.setVisible(false);
        AnchorPane.setBottomAnchor(fileState, 13.0);
        AnchorPane.setLeftAnchor(fileState, 59.0);
        fileState.setLayoutX(60.0);
        fileState.setLayoutY(29.0);

        AnchorPane.setBottomAnchor(hBox, 5.0);
        AnchorPane.setLeftAnchor(hBox, 52.0);
        AnchorPane.setTopAnchor(hBox, 30.0);
        hBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        hBox.setLayoutX(56.0);
        hBox.setLayoutY(26.0);
        hBox.setPrefHeight(25.0);
        hBox.setPrefWidth(88.0);
        hBox.setSpacing(5.0);
        Image img = new Image(new ByteArrayInputStream(user.getPicture()));
        profileImg.setFill(new ImagePattern(img));
        acceptBtn.setGraphic(accept);
        rejectBtn.setLayoutX(10.0);
        rejectBtn.setLayoutY(10.0);
        rejectBtn.setGraphic(reject);
        hBox.setPadding(new Insets(0.0, 0.0, 0.0, 5.0));

        AnchorPane.setBottomAnchor(hBox0, 0.0);
        AnchorPane.setLeftAnchor(hBox0, 60.0);
        AnchorPane.setTopAnchor(hBox0, 70.0);

        //FontAwesome
        msgState.setGlyphName(FontAwesomeIcon.CHECK_CIRCLE.name());
        msgState.setSize("1.2em");
        msgState.setFill(Paint.valueOf("#ff4444"));

        reject.setGlyphName(FontAwesomeIcon.TIMES.name());
        reject.setFill(Paint.valueOf("#ff4444"));
        reject.setSize("1.2em");

        accept.setGlyphName(FontAwesomeIcon.CHECK.name());
        accept.setFill(Paint.valueOf("#25e45b"));
        accept.setSize("1.2em");


        label0.setAlignment(javafx.geometry.Pos.CENTER);
        label0.setText("4 : 30 PM");
        label0.setPadding(new Insets(0.0, 0.0, 0.0, 10.0));
        hBox0.setPadding(new Insets(2.0, 0.0, 0.0, 0.0));


        if (isSentbyMe)
        {
            anchorPane.setStyle("-fx-background-color: #9CCC65; -fx-border-color: gray; -fx-background-radius: 10; -fx-border-radius: 10;");
            setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            hBox.setVisible(false);
            fileState.setText("File is sent.");
            fileState.setVisible(true);
        }else {
            anchorPane.setStyle("-fx-background-color: White; -fx-border-color: silver; -fx-background-radius: 10; -fx-border-radius: 10;");
            setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            fileState.setVisible(false);
            hBox.setVisible(true);
        }

        getChildren().add(profileImg);
        anchorPane.getChildren().add(label);
        anchorPane.getChildren().add(fileName);
        anchorPane.getChildren().add(spinner);
        anchorPane.getChildren().add(fileState);
        hBox.getChildren().add(acceptBtn);
        hBox.getChildren().add(rejectBtn);
        anchorPane.getChildren().add(hBox);
        vBox.getChildren().add(anchorPane);
        getChildren().add(vBox);
        hBox0.getChildren().add(msgState);
        hBox0.getChildren().add(label0);
        getChildren().add(hBox0);
        initComponentEvent();
    }

    private void cancelSending()
    {
        hBox.setVisible(false);
        spinner.setVisible(false);
        sendingFile = null;
        fileState.setText("File cancelled.");
        fileState.setVisible(true);
        responce=true;

    }

    /***
     * Responsible for accepting file transfer
     */
    private void acceptSendingFile()
    {

        hBox.setVisible(false);
        spinner.setVisible(true);
        fileState.setText("File is sending..");
        fileState.setVisible(true);

        //choose directory and send it to main controller
        DirectoryChooser directoryChooser = new DirectoryChooser();

        directoryChooser.setTitle("choose Location you Want to save file ");

        //Show open file dialog
        File file = directoryChooser.showDialog(null);

        if (file != null) {
            savePath=file.getPath();
        }
        responce=true;

    }

    public void notifySendingFile()
    {
        spinner.setVisible(false);

    }

    private void initComponentEvent()
    {
        acceptBtn.setOnAction(e -> {
            acceptSendingFile();
        });

        rejectBtn.setOnAction(e -> {
            cancelSending();
        });
    }

    @Override
    public void setMessageState(Message.MessageState messageState) {
        switch (messageState) {
            case UNDELIVERED:
                msgState.setFill(Color.valueOf("#49b9bb"));
                break;

            case SEEN:
                msgState.setFill(Color.valueOf("#2bc630"));
                break;

            case DELIVERED:
                msgState.setFill(Color.valueOf("#a2aba2"));
                break;
        }
    }

    public String getDirectory(){
        return savePath;
    }
    public boolean isResponce(){
        return responce;
    }
}