package com.example.demo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class splashScreen extends loginScene {

    public Scene splashScreen(Stage stage) {

        pane.getChildren().clear();
        pane.setStyle("-fx-background-color: black;");

        Timeline loading = new Timeline(
                new KeyFrame(Duration.seconds(2),
                        new EventHandler<ActionEvent>() {

                            @Override
                            public void handle(ActionEvent event) {
                                ProgressIndicator progressIndicator = new ProgressIndicator();
                                progressIndicator.setMinSize(50, 50);
                                progressIndicator.setStyle("-fx-progress-color: #814CEB;");
                                pane.getChildren().add(progressIndicator);

                            }
                        }));

        loading.setCycleCount(1);
        loading.play();

        dashboardScene dashScene = new dashboardScene();
        stage.setScene(dashScene.createDashboard(stage));

        stage.show();

        return scene;
    }
}