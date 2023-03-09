package com.example.demo;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;

public class dashboardScene {

    public ToolBar toolbar = new ToolBar();
    public AnchorPane anchorPane = new AnchorPane();
    public GridPane gridPane = new GridPane();
    public Pane chartPane = new Pane();
    Rectangle[] projectsRect = new Rectangle[4];
    Rectangle[] tasksInList = new Rectangle[3];
    Rectangle[][] stats = new Rectangle[2][3];
    public ArrayList<projectNode> allProjects = new ArrayList<>();
    public GridPane projectsMenu = new GridPane();
    public GridPane tasksToday = new GridPane();
    public GridPane statistics = new GridPane();
    public String projectName;
    public String color;
    public ArrayList<String> tasks = new ArrayList<String>();
    public int numTasks;
    public String task;
    public String allTasks;
    public String tasksColumns;
    public String insertTasks;
    public int projectCount;

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

        Separator sep = new Separator();
        sep.setMaxWidth(250);

        addProject.setId("addProject");
        date.setId("date");
        welcome.setId("welcome");

        for (int i = 0; i < 1; i++) { // row
            for (int j = 0; j < 4; j++) { // col
                Rectangle r = new Rectangle(100, 100, 170, 170); // x y width height
                projectsRect[j] = r;
                r.setId("rectangle");
                r.setArcWidth(20);
                r.setArcHeight(20);
                projectsMenu.add(r, j, i);
            }
        }

        for (int i = 0; i < 3; i++) { // row
            for (int j = 0; j < 1; j++) { // col
                Rectangle r = new Rectangle(150, 150, 300, 72); // x y width height
                tasksInList[j] = r;
                r.setId("rectangle");
                r.setArcWidth(20);
                r.setArcHeight(20);
                tasksToday.add(r, j, i);
            }
        }

        for (int i = 0; i < 2; i++) { // row
            for (int j = 0; j < 3; j++) { // col
                Rectangle r = new Rectangle(150, 150, 120, 120); // x y width height
                stats[i][j] = r;
                r.setId("rectangle");
                r.setArcWidth(20);
                r.setArcHeight(20);
                statistics.add(r, j, i);
            }
        }

        addProject.setOnAction(e -> {

            try {
                createProject();
            }

            catch(IndexOutOfBoundsException exception) {
                System.out.println("Too many projects");
            }

            System.out.println("Project created...");
        });

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

    public void createProject() {
        try {
            if (projectCount < projectsRect.length) {
                VBox vboxProject = new VBox(10);
                VBox textField = new VBox(7);
                VBox selections = new VBox(17);
                //projectNode p = new projectNode(); // default created, will fill later
                ToggleGroup tg = new ToggleGroup();
                Separator sep2 = new Separator();
                String[] colorStrings = {"red","yellow","green","blue","purple"};

                ComboBox c = new ComboBox(FXCollections.observableArrayList(colorStrings));

                c.setPromptText("color");

                sep2.setMaxWidth(100);
                textField.setAlignment(Pos.CENTER);

                vboxProject.setAlignment(Pos.CENTER);
                textField.setAlignment(Pos.CENTER);
                selections.setAlignment(Pos.CENTER);

                TextField inputName = new TextField();

                Button create = new Button("create");

                inputName.setPromptText("project name");
                inputName.setAlignment(Pos.CENTER);

                create.setId("create");

                selections.getChildren().addAll(c,create);
                textField.getChildren().addAll(inputName,sep2);

                vboxProject.getChildren().addAll(textField, selections);
                vboxProject.toFront();

                projectsMenu.add(vboxProject, projectCount, 0); // the rectangle it's in

                create.setOnAction(e -> { // when create button pressed
                    vboxProject.getChildren().removeAll(textField, selections);

                    projectName = inputName.getText();
                    Label name = new Label(getProjectName());
                    vboxProject.getChildren().add(name);

                    String selected = c.getValue().toString();

                    if (Objects.equals(selected, colorStrings[0])) {
                        color = "#F85646";
                    } else if (Objects.equals(selected, colorStrings[1])) {
                        color = "#E2B808";
                    } else if (Objects.equals(selected, colorStrings[2])) {
                        color = "#008000";
                    } else if (Objects.equals(selected, colorStrings[3])) {
                        color = "#1893F8";
                    } else {
                        color = "#814CEB";
                    }

                    projectsRect[projectCount].setStyle("-fx-stroke: " + color + "; -fx-stroke-width:2px;");
                    projectCount++;

                    System.out.println(Arrays.toString(projectsRect));
                    System.out.println(getColor());
                    System.out.println(getProjectName());
                });
            }

            else {
                throw new IndexOutOfBoundsException();
            }
        }

        catch(Exception e) {
            System.out.println("Too many projects");
        }
    }

