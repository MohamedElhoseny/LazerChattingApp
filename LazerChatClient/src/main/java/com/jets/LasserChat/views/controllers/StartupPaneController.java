package com.jets.LasserChat.views.controllers;

import Animation.Transition.FadeInDownBigTransition;
import Animation.Transition.FadeInLeftTransition;
import Animation.Transition.FadeInRightBigTransition;
import Animation.Transition.FadeOutRightBigTransition;
import com.jets.LasserChat.controllers.MainController;
import com.jets.LasserChat.controllers.StartupMainController;
import com.jets.LazerChatCommonService.models.entity.User;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class StartupPaneController implements Initializable
{
    @FXML
    private StackPane loginFormPane;
    @FXML
    private StackPane registerationFormPane;
    @FXML
    private Pagination featurePagination;

    @FXML
    private LoginPaneController loginPaneController;
    @FXML
    private RegistrationPaneController registrationPaneController;

    private StartupMainController mainController;

    public StartupPaneController()
    {}

    public StartupPaneController(StartupMainController mainController)
    {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        //inject MainController into loginPaneController & registrationPaneController
        loginPaneController.injectMainController(this);
        registrationPaneController.injectMainController(this);

        //Pagination
        String[] features = new String[]{
                "Connect with your friends", "High quality voice & video call", "Fast file transfer"};
        String[] featuresDescription = new String[]{
                "Connect with your friend directly through Connect with your friend directly through Connect with your friend directly through Connect with your friend directly through Connect with your friend directly through Connect with your friend directly through "
                ,"Voice and video call feature description"
                ,"File transfer feature description"
        };
        //Customize pagination
        featurePagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
        featurePagination.setPageFactory(index -> getFeaturePage(featuresDescription[index], features[index]));

        //Run the pagination self switch pages every 5 seconds
        new Thread(() -> {
            int pageIndex = 0;
            while (true) {
                if (pageIndex == 3)
                    pageIndex = 0;

                final int index = pageIndex;
                Platform.runLater(() -> {
                    featurePagination.setCurrentPageIndex(index);
                });
                pageIndex++;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void startChatRoom(User loginUser)
    {
        //
        this.mainController.openChatRoomScene(loginUser);
    }

    /**
     * Used to get a custome graphic node for pagination
     * @param featureDetails string describe one of the feature in app
     * @param title string of feature
     * @return pane node to set as a pagination graphic node
     */
    private StackPane getFeaturePage(String featureDetails, String title)
    {
        StackPane pagePane = new StackPane();
        Reflection reflection;
        AnchorPane anchorPane;
        Label featureDescription;

        anchorPane = new AnchorPane();
        Label featureTitle = new Label();
        reflection = new Reflection();
        featureDescription = new Label();


        pagePane.setPrefHeight(400.0);
        pagePane.setPrefWidth(600.0);
        pagePane.setStyle("-fx-background-color: TRANSPARENT;");

        anchorPane.setPrefHeight(200.0);
        anchorPane.setPrefWidth(200.0);
        anchorPane.setStyle("-fx-background-color: TRANSPARENT;");

        featureTitle.setLayoutX(48.0);
        featureTitle.setLayoutY(33.0);
        featureTitle.setStyle("-fx-background-color: #2f5266;");
        featureTitle.setText(title);
        featureTitle.setTextFill(javafx.scene.paint.Color.WHITE);
        featureTitle.setFont(new Font("Impact", 31.0));
        featureTitle.setPadding(new Insets(8.0));

        reflection.setFraction(0.65);
        reflection.setTopOpacity(0.23);
        featureTitle.setEffect(reflection);


        AnchorPane.setBottomAnchor(featureDescription, 37.0);
        AnchorPane.setLeftAnchor(featureDescription, 48.0);
        AnchorPane.setTopAnchor(featureDescription, 139.0);
        featureDescription.setAlignment(javafx.geometry.Pos.TOP_LEFT);
        featureDescription.setLayoutX(48.0);
        featureDescription.setLayoutY(139.0);
        featureDescription.setPrefHeight(224.0);
        featureDescription.setPrefWidth(457.0);
        featureDescription.setTextFill(javafx.scene.paint.Color.WHITE);
        featureDescription.setStyle("-fx-background-color: TRANSPARENT;");
        featureDescription.setText(featureDetails);
        featureDescription.setWrapText(true);
        featureDescription.setFont(new Font(18.0));

        anchorPane.getChildren().add(featureTitle);
        anchorPane.getChildren().add(featureDescription);
        pagePane.getChildren().add(anchorPane);


        new FadeInDownBigTransition(featureTitle).playFromStart();
        new FadeInLeftTransition(featureDescription).play();
        return pagePane;
    }


    void showRegistrationForm()
    {
        new FadeOutRightBigTransition(loginFormPane).play();
        new FadeInRightBigTransition(registerationFormPane).play();
        Timeline t1 = new Timeline(new KeyFrame(Duration.millis(500) , event -> {
            registerationFormPane.setVisible(true);
            loginFormPane.setVisible(false);
        }));
        t1.play();
    }

    void showLoginForm()
    {
        new FadeOutRightBigTransition(registerationFormPane).play();
        new FadeInRightBigTransition(loginFormPane).play();
        Timeline t1 = new Timeline(new KeyFrame(Duration.millis(500) , event -> {
            registerationFormPane.setVisible(false);
            loginFormPane.setVisible(true);
        }));
        t1.play();
    }

    void setTrueFlag(FontAwesomeIconView iconView)
    {
        iconView.setGlyphName(FontAwesomeIcon.CHECK.name());
        iconView.setFill(Paint.valueOf("#2bff63"));
        iconView.setVisible(true);
    }
    void setFalseFlag(FontAwesomeIconView iconView)
    {
        iconView.setGlyphName(FontAwesomeIcon.TIMES.name());
        iconView.setFill(Paint.valueOf("#ff3600"));
        iconView.setVisible(true);
    }

    public User getLoginUser(String phone, String password) {
        return mainController.loginService(phone, password);
    }
    public boolean registerNewUser(User newUser){
        return mainController.registerService(newUser);
    }
}
