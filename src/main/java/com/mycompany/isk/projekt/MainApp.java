package com.mycompany.isk.projekt;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static final String SRC_MAIN_RESOURCES_OUTPUT_JSON = "src/main/resources/output.json";

    @Override
    public void start(Stage stage) throws Exception {
        JsonMapping jsonMapping = new JsonMapping();
        RoutingData routingData = jsonMapping.getRoutingData();
        routingData.fillRoutingTables();
        jsonMapping.saveRoutingData(routingData);
        RoutingSimModel routingSimModel = new RoutingSimModel(routingData, false);
        while (true) {
            System.out.println(routingData.getIteration() + "\n");
            routingSimModel.simulate();
            RoutingData routingDataSerialized = jsonMapping.getRoutingData(SRC_MAIN_RESOURCES_OUTPUT_JSON);
            jsonMapping.saveRoutingData(routingData);
            if (Boolean.FALSE.equals(routingSimModel.getIsChanged())) {
                break;
            }
        }

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