    public Rectangle getRect(int pos) {
        return projectsRect[pos];
    }

    public void setProjectNameData(String projectName) {
        projectName = this.projectName;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public void setProjectName() {

        Label name = new Label(projectName);
        projectsMenu.add(name,0,projectCount);
    }

    public void setColorData(String color) {
        color = this.color;
    }

    public void setColor(int pos, String color) {
        projectsRect[pos].setStyle("-fx-border-width: 0 3px 0 0; -fx-border-color: " + color + ";");
    }

    public String getColor() {
        return color;
    }

    public void addTask(String task) {
        tasks.add(task);
        numTasks++;
    }
    public void deleteTask(int pos) {
        tasks.remove(pos);
        numTasks--;
    }

    public String getTask(int pos) {
        task = tasks.get(pos);
        return task;
    }

    public String generateTaskColumns(int numTasks) {

        for (int i = 0; i < numTasks; i++) {
            allTasks = allTasks.concat("`tasks" + i + "`  VARCHAR(45) NULL, ");}
        return allTasks;
    }

    public String getTaskColumns(String productName) {
        tasksColumns = projectName + "(" + projectName;

        for (int i = 0; i < numTasks; i++) {
            if (i == numTasks-1) { // if last, don't add comma after
                tasksColumns = tasksColumns.concat("tasks" + i);}
            else {
                tasksColumns = tasksColumns.concat("tasks" + i + ",");}
        }

        tasksColumns.concat(")");
        return tasksColumns;
    }

    public String insertTasks(ArrayList<String> tasks, int numTasks) {
        for (int i = 0; i < numTasks; i++) {
            if (i == numTasks - 1) { // if last, don't add comma after
                insertTasks = insertTasks.concat(tasks.get(i));
            } else {
                insertTasks = insertTasks.concat(tasks.get(i) + ",");
            }
        }
        return insertTasks;
    }

    public void createProject(String projectName, String color, ArrayList<String> tasks, int spot) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        try {
            Statement statement = connectDB.createStatement();

            statement.executeUpdate("CREATE TABLE `login_info`.`" + projectName + "` (`id" + projectName + "` INT NOT NULL,`" + "`" + projectName + "`" + "VARCHAR(45) NOT NULL,`color` VARCHAR(45) NOT NULL," + generateTaskColumns(numTasks) + "PRIMARY KEY (`id"+projectName+"`));");
            statement.executeUpdate("INSERT INTO " + tasksColumns + " " + "VALUES" + "('" + insertTasks(tasks, numTasks) + "')");

            System.out.println("Created project");
            connectDB.close();

        } catch (Exception e) {
            System.out.println(e);
        }
        projectCount++;
    }

    public void deleteProject(int pos) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        try {
            generateTaskColumns(numTasks);
            Statement statement = connectDB.createStatement();
            statement.executeUpdate("DROP TABLE " +  projectName + ";");

            System.out.println("Deleted project");
            connectDB.close();

        } catch (Exception e) {
            System.out.println(e);
        }

        projectsMenu.getChildren().removeIf( node -> projectsMenu.getColumnIndex(node) == pos && projectsMenu.getRowIndex(node) == 0);
        projectCount--;
    }
}