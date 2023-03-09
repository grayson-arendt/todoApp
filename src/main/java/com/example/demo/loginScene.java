package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class loginScene
{
    public static final String[] loginCredentials = new String[2];
    public boolean loginValid = false;
    public Pane pane = new Pane();
    public VBox vbox = new VBox(15);
    public HBox hbox = new HBox(50);
    public Label signIn = new Label("Sign in");
    public Label invalidLogin = new Label("");
    public Hyperlink register = new Hyperlink("Register");
    public Hyperlink forgotPass = new Hyperlink("Forgot password?");
    public TextField user = new TextField();
    public PasswordField pass = new PasswordField();
    public Scene scene;
    registerScene rScene = new registerScene();

    public Scene createLogin(Stage stage)
    {
        pane.setId("pane");
        vbox.setId("box");
        signIn.setId("signIn");
        invalidLogin.setId("invalidLogin");
        register.setId("register");
        forgotPass.setId("forgotPass");

        user.setPromptText("Username");
        pass.setPromptText("Password");

        user.setFocusTraversable(true);
        pass.setFocusTraversable(false);

        Scene scene = new Scene(pane, 400, 500);
        scene.getStylesheets().add(loginScene.class.getResource("darktheme.css").toString());

        hbox.getChildren().addAll(forgotPass, register);
        vbox.getChildren().addAll(user, pass, hbox, invalidLogin);
        pane.getChildren().addAll(vbox, signIn);

        vbox.setAlignment(Pos.CENTER);

        user.getParent().requestFocus();

        register.setOnAction(e -> {
            stage.setScene(rScene.createRegister(stage));

            System.out.println("Registering...");
        });

        EventHandler<ActionEvent> event = e -> {

            loginCredentials[0] = user.getText();
            loginCredentials[1] = pass.getText();

            if (!loginCredentials[0].isBlank() && !loginCredentials[1].isBlank()) {
                System.out.println(loginCredentials[0]);
                System.out.println(loginCredentials[1]);
            } else {
                // tell user to enter user or pass
                System.out.println("Login unsuccessful");
            }

            validateLogin();

            if (loginValid) {
                dashboardScene dashScene = new dashboardScene();
                stage.setScene(dashScene.createDashboard(stage));
            }

            else {
                invalidLogin.setText("Invalid login");
                user.clear();
                pass.clear();
            }
        };

        pass.setOnAction(event);

        return scene;
    }

    public void validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();

        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM login WHERE username = '" + loginCredentials[0] + "' AND password = '" + loginCredentials[1] + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    System.out.println("Login success");
                    loginValid = true;

                } else {
                    System.out.println("Login fail");
                    loginValid = false;
                }
            }
        } catch (Exception e) {
            System.out.println("Underlying exception: " + e.getCause());
        }
    }
}