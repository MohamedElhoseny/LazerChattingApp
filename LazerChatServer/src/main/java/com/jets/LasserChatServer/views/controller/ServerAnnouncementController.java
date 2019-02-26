package com.jets.LasserChatServer.views.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ServerAnnouncementController implements Initializable
{
    @FXML
    private ImageView userImgIV;

    @FXML
    private JFXButton chooseImg;

    @FXML
    private TextArea announceTA;

    @FXML private Label errorMsg;

    AdminViewController adminViewController;
    File file;

    public ServerAnnouncementController(AdminViewController controller){
        adminViewController = controller;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void chooseImgFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(new Stage());
        Image image = new Image(file.toURI().toString());
        userImgIV.setImage(image);
    }
    @FXML
    public void broadcastAnnouncement(ActionEvent event)
    {
        boolean check = true;
        if (file == null) {
            errorMsg.setText("*Please choose a valid image ! ");
            errorMsg.setVisible(true);
            check = false;
        }
        if (announceTA.getText().isEmpty()) {
            errorMsg.setText("*Please enter a description for announcement ! ");
            errorMsg.setVisible(true);
            check = false;
        }
        if (check) {
            announceTA.setText("Announcement sent to all online clients");
            userImgIV.setImage(null);
            adminViewController.sendAnnouncement(file, announceTA.getText());
            errorMsg.setVisible(false);
        }
    }
}
