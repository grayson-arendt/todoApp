package com.example.demo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Objects;

public class registerScene {
    private final static String[] registers = new String[3];
    public boolean usernameTaken = false;
    public Label successful = new Label("Create an account");
    public Hyperlink back = new Hyperlink("Back to login");
    public Button close2 = new Button();
    public Pane rpane = new Pane();
    public VBox rvbox = new VBox(15);
    public HBox rhbox = new HBox(50);
    public Hyperlink spacer1 = new Hyperlink("");
    public Hyperlink spacer2 = new Hyperlink("");
    public TextField newUser = new TextField();
    public PasswordField newPass = new PasswordField();
    public PasswordField confirmPass = new PasswordField();

    EventHandler<ActionEvent> registerEvent;

    public Scene createRegister(Stage stage)
    {
        back.setId("back");
        rpane.setId("rpane");
        rvbox.setId("rbox");

        successful.setId("successful");

        newUser.setPromptText("New username");
        newPass.setPromptText("New password");
        confirmPass.setPromptText("Confirm password");

        rhbox.getChildren().addAll(spacer1, back, spacer2);
        rvbox.getChildren().addAll(successful, newUser, newPass, confirmPass, rhbox);
        rpane.getChildren().addAll(close2, rvbox);

        Scene registerscene = new Scene(rpane, 400, 500);
        registerscene.getStylesheets().add(registerScene.class.getResource("darktheme.css").toString());


        EventHandler<ActionEvent> registerEvent = a -> {

                registers[0] = newUser.getText();
                registers[1] = newPass.getText();
                registers[2] = confirmPass.getText();

                if (!Objects.equals(registers[1], registers[2])) {
                    successful.setText("Passwords do not match");
                    successful.setStyle("-fx-text-fill: #dc493a; -fx-padding: 0 0 20 30");
                    newPass.clear();
                    confirmPass.clear();

                } else if (Objects.equals(registers[1], registers[2])) {

                    try {
                        registerLogin();

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                    if (usernameTaken) {
                        successful.setStyle("-fx-text-fill: #dc493a; -fx-padding: 0 0 20 50");
                        successful.setText("Username taken");
                        newUser.clear();
                        newPass.clear();
                        confirmPass.clear();

                    } else {
                        successful.setText("Register successful");
                        successful.setStyle("-fx-text-fill: #136f63; -fx-padding: 0 0 20 37");
                        newUser.clear();
                        newPass.clear();
                        confirmPass.clear();
                    }
                }
            };

        back.setOnAction((e -> {
            loginScene login = new loginScene();
            stage.setScene(login.createLogin(stage));
            successful.setText("");
        }));

        close2.setId("close");
        close2.setPrefSize(15, 15);
        close2.setOnAction((this::close));

        confirmPass.setOnAction(registerEvent);

        stage.initStyle(StageStyle.TRANSPARENT);

        return registerscene;
    }

    public void close(ActionEvent e) {
        Platform.exit();
        System.exit(0);
    }

    public void registerLogin() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();

        Connection connectDB = connectNow.getConnection();

        try {
            Statement statement = connectDB.createStatement();
            statement.executeUpdate("INSERT INTO login(username,password) VALUES ('" + registers[0] + "','" + registers[1] + "')");

            usernameTaken = false;

            System.out.println("Added to database");
            connectDB.close();
        } catch (SQLIntegrityConstraintViolationException e) {
            usernameTaken = true;

            System.out.println(e);
        }
    }
}
