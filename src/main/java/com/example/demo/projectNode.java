package com.example.demo;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import java.sql.*;
import java.util.ArrayList;

@SuppressWarnings("all")

public class projectNode extends dashboardScene {

    public String projectName;
    public String color;
    public ArrayList<String> tasks = new ArrayList<String>();
    public int numTasks;
    public String task;
    public String allTasks;
    public String tasksColumns;
    public String username;
    public String insertTasks;
    public int projectCount;

    public projectNode() {
        projectName = null;
        color = null;
        tasks = null;
        numTasks = 0;
    }

    public projectNode(String projectName, String color, ArrayList<String> tasks) {
        projectName = this.projectName;
        color = this.color;
        tasks = this.tasks;
        numTasks = tasks.size();
    }

    public Rectangle getRect(int pos) {
        return projectsRect.get(pos);
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
        projectsRect.get(pos).setStyle("-fx-border-width: 0 3px 0 0; -fx-border-color: " + color + ";");
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


