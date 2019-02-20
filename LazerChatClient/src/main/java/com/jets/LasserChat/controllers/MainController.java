package com.jets.LasserChat.controllers;

import com.jets.LasserChat.views.controllers.StartupViewController;
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

public class MainController extends Application
{
    private StartupMainController startupMainController;
    private Stage primaryStage;

    public MainController()
    {
        startupMainController = new StartupMainController(this);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader fxmlLoader = new FXMLLoader();
        File file = new File("C:\\Users\\hd\\Desktop\\LazerChattingApp\\LazerChatClient\\src\\main\\java\\com\\jets\\LasserChat\\views\\fxml\\StartupPane.fxml");
        //Move controlling to another control with different responsibilty
        StartupViewController startupViewController = new StartupViewController(startupMainController);
        fxmlLoader.setController(startupViewController);
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
     * Used to get a new decorated scene to add it to stage
     * @param stage primary Stage, must be new to add new style
     * @param root  root node of pane to add to scene
     * @return scene suitable to add to stage
     */
    public static Scene getDecoratedScene(Stage stage, Parent root)
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

    public Stage getPrimaryStage()
    {
        return this.primaryStage;
    }

    @Override
    public void init() throws Exception
    {
        super.init();
        System.out.println("JavaFX Launcher Thread ..");
    }
    @Override
    public void stop() throws Exception
    {
        super.stop();
        System.out.println("Application is stopped !");
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
