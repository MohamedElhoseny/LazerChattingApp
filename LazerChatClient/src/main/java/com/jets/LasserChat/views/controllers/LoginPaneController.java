package com.jets.LasserChat.views.controllers;

import Animation.Transition.ShakeTransition;
import com.jets.LazerChatCommonService.models.entity.User;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginPaneController implements Initializable
{
    @FXML private JFXTextField phoneTF;
    @FXML private JFXPasswordField passwordTF;
    @FXML private FontAwesomeIconView phone_error;
    @FXML private FontAwesomeIconView password_error;

    private StartupPaneController parentController;

    public LoginPaneController(){}

    public void injectMainController(StartupPaneController startupPaneController)
    {
        this.parentController = startupPaneController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        phoneTF.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Integer.parseInt(phoneTF.getText());
                if (phoneTF.getText().length() == 11)
                    parentController.setTrueFlag(phone_error);
            }catch (NumberFormatException e){
                parentController.setFalseFlag(phone_error);
                new ShakeTransition(phone_error).play();
            }
        });

        passwordTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (passwordTF.getText().length() < 4)
                parentController.setFalseFlag(password_error);
            else
                parentController.setTrueFlag(password_error);
        });
    }

    @FXML
    private void showRegistrationForm(){
        parentController.showRegistrationForm();
    }


    @FXML
    void logIn(ActionEvent event)
    {
        String phone = phoneTF.getText();
        String password = passwordTF.getText();

        User loginUser = parentController.getLoginUser(phone, password);

        if (loginUser != null)
            parentController.startChatRoom(loginUser);
        else
            parentController.setFalseFlag(password_error);
    }

    @FXML
    void logInUsingFacebook(ActionEvent event) {

    }

    @FXML
    void logInUsingGmail(ActionEvent event) {

    }


}
