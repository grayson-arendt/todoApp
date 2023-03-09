package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

public class mainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        //loginScene login = new loginScene();
        //stage.setScene(login.createLogin(stage));

        dashboardScene dashScene = new dashboardScene();
        stage.setScene(dashScene.createDashboard(stage));

        //splashScreen splScreen = new splashScreen();
        //stage.setScene(splScreen.splashScreen(stage));

        File file = new File("target/classes/com/example/images/appIcon.png");

        Image icon = new Image(file.toURI().toString());
        stage.getIcons().add(icon);


        stage.setResizable(false);
        stage.centerOnScreen();

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}