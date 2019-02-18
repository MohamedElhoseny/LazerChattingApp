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

public class RegistrationPaneController implements Initializable
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

    private StartupPaneController startupPaneController;
    private RegisterValidation registerValidation;

    public RegistrationPaneController()
    {
        registerValidation = new RegisterValidation();
    }

    public void injectMainController(StartupPaneController startupPaneController)
    {
        this.startupPaneController = startupPaneController;
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
            File selectedImage = fileChooser.showOpenDialog(null);
            stream = new FileInputStream(selectedImage);
            imageFile = new Image(stream);
            userImgIV.setImage(imageFile);
            Image image = new Image(selectedImage.toURI().toString());
            userImgIV.setImage(image);
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
    void registerUser(ActionEvent event) {
        //if (isAccept) {
            User user = new User();
            //dummy
            user.setId(1001);
            user.setGender(1);

            user.setName(userNameTF.getText());
            user.setCountry(userCountryTF.getText());
            user.setEmail(userEmailTF.getText());
            user.setPhone(userPhoneTF.getText());
            user.setPassword(userPasswordTF.getText());
            user.setDate(userBirthdayDP.getValue().toString());
            user.setBio(userBioTA.getText());
            user.setPicture(convertImageToBytes(userImgIV.getImage()));
            //Image img = new Image(new ByteArrayInputStream(buffer));   to read image from array of bytes

            boolean registerAccepted = startupPaneController.registerNewUser(user);
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
        this.startupPaneController.showLoginForm();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        userNameTF.textProperty().addListener(e->{
            if( registerValidation.validateName(userNameTF.getText()))
                startupPaneController.setTrueFlag(name_error);
            else
                startupPaneController.setFalseFlag(name_error);
        });
        userCountryTF.textProperty().addListener(e->{
            if (registerValidation.validateName(userCountryTF.getText()))
                startupPaneController.setTrueFlag(country_error);
            else
                startupPaneController.setFalseFlag(country_error);
        });
        userEmailTF.textProperty().addListener(e->{
            if( registerValidation.validateEmail(userEmailTF.getText()))
                startupPaneController.setTrueFlag(email_error);
            else
                startupPaneController.setFalseFlag(email_error);
        });
        userPhoneTF.textProperty().addListener(e->{
            if(registerValidation.validatePhone(userPhoneTF.getText()))
                startupPaneController.setTrueFlag(phone_error);

            else
                startupPaneController.setFalseFlag(phone_error);
        });
        userPasswordTF.textProperty().addListener(e->{
            if(registerValidation.validatePassword(userPasswordTF.getText()))
                startupPaneController.setTrueFlag(password_error);
            else
                startupPaneController.setFalseFlag(password_error);
        });
        userConfirmPasswordTF.textProperty().addListener(e->{
            if(registerValidation.matchPassword(userPasswordTF.getText(), userConfirmPasswordTF.getText()))
                startupPaneController.setTrueFlag(confirmPassword_error);
            else
                startupPaneController.setFalseFlag(confirmPassword_error);
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
