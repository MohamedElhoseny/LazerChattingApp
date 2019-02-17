package com.jets.LasserChat.controllers;

import com.jets.LasserChat.views.controllers.ChatRoomController;
import com.jets.LasserChat.views.controllers.StartupPaneController;
import com.jets.LazerChatCommonService.models.entity.User;
import com.jfoenix.controls.JFXDecorator;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

public class MainController extends Application
{
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader();
        File file = new File("E:\\FCIH\\ITI\\JavaSE\\Project\\Implementation\\LazerChatClient\\src\\main\\java\\com\\jets\\LasserChat\\views\\fxml\\StartupPane.fxml");
        StartupPaneController startupPaneController = new StartupPaneController(this);
        fxmlLoader.setController(startupPaneController);
        fxmlLoader.setLocation(file.toURL());
        Parent root = fxmlLoader.load();

        //Set decorated scene
        Scene scene = getDecoratedScene(primaryStage, root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        this.primaryStage = primaryStage;
        primaryStage.show();
    }

    /**
     * Responsible for changing scene from startupScene to chatRoomScene
     * @param loginUser an object of logged in User, used to initialize next scene
     */
    public void openChatRoomScene(User loginUser)
    {
        FXMLLoader fxmlLoader = new FXMLLoader();
        File file = new File("E:\\FCIH\\ITI\\JavaSE\\Project\\Implementation\\LazerChatClient\\src\\main\\java\\com\\jets\\LasserChat\\views\\fxml\\ChatRoomUI.fxml");
        //pass another reference from another controller instead of main to handle chat events
        ChatRoomController chatRoomController = new ChatRoomController(loginUser);
        fxmlLoader.setController(chatRoomController);
        try {
            fxmlLoader.setLocation(file.toURL());
            Parent root = fxmlLoader.load();
            primaryStage.hide();
            primaryStage = new Stage();
            Scene scene = getDecoratedScene(primaryStage,root);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Used to get a new decorated scene to add it to stage
     * @param stage primary Stage, must be new to add new style
     * @param root  root node of pane to add to scene
     * @return scene suitable to add to stage
     */
    private Scene getDecoratedScene(Stage stage, Parent root)
    {
        JFXDecorator decorator = new JFXDecorator(stage, root);
        stage.setTitle("LaZer Chat Application");
        FontAwesomeIconView appIcon = new FontAwesomeIconView(FontAwesomeIcon.GROUP);
        appIcon.setFill(Color.WHITE);
        appIcon.setGlyphSize(18.0);
        decorator.setCustomMaximize(true);
        decorator.setGraphic(appIcon);
        Scene scene = new Scene(decorator);
        final ObservableList<String> stylesheets = scene.getStylesheets();
        stylesheets.addAll(MainController.class.getResource("/css/chatStyle.css").toExternalForm());
        return scene;
    }


    @Override
    public void init() throws Exception {
        super.init();
        System.out.println("JavaFX Launcher Thread ..");
    }
    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("Application is stopped !");
    }

    public static void main(String[] args){
        launch(args);
    }
}
