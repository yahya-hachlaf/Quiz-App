package menu;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.PopupWindow;

import java.io.IOException;

public class ControllerMenu {

    @FXML
    void startButton_Clicked(ActionEvent event) {

        Button button = (Button) event.getSource();
        Stage primaryStage = (Stage) button.getScene().getWindow();
        primaryStage.close();

        try {
            goToCategoryOverview();
        } catch (IOException e) {
            e.printStackTrace();
            PopupWindow.display("Datei nicht gefunden \n Programm bitte neustarten");
        }
    }


    @FXML
    void scoreButton_Clicked(ActionEvent event) {
        System.out.println("Score");
    }

    @FXML
    void quitButton_Clicked(ActionEvent event) {
        Platform.exit();
    }

    public void goToCategoryOverview() throws IOException {
        Stage stage2 = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();

        Pane root = fxmlLoader.load(getClass().getResource("menu2.fxml").openStream());

        Scene scene2 = new Scene(root);

        stage2.setScene(scene2);
        stage2.setTitle("Kategorie Auswahl");
        stage2.setResizable(false);
        stage2.show();

    }
}

