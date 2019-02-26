package com.jets.LasserChatServer.views.controller;

import com.jets.LasserChatServer.controllers.ServerServicesController;
import com.jets.LazerChatCommonService.models.entity.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class AdminViewController implements Initializable
{
    @FXML private JFXTabPane tabPane;
    @FXML private Tab ManageServiceTab;
    @FXML private Tab announcementTab;
    @FXML private Tab statisticsTab;
    @FXML private Tab dataTab;
    @FXML private Tab registerationTab;

    private ServerServicesController mainController;
    private ManageServiceController manageServiceController;

    public AdminViewController(ServerServicesController controller) {
        this.mainController = controller;
        manageServiceController = new ManageServiceController(this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(0);

        //setting tab contents
        ManageServiceTab.setContent(getManageServicesPane());
        announcementTab.setContent(getServerAnnouncementPane());
        statisticsTab.setContent(getServerStatisticsPane());
        dataTab.setContent(getDataAdministrationPane());
        registerationTab.setContent(getUserRegistrationPane());
    }


    private Parent getManageServicesPane() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setController(manageServiceController);
        File file = new File("src/main/java/com/jets/LasserChatServer/views/fxml/ManageService.fxml");
        try {
            fxmlLoader.setLocation(file.toURL());
            return fxmlLoader.load();
        } catch (IOException e) {
            return null;
        }
    }


    private Parent getServerAnnouncementPane() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        ServerAnnouncementController adminViewController = new ServerAnnouncementController(this);
        fxmlLoader.setController(adminViewController);
        File file = new File("src/main/java/com/jets/LasserChatServer/views/fxml/ServerAnnouncement.fxml");
        try {
            fxmlLoader.setLocation(file.toURL());
            return fxmlLoader.load();
        } catch (IOException e) {
            return null;
        }
    }

    private Parent getServerStatisticsPane() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        ServerStatisticsController adminViewController = new ServerStatisticsController(this);
        fxmlLoader.setController(adminViewController);
        File file = new File("src/main/java/com/jets/LasserChatServer/views/fxml/ServerStatistics.fxml");
        try {
            fxmlLoader.setLocation(file.toURL());
            return fxmlLoader.load();
        } catch (IOException e) {
            return null;
        }
    }


    private Parent getDataAdministrationPane() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        DataAdministrationController adminViewController = new DataAdministrationController(this);
        fxmlLoader.setController(adminViewController);
        File file = new File("src/main/java/com/jets/LasserChatServer/views/fxml/DataAdministration.fxml");
        try {
            fxmlLoader.setLocation(file.toURL());
            return fxmlLoader.load();
        } catch (IOException e) {
            return null;
        }
    }

    private Parent getUserRegistrationPane() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        RegistrationViewController adminViewController = new RegistrationViewController(this);
        fxmlLoader.setController(adminViewController);
        File file = new File("src/main/java/com/jets/LasserChatServer/views/fxml/RegisterUser.fxml");
        try {
            fxmlLoader.setLocation(file.toURL());
            return fxmlLoader.load();
        } catch (IOException e) {
            return null;
        }
    }


    public List<User> getOnlineUsers() {

        return mainController.getOnlinUsers();
    }

    public List<User> getOfflineUsers() {
        return mainController.getOfflinUsers();
    }


    public List<User> getAllUsers() {
        return mainController.getAllUsers();
    }

    public Map<String, Integer> getUsersCountry() {
        return mainController.getUsersCountry();
    }

    public void startServer() {
        mainController.startServer();
    }

    public void stopServer() {
        mainController.startServer();
    }

    public void sendAnnouncement(File file, String announcementString) {
        mainController.sendAnnouncement(file, announcementString);
    }

    public void incrementOnline() {
        manageServiceController.incrementOnline();
    }
    public void decrementOnline(){
        manageServiceController.decrementOnline();

    }
}
