package com.example.demo;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class projectScene extends dashboardScene {

    public void createProject() {

        VBox vboxProject = new VBox();
        HBox colors = new HBox();
        projectNode p = new projectNode(); // default created, will fill later
        ToggleGroup tg = new ToggleGroup();

        TextField inputName = new TextField();
        RadioButton red = new RadioButton();
        RadioButton yellow = new RadioButton();
        RadioButton green = new RadioButton();
        RadioButton blue = new RadioButton();
        RadioButton purple = new RadioButton();

        inputName.setPromptText("project name");

        red.setId("red");
        yellow.setId("yellow");
        green.setId("green");
        blue.setId("blue");
        purple.setId("purple");

        red.setToggleGroup(tg);
        yellow.setToggleGroup(tg);
        green.setToggleGroup(tg);
        blue.setToggleGroup(tg);
        purple.setToggleGroup(tg);

        Button create = new Button("create");

        projectsMenu.add(vboxProject,p.projectCount,0); // the rectangle it's in

        colors.getChildren().addAll(red,yellow,green,blue,purple);
        vboxProject.getChildren().addAll(inputName,colors,create);
        vboxProject.toFront();

        create.setOnAction(e -> { // when create button pressed
            p.setProjectNameData(inputName.getText());

            Toggle selection = tg.getSelectedToggle();

            if (selection == red) {p.setColorData("#F85646");}
            else if (selection == yellow) {p.setColorData("#E2B808");}
            else if (selection == green) {p.setColorData("#008000");}
            else if (selection == blue) {p.setColorData("#1893F8");}
            else {p.setColorData("#814CEB");}

            p.setColor(p.projectCount, p.color);
            p.setProjectName();
        });
    }

    public void deleteProject(int pos) {
        // work on this later
    }
}
