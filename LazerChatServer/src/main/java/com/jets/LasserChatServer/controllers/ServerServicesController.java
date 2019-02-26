package com.jets.LasserChatServer.controllers;

import com.jets.LasserChatServer.models.*;
import com.jets.LasserChatServer.views.controller.AdminViewController;
import com.jets.LazerChatCommonService.models.entity.Annoncement;
import com.jets.LazerChatCommonService.models.entity.User;
import com.jfoenix.controls.JFXDecorator;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Map;

public class ServerServicesController extends Application
{
    //Server services impl
    private UserServicesImp userServices;
    private RegisterServicesImp registerServices;
    private StatuesServicesImp statusServices;
    private ContactServicesImp contactServices;
    private FriendRequestNotifierServicesImp friendRequestNotifierServices;
    private Registry registry;
    private boolean isSecondTimeServerStopped;
    private UserDAOImplementation userDAOImplementation;
    AdminViewController adminViewController;

    public ServerServicesController()
    {
        try
        {
            userServices = new UserServicesImp();
            registerServices = new RegisterServicesImp(this);
            statusServices = new StatuesServicesImp();
            contactServices = new ContactServicesImp();
            friendRequestNotifierServices = new FriendRequestNotifierServicesImp();
            userDAOImplementation = UserDAOImplementation.getInstance();
            registry = LocateRegistry.getRegistry();
            isSecondTimeServerStopped = false;
            System.out.println("Server is started .");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader();
        adminViewController = new AdminViewController(this);
        fxmlLoader.setController(adminViewController);
        File file = new File("src/main/java/com/jets/LasserChatServer/views/fxml/AdminView.fxml");
        fxmlLoader.setLocation(file.toURL());
        Parent root = fxmlLoader.load();
        Scene scene = getDecoratedScene(primaryStage, root);
        primaryStage.setScene(scene);


        primaryStage.setOnCloseRequest((event) -> {
            event.consume();
            System.exit(0);
        });
        primaryStage.show();

    }

    public void startServer()
    {
        try {
            //Registering services
            registry.rebind("UserServices", userServices);
            registry.rebind("RegisterServices", registerServices);
            registry.rebind("StatuesServices",statusServices);
            registry.rebind("ContactServices", contactServices);
            registry.rebind("FriendRequestNotifierServices", friendRequestNotifierServices);
            if (isSecondTimeServerStopped)
                registerServices.startServer();

            System.out.println("Server Started");
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    public void stopServer()
    {
        try {
            registerServices.stopServer();
            //unbinding services
            registry.unbind("UserServices");
            registry.unbind("RegisterServices");
            registry.unbind("StatuesServices");
            registry.unbind("ContactServices");
            registry.unbind("FriendRequestNotifierServices");
            registerServices.clearMap();
            isSecondTimeServerStopped = true;

            //update UI
            System.out.println("Server Stopped");
        } catch (RemoteException | NotBoundException ex) {
            ex.printStackTrace();
        }
    }

    public void sendAnnouncement(File choosenFileImg, String announceString)
    {
        Annoncement annoncement = new Annoncement();
        byte[] img = null;

        if (choosenFileImg != null)
        {
            img = convertImageToBytes(choosenFileImg);
            annoncement.setImage(img);

            annoncement.setAnnoncementText(announceString);
            registerServices.broadcast(annoncement);
        }

    }

    private byte[] convertImageToBytes(File choosenImg)
    {
        BufferedImage image = null;
        try {
            image = ImageIO.read(choosenImg);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            return null;
        }
    }

    public static Scene getDecoratedScene(Stage stage, Parent root)
    {
        JFXDecorator decorator = new JFXDecorator(stage, root);
        stage.setTitle("LaZer Chat Server Application");
        FontAwesomeIconView appIcon = new FontAwesomeIconView(FontAwesomeIcon.GROUP);
        appIcon.setFill(Color.WHITE);
        appIcon.setGlyphSize(18.0);
        decorator.setCustomMaximize(true);
        decorator.setGraphic(appIcon);
        Scene scene = new Scene(decorator);
        final ObservableList<String> stylesheets = scene.getStylesheets();
        stylesheets.addAll(ServerServicesController.class.getResource("/css/chatStyle.css").toExternalForm());
        return scene;
    }

    public List<User> getOfflinUsers() {
        return userDAOImplementation.getOfflineUsers();

    }

    public List<User> getOnlinUsers() {
        return userDAOImplementation.getOnlineUsers();
    }


    public List<User> getAllUsers() {
        return userDAOImplementation.getAllUsers();
    }

    public Map<String, Integer> getUsersCountry() {
        return userDAOImplementation.getUsersCountry();
    }
    public static void main(String[] args){
        launch(args);
    }

    public void incrementOnline() {
        adminViewController.incrementOnline();
    }
    public void decrementOnline() {
        adminViewController.decrementOnline();
    }
}
