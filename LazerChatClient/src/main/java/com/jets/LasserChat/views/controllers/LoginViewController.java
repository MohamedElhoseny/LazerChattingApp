package com.jets.LasserChat.views.controllers;

import Animation.Transition.ShakeTransition;
import com.jets.LazerChatCommonService.models.entity.User;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginViewController implements Initializable
{
    @FXML private JFXTextField phoneTF;
    @FXML private JFXPasswordField passwordTF;
    @FXML private FontAwesomeIconView phone_error;
    @FXML private FontAwesomeIconView password_error;

    private StartupViewController startupViewController;

    public LoginViewController()
    {
        this.checkLoginHistroy();
    }

    private void checkLoginHistroy()
    {
        File file = new File("userInformation.txt");
        if (file.exists()) {
            BufferedReader reader = null;
            String[] data = new String[3];

            try {
                reader = new BufferedReader(new FileReader(file));

                for (int i = 0; i < 3; i++) {
                    data[i] = reader.readLine();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (data[2].equals("true")) {
                System.out.println(data[0] + ", " + data[1]);
                userLogin(data[0], data[1]);
            }
        }
    }

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
    void logIn(ActionEvent event) {
        userLogin(phoneTF.getText(), passwordTF.getText());
    }

    @FXML
    void logInUsingFacebook(ActionEvent event) {

    }

    @FXML
    void logInUsingGmail(ActionEvent event) {

    }

    private void userLogin(String phone, String password) {
        Platform.runLater(() -> {
            User loginUser = startupViewController.getLoginUser(phone, password);
            if (loginUser != null) {
                startupViewController.startChatRoom(loginUser);
            } else {
                //handle wrong user & password
                startupViewController.setFalseFlag(password_error);
            }
        });
    }
}
