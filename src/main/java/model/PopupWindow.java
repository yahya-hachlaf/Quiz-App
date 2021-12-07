package model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PopupWindow {

    public static void display(String message) {
        Stage stage = new Stage();
        stage.setTitle("Info");

        Label label = new Label(message);
        Button button = new Button("OK");

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stage.close();
            }
        });

        VBox vBox = new VBox(10);    // Abstand = 10
        vBox.getChildren().addAll(label, button);    // zuerst Label dann Button
        vBox.setAlignment(Pos.CENTER);   // zentriere alles

        Scene scene = new Scene(vBox);

        stage.setScene(scene);
        stage.show();
    }
}
