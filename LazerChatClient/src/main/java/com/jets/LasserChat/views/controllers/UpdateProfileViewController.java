package com.jets.LasserChat.views.controllers;

import com.jets.LasserChat.models.remote.ServiceLocator;
import com.jets.LazerChatCommonService.models.dao.UserServices;
import com.jets.LazerChatCommonService.models.entity.User;

import com.jfoenix.controls.JFXDatePicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UpdateProfileViewController implements Initializable {
    @FXML
    private Circle userImgIV;

    @FXML
    private TextField userNameTF;

    @FXML
    private TextField userEmailTF;

    @FXML
    private TextField userPhoneTF;

    @FXML
    private TextField userCountryTF;

    @FXML
    private PasswordField userPasswordTF;

    @FXML
    private JFXDatePicker userBirthdayDP;

    private ChatRoomViewController chatRoomViewController;

    private File choosenImg;
    User user;
    UserServices userServices;


    public UpdateProfileViewController(ChatRoomViewController chatRoomViewController, User currentUser) {

        this.chatRoomViewController = chatRoomViewController;
        this.user=currentUser;




    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userNameTF.setText(user.getName());
        userEmailTF.setText(user.getEmail());
        userCountryTF.setText(user.getCountry());
        userPhoneTF.setText(user.getPhone());
        userPasswordTF.setText(user.getPassword());
        Image img = new Image(new ByteArrayInputStream(user.getPicture()));
        userImgIV.setFill(new ImagePattern(img));
        LocalDate localDate=LocalDate.parse(user.getDate());
        userBirthdayDP.setValue(localDate);

    }

    public void changeProfileImage(ActionEvent actionEvent) {
        Image imageFile;
        InputStream stream = null;
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open File Chooser");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", ".png", ".jpg"));
            choosenImg = fileChooser.showOpenDialog(null);
            if (choosenImg != null) {
                stream = new FileInputStream(choosenImg);
                imageFile = new Image(stream);
                userImgIV.setFill(new ImagePattern(imageFile));
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

    public void saveUpdatedInfo(ActionEvent actionEvent) {

        user.setGender("male");
        user.setName(userNameTF.getText());
        user.setCountry(userCountryTF.getText());
        user.setEmail(userEmailTF.getText());
        user.setPhone(userPhoneTF.getText());
        user.setPassword(userPasswordTF.getText());
        user.setDate(userBirthdayDP.getValue().toString());
        byte[] imgBytes = null;
        if (choosenImg != null) {
            imgBytes = convertImageToBytes(choosenImg);
        }

        user.setPicture(imgBytes);
        chatRoomViewController.updateProfileData(user);
        boolean updateAccepted = chatRoomViewController.updateUser(user);
    }

    private byte[] convertImageToBytes(File choosenImg) {
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