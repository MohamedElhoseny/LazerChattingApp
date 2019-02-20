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
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
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
    @FXML private JFXTextField userCountryTF;
    @FXML private FontAwesomeIconView country_error;
    @FXML private DatePicker userBirthdayDP;
    @FXML private TextArea userBioTA;
    @FXML private ComboBox<String> userGenderCB;

    private StartupViewController startupViewController;
    private RegisterValidation registerValidation;
    private File choosenImg;

    public RegistrationViewController()
    {
        registerValidation = new RegisterValidation();
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
            stream = new FileInputStream(choosenImg);
            imageFile = new Image(stream);
            if (imageFile != null)
                userImgIV.setImage(imageFile);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
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
        //if (isAccept) {
            User user = new User();
            //dummy

            user.setGender("male");
            user.setName(userNameTF.getText());
            user.setCountry(userCountryTF.getText());
            user.setEmail(userEmailTF.getText());
            user.setPhone(userPhoneTF.getText());
            user.setPassword(userPasswordTF.getText());
            user.setDate(userBirthdayDP.getValue().toString());
            user.setBio(userBioTA.getText());

            try {
                BufferedImage bufferedImage = ImageIO.read(choosenImg);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "jpg", bos);
                byte[] imgBytes  = bos.toByteArray();

                user.setPicture(imgBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }

            boolean registerAccepted = startupViewController.registerNewUser(user);
            if (registerAccepted)
                //dummy
                System.out.println("Accepted");
            else
                //dummy
                System.out.println("Not Accepted !");
        //}
    }

    @FXML
    private void showLoginForm(ActionEvent event) {
        this.startupViewController.showLoginForm();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        userNameTF.textProperty().addListener(e->{
            if( registerValidation.validateName(userNameTF.getText()))
                startupViewController.setTrueFlag(name_error);
            else
                startupViewController.setFalseFlag(name_error);
        });
        userCountryTF.textProperty().addListener(e->{
            if (registerValidation.validateName(userCountryTF.getText()))
                startupViewController.setTrueFlag(country_error);
            else
                startupViewController.setFalseFlag(country_error);
        });
        userEmailTF.textProperty().addListener(e->{
            if( registerValidation.validateEmail(userEmailTF.getText()))
                startupViewController.setTrueFlag(email_error);
            else
                startupViewController.setFalseFlag(email_error);
        });
        userPhoneTF.textProperty().addListener(e->{
            if(registerValidation.validatePhone(userPhoneTF.getText()))
                startupViewController.setTrueFlag(phone_error);

            else
                startupViewController.setFalseFlag(phone_error);
        });
        userPasswordTF.textProperty().addListener(e->{
            if(registerValidation.validatePassword(userPasswordTF.getText()))
                startupViewController.setTrueFlag(password_error);
            else
                startupViewController.setFalseFlag(password_error);
        });
        userConfirmPasswordTF.textProperty().addListener(e->{
            if(registerValidation.matchPassword(userPasswordTF.getText(), userConfirmPasswordTF.getText()))
                startupViewController.setTrueFlag(confirmPassword_error);
            else
                startupViewController.setFalseFlag(confirmPassword_error);
        });
    }

    private byte[] convertImageToBytes(Image object)
    {
        /*BufferedImage image = null;
        try {
            image = ImageIO.read();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[] res=baos.toByteArray();
            return res;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }*/
        return null;
    }
}
