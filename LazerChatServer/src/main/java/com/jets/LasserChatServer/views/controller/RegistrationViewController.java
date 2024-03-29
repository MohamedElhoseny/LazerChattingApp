package com.jets.LasserChatServer.views.controller;

import com.jets.LasserChatServer.views.utility.RegisterValidation;
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

    private RegisterValidation registerValidation;
    private File choosenImg;
    private AdminViewController adminViewController;

    public RegistrationViewController(AdminViewController adminViewController)
    {
        this.adminViewController = adminViewController;
        registerValidation = new RegisterValidation();
        choosenImg = null;
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
        user.setGender("male");
        user.setName(userNameTF.getText());
        user.setCountry(userCountryTF.getText());
        user.setEmail(userEmailTF.getText());
        user.setPhone(userPhoneTF.getText());
        user.setPassword(userPasswordTF.getText());
        user.setDate(userBirthdayDP.getValue().toString());
        user.setBio(userBioTA.getText());
        //convertImage
        byte[] imgBytes;
        if (choosenImg != null) {
            imgBytes = convertImageToBytes(choosenImg);
            user.setPicture(imgBytes);
        }

        /*boolean registerAccepted = startupViewController.registerNewUser(user);
        if (registerAccepted) {
            System.out.println("Accepted");
            startupViewController.showLoginForm();
        }else
            System.out.println("Not Accepted !");
        */
    }


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        userGenderCB.getItems().add("Male");
        userGenderCB.getItems().add("Female");
        userGenderCB.getSelectionModel().select(0);
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
