package com.jets.LasserChat.views.controllers;

import com.jets.LasserChat.views.utility.RegisterValidation;
import com.jets.LazerChatCommonService.models.entity.User;
import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class AddContactViewController implements Initializable {
    private ChatRoomViewController chatRoomViewController;
    private ObservableList<String> newContactList;
    private RegisterValidation registerValidation;
    private User loginUser;

    @FXML
    private TextField newContactTextField;

    @FXML
    private Label responseLabel;

    @FXML
    private JFXListView newContactListView;

    @FXML
    void closeAddContactPane(ActionEvent event) {
        chatRoomViewController.closeAddContactPane();
    }

    @FXML
    void addFriendToList(ActionEvent event) {
        String phoneNumber = newContactTextField.getText().trim();
        responseLabel.setVisible(false);
        if (!phoneNumber.equals("") && registerValidation.validatePhone(phoneNumber) && !phoneNumber.equals(loginUser.getPhone())) {
            User newContact = new User();
            newContact.setPhone(phoneNumber);
            if (chatRoomViewController.checkIfUserExist(newContact)) {
                if (!chatRoomViewController.checkIfFriends(loginUser, newContact)) {
                    if (!chatRoomViewController.checkIfPending(loginUser, newContact)) {
                        if (chatRoomViewController.checkIfPending(newContact, loginUser)) {
                            chatRoomViewController.addNewContact(loginUser, newContact);
                            newContactTextField.clear();
                            responseLabel.setVisible(true);
                            responseLabel.setText("Friend request Accepted");
                        } else {
                            if (!newContactList.contains(phoneNumber)) {
                                newContactTextField.clear();
                                newContactList.add(phoneNumber);
                            } else {
                                responseLabel.setVisible(true);
                                responseLabel.setText("User Added");
                            }
                        }
                    } else {
                        responseLabel.setVisible(true);
                        responseLabel.setText("Friend request is pending");
                    }
                } else {
                    responseLabel.setVisible(true);
                    responseLabel.setText("User is already in friend list");
                }
            } else {
                responseLabel.setVisible(true);
                responseLabel.setText("User not found");
            }
        } else {
            responseLabel.setVisible(true);
            responseLabel.setText("Add valid phone number");
        }

    }

    @FXML
    void saveFriends(ActionEvent event) {
        if (!newContactList.isEmpty()) {
            newContactList.forEach(phone -> {
                User newContact = new User();
                newContact.setPhone(phone);
                Platform.runLater(() -> {
                    if (chatRoomViewController.addFriendRequest(loginUser, newContact)) {
                        responseLabel.setVisible(true);
                        responseLabel.setText("Done");
                        newContactList.clear();
                        chatRoomViewController.notifyFriendsRequest(loginUser, newContact);
                    } else {
                        responseLabel.setVisible(true);
                        responseLabel.setText("Error please try again");
                    }
                });
            });
        }
    }

    @FXML
    void deleteFriendFromList(ActionEvent event) {
        if (!newContactListView.getSelectionModel().isEmpty()) {
            newContactList.remove(newContactListView.getSelectionModel().getSelectedItem().toString());
        }
    }


    AddContactViewController(ChatRoomViewController chatRoomViewController, User loginUser) {
        this.chatRoomViewController = chatRoomViewController;
        this.loginUser = loginUser;
        registerValidation = new RegisterValidation();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newContactList = FXCollections.observableArrayList();
        newContactListView.setItems(newContactList);
        responseLabel.setVisible(false);
    }
}
