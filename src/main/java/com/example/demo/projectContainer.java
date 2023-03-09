package com.example.demo;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

public class projectContainer extends projectNode {

    public projectContainer(String projectName, String color, ArrayList<String> tasks) {
        super(projectName, color, tasks);
    }

    public void addProjectToList(projectNode project) {
        allProjects.add(project);
    }

    public projectNode getProject(int pos) {
        return allProjects.get(pos);
    }

    public void deleteProject(projectNode project) {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        try {
            generateTaskColumns(numTasks);

            Statement statement = connectDB.createStatement();
            statement.executeUpdate("DROP TABLE " +  project.getProjectName() + ";");

            System.out.println("Deleted project");
            connectDB.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
