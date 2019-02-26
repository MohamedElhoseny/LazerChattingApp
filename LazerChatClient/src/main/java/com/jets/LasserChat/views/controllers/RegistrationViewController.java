package com.jets.LasserChat.views.controllers;

import com.jets.LasserChat.views.utility.RegisterValidation;
import com.jets.LazerChatCommonService.models.entity.User;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class RegistrationViewController implements Initializable
{
    @FXML private ImageView userImgIV;
    @FXML private JFXTextField userPhoneTF;
    @FXML private FontAwesomeIconView phone_error;
    @FXML private JFXTextField userNameTF;
    @FXML private FontAwesomeIconView name_error;
    @FXML private JFXPasswordField userPasswordTF;
    @FXML private FontAwesomeIconView password_error;
    @FXML private JFXPasswordField userConfirmPasswordTF;
    @FXML private FontAwesomeIconView confirmPassword_error;
    @FXML private JFXTextField userEmailTF;
    @FXML private FontAwesomeIconView email_error;
    @FXML private ComboBox<String> userCountryCB;
    @FXML private FontAwesomeIconView country_error;
    @FXML private FontAwesomeIconView date_error;
    @FXML private DatePicker userBirthdayDP;
    @FXML private TextArea userBioTA;
    @FXML private ComboBox<String> userGenderCB;

    private StartupViewController startupViewController;
    private RegisterValidation registerValidation;
    private File choosenImg;
    private File defaultImg;
    private boolean isAccept;
    private boolean isDateAccept;
    private boolean isCountryAccept;

    public RegistrationViewController()
    {
        registerValidation = new RegisterValidation();
        choosenImg = null;
        defaultImg = new File("src/main/resources/images/default-avatar.png");
    }

    public void injectMainController(StartupViewController startupViewController)
    {
        this.startupViewController = startupViewController;
    }

    @FXML
    void openImageChooser(ActionEvent event)
    {
        Image imageFile;
        InputStream stream = null;
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open File Chooser");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
            choosenImg = fileChooser.showOpenDialog(null);
            if (choosenImg != null) {
                stream = new FileInputStream(choosenImg);
                imageFile = new Image(stream);
                userImgIV.setImage(imageFile);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Error while choosing file : "+ ex.getMessage());
        } finally {
            try {
                if (stream != null)
                    stream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    void registerUser(ActionEvent event)
    {
        if (userBirthdayDP.getValue() == null) {
            isDateAccept = false;
            startupViewController.setFalseFlag(date_error);
        }else {
            isDateAccept = true;
            startupViewController.setTrueFlag(date_error);
        }

        if (userCountryCB.getSelectionModel().getSelectedItem() == null) {
            isCountryAccept = false;
            startupViewController.setFalseFlag(country_error);
        }else {
            isCountryAccept = true;
            startupViewController.setTrueFlag(country_error);
        }


        if (isAccept && isDateAccept && isCountryAccept) {
            User user = new User();
            user.setName(userNameTF.getText());
            user.setCountry(userCountryCB.getSelectionModel().getSelectedItem());
            user.setGender(userGenderCB.getSelectionModel().getSelectedItem());
            user.setEmail(userEmailTF.getText());
            user.setPhone(userPhoneTF.getText());
            user.setPassword(userPasswordTF.getText());
            user.setDate(userBirthdayDP.getValue().toString());
            user.setBio(userBioTA.getText());
            //convertImage
            byte[] imgBytes;
            if (choosenImg != null) imgBytes = convertImageToBytes(choosenImg);
            else imgBytes = convertImageToBytes(defaultImg);

            user.setPicture(imgBytes);

            boolean registerAccepted = startupViewController.registerNewUser(user);
            if (registerAccepted) {
                System.out.println("Accepted");
                startupViewController.showLoginForm();
            }
        }else
            System.out.println("Not Accepted !");
    }

    @FXML
    private void showLoginForm(ActionEvent event) {
        this.startupViewController.showLoginForm();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        userGenderCB.getItems().add("Male");
        userGenderCB.getItems().add("Female");
        userGenderCB.getSelectionModel().select(0);

        String[] country = Locale.getISOCountries();
        for (String s : country) {
            Locale locale = new Locale("", s);
        }
        userCountryCB.getItems().addAll(country);
        userCountryCB.setEditable(false);

        userNameTF.textProperty().addListener(e ->
        {
            if (registerValidation.validateName(userNameTF.getText()) && userNameTF.getText().length() > 4) {
                startupViewController.setTrueFlag(name_error);
                isAccept = true;
            }else {
                startupViewController.setFalseFlag(name_error);
                isAccept = false;
            }
        });

        userEmailTF.textProperty().addListener(e -> {
            if (registerValidation.validateEmail(userEmailTF.getText())) {
                startupViewController.setTrueFlag(email_error);
                isAccept = true;
            }else {
                isAccept = false;
                startupViewController.setFalseFlag(email_error);
            }
        });
        userPhoneTF.textProperty().addListener(e ->
        {
            if (registerValidation.validatePhone(userPhoneTF.getText())) {
                isAccept = true;
                startupViewController.setTrueFlag(phone_error);
            } else {
                startupViewController.setFalseFlag(phone_error);
                isAccept = false;
            }
        });
        userPasswordTF.textProperty().addListener(e ->
        {
            if (registerValidation.validatePassword(userPasswordTF.getText())) {
                isAccept = true;
                startupViewController.setTrueFlag(password_error);
            }else {
                isAccept = false;
                startupViewController.setFalseFlag(password_error);}
        });
        userConfirmPasswordTF.textProperty().addListener(e -> {
            if (registerValidation.matchPassword(userPasswordTF.getText(), userConfirmPasswordTF.getText())) {
                startupViewController.setTrueFlag(confirmPassword_error);
                isAccept = true;
            }else {
                isAccept  = false;
                startupViewController.setFalseFlag(confirmPassword_error);
            }
        });

    }

    private byte[] convertImageToBytes(File choosenImg)
    {
        BufferedImage image = null;
        try {
            image = ImageIO.read(choosenImg);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            return null;
        }
    }
}
