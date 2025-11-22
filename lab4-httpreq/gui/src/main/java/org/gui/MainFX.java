package org.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import org.client.api.TranstatApiClient;
import org.client.service.TrafficStatsService;

public class MainFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        TranstatApiClient apiClient = new TranstatApiClient();
        TrafficStatsService statsService = new TrafficStatsService(apiClient);

        SceneManager sceneManager = new SceneManager(primaryStage, statsService);
        sceneManager.showMainView();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
