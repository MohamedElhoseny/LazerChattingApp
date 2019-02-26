package com.jets.LasserChatServer.views.controller;

import com.jets.LazerChatCommonService.models.entity.User;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ManageServiceController implements Initializable
{
    @FXML
    private JFXButton startBtn;

    @FXML
    private JFXButton stopBtn;

    @FXML
    private Label numOfOnline;

    @FXML
    private Label numOfOflline;

    @FXML private Label serverState;

    int onlineUser ;
    int offlineUser;
    AdminViewController adminViewController;

    public ManageServiceController(AdminViewController controller){
        adminViewController = controller;
        onlineUser = 0;
        offlineUser = 0;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            List<User> onlineUsers = adminViewController.getOnlineUsers();
            List<User> offlineUsers = adminViewController.getOfflineUsers();

             onlineUser = onlineUsers.size();
             offlineUser = offlineUsers.size();

            numOfOnline.setText(String.valueOf(onlineUser));
            numOfOflline.setText(String.valueOf(offlineUser));
        });
    }

    @FXML
    void startServer(ActionEvent event) {
        adminViewController.startServer();
        startBtn.setDisable(true);
        stopBtn.setDisable(false);
        serverState.setText("Running");
        serverState.setTextFill(Color.LIME);
    }

    @FXML
    void stopServer(ActionEvent event) {
        adminViewController.stopServer();
        startBtn.setDisable(false);
        stopBtn.setDisable(true);
        serverState.setText("Stopped");
        serverState.setTextFill(Color.RED);
    }

    public void incrementOnline()
    {
        Platform.runLater(()->{
            numOfOnline.setText(String.valueOf(++onlineUser));
            numOfOflline.setText(String.valueOf(--offlineUser));
        });

    }

    public void decrementOnline()
    {
        Platform.runLater(()->{
            numOfOnline.setText(String.valueOf(--onlineUser));
            numOfOflline.setText(String.valueOf(++offlineUser));
        });

    }
}
