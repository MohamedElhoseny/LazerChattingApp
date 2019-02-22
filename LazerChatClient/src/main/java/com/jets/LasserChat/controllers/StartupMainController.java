package com.jets.LasserChat.controllers;

import com.jets.LasserChat.models.remote.ServiceLocator;
import com.jets.LasserChat.views.controllers.ChatRoomViewController;
import com.jets.LazerChatCommonService.models.dao.UserServices;
import com.jets.LazerChatCommonService.models.entity.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;

public class StartupMainController
{
    private MainController mainController;
    private UserServices userServices;
    boolean isServerStopped = false;

    public StartupMainController(MainController mainController)
    {
        this.mainController = mainController;
        this.userServices = (UserServices) ServiceLocator.getService("UserServices");
    }

    public User loginService(String phone, String password)
    {
        User loginUser = null;
        try
        {
            System.out.println("Trying to login : Phone = "+phone+" , password = "+password);
            loginUser = userServices.logIn(phone, password);

        } catch (RemoteException e) {
            System.err.println("Error occur in userServices : " + e.getMessage());
        }

        return loginUser;
    }


    public boolean registerService(User newUser)
    {
        boolean isAccepted;
        try {
            isAccepted = userServices.register(newUser);
        } catch (RemoteException e) {
            System.err.println("Error occur in userServices : "+e.getMessage());
            isAccepted = false;
        }
        return isAccepted;
    }


    /**
     * Responsible for changing scene from startupScene to chatRoomScene
     * @param loginUser an object of logged in User, used to initialize next scene
     */
    public void openChatRoomScene(User loginUser)
    {
        FXMLLoader fxmlLoader = new FXMLLoader();
        File file = new File("src/main/java/com/jets/LasserChat/views/fxml/ChatRoomUI.fxml");
        //pass another reference from another controller instead of main to handle chat events
        ChatRoomViewController chatRoomViewController = new ChatRoomViewController(loginUser);
        fxmlLoader.setController(chatRoomViewController);
        try
        {
            fxmlLoader.setLocation(file.toURL());
            Parent root = fxmlLoader.load();
            Stage primaryStage = mainController.getPrimaryStage();
            primaryStage.close();
            primaryStage = new Stage();
            Scene scene = MainController.getDecoratedScene(primaryStage,root);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
