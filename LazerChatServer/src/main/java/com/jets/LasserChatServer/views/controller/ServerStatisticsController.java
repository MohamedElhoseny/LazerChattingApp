package com.jets.LasserChatServer.views.controller;

import com.jets.LazerChatCommonService.models.entity.User;
import com.jfoenix.controls.JFXTabPane;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ServerStatisticsController implements Initializable
{
    AdminViewController adminViewController;
    int onlineUsers;
    int offlineUsers;
    int maleUsers;
    int femaleUsers;
    Map<String, Integer> usersCountry;

    @FXML private JFXTabPane tabPane;
    @FXML private StackPane onlineOfflinePieChart;
    @FXML private StackPane genderPieChart;
    @FXML private StackPane countryBarChart;



    public ServerStatisticsController(AdminViewController controller)
    {
        adminViewController = controller;
        onlineUsers = 0;
        offlineUsers = 0;
        maleUsers = 0;
        femaleUsers = 0;
            List<User> users = adminViewController.getAllUsers();
            if (users == null) System.out.println("dasdsadsdas");
            users.stream().forEach(user -> {
                if (user.getStatus() == 1 || user.getStatus() == 2 || user.getStatus() == 3) onlineUsers++;
                if (user.getStatus() == 4) offlineUsers++;
                if (user.getGender().equalsIgnoreCase("male")) maleUsers++;
                if (user.getGender().equalsIgnoreCase("female")) femaleUsers++;
            });
            System.out.println(onlineUsers + "," + offlineUsers + "," + maleUsers + "," + femaleUsers + ",");
            usersCountry = adminViewController.getUsersCountry();
            usersCountry.entrySet().stream().forEach(stringIntegerEntry -> System.out.println(stringIntegerEntry.getKey() + ", " + stringIntegerEntry.getValue()));

    }


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        System.out.println("male = "+maleUsers);
        onlineOfflinePieChart.getChildren().add(get_PieChart("Online", onlineUsers, "Offline", offlineUsers));
        genderPieChart.getChildren().add(get_PieChart("Male", maleUsers, "Female", femaleUsers));
        countryBarChart.getChildren().add(get_BarChart(usersCountry));
    }

    private PieChart get_PieChart(String v1, int n1, String v2, int n2)
    {
        //Model Data
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        System.out.println(v1+"="+n1);
        System.out.println(v2+"="+n2);

        pieChartData.addAll(
                new PieChart.Data(v1,n1),
                new PieChart.Data(v2,n2)
        );

        //Create Chart
        PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Statistics of "+v1+" and "+v2);

        return  chart;
    }

    private BarChart get_BarChart(Map<String, Integer> usersCountry)
    {
        //Defining the x axis
        CategoryAxis xAxis = new CategoryAxis();

        xAxis.setCategories(FXCollections.observableArrayList(usersCountry.keySet()));

        //Defining the y axis
        NumberAxis yAxis = new NumberAxis(); //without specifying parameters, it will specify it according inputs
        yAxis.setLabel("Number of users");

        //Creating the Bar chart
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Comparison between various cars");

        //Prepare XYChart.Series objects by setting data
        XYChart.Series<String, Number> series;

        for (String country:usersCountry.keySet()) {
            series = new XYChart.Series<>();
            series.setName(country);
            series.getData().add(new XYChart.Data<>(country, usersCountry.get(country)));
            barChart.getData().add(series);
        }

        return barChart;
    }
}