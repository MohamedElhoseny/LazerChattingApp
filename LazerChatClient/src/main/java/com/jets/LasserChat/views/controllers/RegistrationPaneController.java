package com.jets.LasserChat.views.controllers;

import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationPaneController
{
    @FXML private ImageView userImgIV;
    @FXML private JFXTextField userPhoneTF;
    @FXML private FontAwesomeIconView phone_error;
    @FXML private JFXTextField userNameTF;
    @FXML private FontAwesomeIconView name_error;
    @FXML private JFXTextField userPasswordTF;
    @FXML private FontAwesomeIconView password_error;
    @FXML private JFXTextField userConfirmPasswordTF;
    @FXML private FontAwesomeIconView confirmPassword_error;
    @FXML private JFXTextField userEmailTF;
    @FXML private FontAwesomeIconView email_error;
    @FXML private JFXTextField userCountryTF;
    @FXML private FontAwesomeIconView country_error;
    @FXML private DatePicker userBirthdayDP;
    @FXML private TextArea userBioTA;
    @FXML private ComboBox<String> userGenderCB;

    private StartupPaneController startupPaneController;

    public RegistrationPaneController(){}

    public void injectMainController(StartupPaneController startupPaneController)
    {
        this.startupPaneController = startupPaneController;
    }

    @FXML
    void openImageChooser(ActionEvent event) {

    }

    @FXML
    void registerUser(ActionEvent event) {

    }

    @FXML
    private void showLoginForm(ActionEvent event) {
        this.startupPaneController.showLoginForm();
    }
}
