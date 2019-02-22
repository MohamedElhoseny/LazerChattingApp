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

public class LoginViewController implements Initializable
{
    @FXML private JFXTextField phoneTF;
    @FXML private JFXPasswordField passwordTF;
    @FXML private FontAwesomeIconView phone_error;
    @FXML private FontAwesomeIconView password_error;

    private StartupViewController startupViewController;

    public LoginViewController(){}

    public void injectMainController(StartupViewController startupViewController)
    {
        this.startupViewController = startupViewController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        phoneTF.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Integer.parseInt(phoneTF.getText());
                if (phoneTF.getText().length() == 11)
                    startupViewController.setTrueFlag(phone_error);
            }catch (NumberFormatException e){
                startupViewController.setFalseFlag(phone_error);
                new ShakeTransition(phone_error).play();
            }
        });

        passwordTF.textProperty().addListener((observable, oldValue, newValue) -> {
            if (passwordTF.getText().length() < 4)
                startupViewController.setFalseFlag(password_error);
            else
                startupViewController.setTrueFlag(password_error);
        });
    }

    @FXML
    private void showRegistrationForm(){
        startupViewController.showRegistrationForm();
    }


    /**
     * Read phone, password from login scene and pass them to startupViewController
     * to pass them to startupMainController to call UserServices from server
     *              AND
     * Invoke startupViewController to show chat room scene
     */
    @FXML
    void logIn(ActionEvent event)
    {
        String phone = phoneTF.getText();
        String password = passwordTF.getText();

        User loginUser = startupViewController.getLoginUser(phone, password);

        if (loginUser != null)
        {
            System.out.println("New User is login to app : "+loginUser);
            startupViewController.startChatRoom(loginUser);
        }
        else
        {
            //handle wrong user & password
            startupViewController.setFalseFlag(password_error);
        }
    }

    @FXML
    void logInUsingFacebook(ActionEvent event) {

    }

    @FXML
    void logInUsingGmail(ActionEvent event) {

    }


}
