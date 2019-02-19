package com.jets.LasserChatServer.controllers;

import com.jets.LasserChatServer.models.RegisterServicesImp;
import com.jets.LasserChatServer.models.UserServicesImp;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerServicesController extends Application
{
    //Server services impl
    private UserServicesImp userServices;
    private RegisterServicesImp registerServices;
    private Registry registry;
    private boolean isSecondTimeServerStopped;


    public ServerServicesController()
    {
        try
        {
            userServices = new UserServicesImp();
            registerServices = new RegisterServicesImp();
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
        BorderPane root = new BorderPane();
        Button start = new Button("Start");
        Button stop = new Button("Stop");
        stop.setDisable(true);

        stop.setOnAction((event) -> {
            try {
                registerServices.stopServer();
                //unbinding services
                registry.unbind("UserServices");
                registry.unbind("RegisterServices");
                registerServices.clearMap();
                isSecondTimeServerStopped = true;

                //update UI
                System.out.println("Server Stopped");
                stop.setDisable(true);
                start.setDisable(false);
            } catch (RemoteException | NotBoundException ex) {
                ex.printStackTrace();
            }
        });

        start.setOnAction((event) -> {
            try {
                //Registering services
                registry.rebind("UserServices", userServices);
                registry.rebind("RegisterServices", registerServices);

                if (isSecondTimeServerStopped)
                    registerServices.startServer();
                System.out.println("Server Started");

                //update UI
                stop.setDisable(false);
                start.setDisable(true);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        });

        root.setLeft(start);
        root.setRight(stop);
        Scene scene = new Scene(root, 200, 200);
        primaryStage.setTitle("Server");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest((event) -> {
            event.consume();
            System.exit(0);
        });
        primaryStage.show();
    }


    public static void main(String[] args){
        launch(args);
    }
}
