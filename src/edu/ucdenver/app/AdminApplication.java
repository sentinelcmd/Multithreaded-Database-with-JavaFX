package edu.ucdenver.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AdminApplication extends Application {


    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("AdminController.fxml"))));
        primaryStage.setTitle("World cup Database Client");
        primaryStage.setScene(new Scene(root, 720, 480));
        primaryStage.show();


    }
}
