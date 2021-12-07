package menu2;

import application.ControllerQuiz;
import dbUtil.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.PopupWindow;
import model.Question;
import model.QuestionBank;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerMenu2 implements Initializable {

    @FXML
    private Button categoryComputer;

    @FXML
    private Button categoryProLan;

    @FXML
    private Button categoryJava;

    @FXML
    private Button categoryGermany;

    @FXML
    private Button categoryAnimal;

    @FXML
    private Label categoryLabel;

    @FXML
    private HBox statusHBox;

    @FXML
    private Circle dbStatusLight;

    // Liste mit Fragen
    ArrayList<Question> quizQuestionList = new ArrayList<>();

    //Welche Kategorie soll im Quiz erscheinen
    // Quasi welche Kategorie der Nutzer gewählt hat, wird hier gespeichert
    ArrayList<String> categoryList = new ArrayList<String>();

    // Database
    Database database = new Database();
    QuestionBank questionBank = new QuestionBank();

    //****************** Category ******************

    @FXML
    void categoryComputerButton_Clicked(ActionEvent event) {
        Button button = (Button) event.getSource();
        getCategory(button.getText(), button);
    }

    @FXML
    void categoryProLanButto_Clicked(ActionEvent event) {
        Button button = (Button) event.getSource();
        getCategory(button.getText(), button);
    }

    @FXML
    void categoryJavaButton_Clicked(ActionEvent event) {
        Button button = (Button) event.getSource();
        getCategory(button.getText(), button);
    }

    @FXML
    void categoryGermanyButton_Clicked(ActionEvent event) {
        Button button = (Button) event.getSource();
        getCategory(button.getText(), button);
    }

    @FXML
    void categoryAnimalButton_Clicked(ActionEvent event) {
        Button button = (Button) event.getSource();
        getCategory(button.getText(), button);
    }

   //****************** HBox Status ******************
    @FXML
    void cancelButton_Clicked(ActionEvent event) {
        categoryComputer.setDisable(false);
        categoryProLan.setDisable(false);
        categoryJava.setDisable(false);
        categoryGermany.setDisable(false);
        categoryAnimal.setDisable(false);

        statusHBox.setDisable(true);
        categoryLabel.setText("");
    }

    @FXML
    void okButton_Clicked(ActionEvent event) {

        for (String category: categoryList) {
            questionBank.loadCategoryQuestions(database.getStatement(), category);
        }

        quizQuestionList = questionBank.getQuestionList();
        // Zum Testing
//        for (Question q: quizQuestionList) {
//            System.out.println("ID " + q.getQuestion_id());
//            System.out.println("Frage " + q.getQuestion_text());
//            System.out.println("Antwort " + q.isQuestion_answer());
//            System.out.println("Ergänzung " + q.getQuestion_complement());
//
//        }

        // Das Kategorie Fenster schließen
        Button okButton = (Button) event.getSource();     // mach die Event Quelle als Button
        Stage stage = (Stage) okButton.getScene().getWindow();     // Welche Stage und welchem Hauptfenster
        stage.close();

        try {
            startQuiz();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //****************** Initialize ******************

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statusHBox.setDisable(true);

        // Database Stuff
        boolean dbConnection = database.open();

        if(dbConnection){
            dbStatusLight.setFill(Color.GREEN);
        } else{
            dbStatusLight.setFill(Color.RED);
            PopupWindow.display("Keine Verbindung möglich \n Programm bitte neu starten");
        }

    }

    //****************** HBox Status ******************

    private void getCategory(String category, Button button){
        // Kategorie speichern
        categoryList.add(category);

        // Unten im Label den Namen anzeigen
        categoryLabel.setText(categoryLabel.getText() + " " + category);

        // Kategorie Button ausblenden
        button.setDisable(true);

        // Statusbox einschalten
        statusHBox.setDisable(false);

    }

    private void startQuiz() throws IOException {
        Stage stage3 = new Stage();
        stage3.setTitle("Quiz App");
        FXMLLoader fxmlLoader = new FXMLLoader();

        Pane root = fxmlLoader.load(getClass().getResource("/menu/quiz.fxml").openStream());

        // Die Arraylist mit den Fragen muss zum ControllerQuiz, mit getController() erhält man die Controller Klasse aud der FXML Datei
        ControllerQuiz controllerQuiz = fxmlLoader.getController();   //ic
        controllerQuiz.setQuestion(quizQuestionList);  // übergebe die Question von ControllerMenu2 zu ControllerQuiz mit Hilfe von setQuestion();

        Scene scene3 = new Scene(root);

        stage3.setScene(scene3);
        stage3.setResizable(false);
        stage3.show();
    }

}

