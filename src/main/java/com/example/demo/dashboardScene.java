package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class dashboardScene {

    public ToolBar toolbar = new ToolBar();
    public AnchorPane anchorPane = new AnchorPane();
    public GridPane gridPane = new GridPane();
    public Pane chartPane = new Pane();
    public ArrayList<Rectangle> projectsRect = new ArrayList<>();
    public ArrayList<Rectangle> tasksInList = new ArrayList<>();
    public ArrayList<Rectangle> stats = new ArrayList<>();
    public ArrayList<projectNode> allProjects = new ArrayList<>();
    public GridPane projectsMenu = new GridPane();
    public GridPane tasksToday = new GridPane();
    public GridPane statistics = new GridPane();

    static String[] suffixes =
            {  "0th",  "1st",  "2nd",  "3rd",  "4th",  "5th",  "6th",  "7th",  "8th",  "9th",
                    "10th", "11th", "12th", "13th", "14th", "15th", "16th", "17th", "18th", "19th",
                    "20th", "21st", "22nd", "23rd", "24th", "25th", "26th", "27th", "28th", "29th",
                    "30th", "31st" };

    public Scene createDashboard(Stage stage) {

        String username = loginScene.loginCredentials[0];

        Calendar cal = Calendar.getInstance();
        String dayStr = suffixes[cal.get(Calendar.DAY_OF_MONTH)];

        int year = cal.get(Calendar.YEAR);

        SimpleDateFormat format = new SimpleDateFormat("EEEE, MMMM");

        String welcomeText = ("hello, " + username);
        String fullDate = ("Today is " + format.format(cal.getTime()) + " " + dayStr + " " + year);

        Button dashboardHome = new Button();
        Button taskList = new Button();
        Button tracking = new Button();
        Button settings = new Button();

        dashboardHome.setMinSize(40,40);
        taskList.setMinSize(40,40);
        tracking.setMinSize(40,40);
        settings.setMinSize(40,40);

        dashboardHome.setId("dashboardHome");
        taskList.setId("taskList");
        tracking.setId("tracking");
        settings.setId("settings");

        toolbar.setMinWidth(60);
        toolbar.prefHeightProperty().bind(stage.heightProperty());

        toolbar.getItems().addAll(dashboardHome,taskList,tracking,settings);
        toolbar.setOrientation(Orientation.VERTICAL);
        toolbar.setId("toolbar");

        gridPane.setStyle("-fx-grid-lines-visible: true");

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Grapefruit", 13),
                        new PieChart.Data("Oranges", 25),
                        new PieChart.Data("Plums", 10),
                        new PieChart.Data("Pears", 22),
                        new PieChart.Data("Apples", 30));
        final PieChart chart = new PieChart(pieChartData);

        //chart.setLegendSide(Side.BOTTOM);

        chart.prefHeightProperty().bind(stage.heightProperty().multiply(0.25));
        chart.prefWidthProperty().bind(stage.heightProperty().multiply(0.5));

        VBox vbox = new VBox(20);
        VBox vboxTop = new VBox(15);

        HBox hbox = new HBox(40);
        HBox hboxTop = new HBox(200);
        HBox hboxTop2 = new HBox(350); //adjusts how far add project
        Label date = new Label(fullDate.toLowerCase());
        Label welcome = new Label(welcomeText.toLowerCase());

        Button addProject = new Button("+ project");

        addProject.setOnAction(e -> {
            projectScene pScene = new projectScene();
            pScene.createProject();

            System.out.println("Project created...");
        });

        Separator sep = new Separator();
        sep.setMaxWidth(250);

        addProject.setId("addProject");
        date.setId("date");
        welcome.setId("welcome");

        for (int i = 0; i < 1; i++) { // row
            for (int j = 0; j < 4; j++) { // col
                Rectangle r = new Rectangle(100, 100, 170, 170); // x y width height
                projectsRect.add(r);
                r.setId("rectangle");
                r.setArcWidth(20);
                r.setArcHeight(20);
                projectsMenu.add(r, j, i);
            }
        }

        for (int i = 0; i < 3; i++) { // row
            for (int j = 0; j < 1; j++) { // col
                Rectangle r = new Rectangle(150, 150, 300, 72); // x y width height
                tasksInList.add(r);
                r.setId("rectangle");
                r.setArcWidth(20);
                r.setArcHeight(20);
                tasksToday.add(r, j, i);
            }
        }

        for (int i = 0; i < 2; i++) { // row
            for (int j = 0; j < 3; j++) { // col
                Rectangle r = new Rectangle(150, 150, 120, 120); // x y width height
                stats.add(r);
                r.setId("rectangle");
                r.setArcWidth(20);
                r.setArcHeight(20);
                statistics.add(r, j, i);
            }
        }

        projectsMenu.setHgap(20);
        projectsMenu.setVgap(5);

        tasksToday.setHgap(20);
        tasksToday.setVgap(20);

        statistics.setHgap(20);
        statistics.setVgap(20);

        AnchorPane.setTopAnchor(vbox, 0.0);
        AnchorPane.setRightAnchor(vbox, 50.0);

        chart.setLabelsVisible(false);

        chartPane.getChildren().add(chart);
        //gridPane.add(chartPane, 9, 3);

        hbox.getChildren().addAll(tasksToday,statistics);
        hboxTop2.getChildren().addAll(date,addProject);

        vboxTop.getChildren().addAll(welcome,sep,hboxTop2);
        hboxTop.getChildren().addAll(vboxTop);

        vbox.getChildren().addAll(hboxTop,projectsMenu,hbox);
        anchorPane.getChildren().addAll(vbox, toolbar);

        Scene scene = new Scene(anchorPane, 900, 640);

        scene.getStylesheets().add(this.getClass().getResource("/com/example/demo/dashboard.css").toExternalForm());
        stage.centerOnScreen();

        return scene;
    }
}