package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import menu.ControllerMenu;
import model.PopupWindow;
import model.Question;

import java.io.IOException;
import java.util.ArrayList;

public class ControllerQuiz {

    @FXML
    private Label questionLabel;

    @FXML
    private Button trueButton;

    @FXML
    private Button falseButton;

    @FXML
    private Label questionCountLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label bestScoreLabel;

    @FXML
    private Label answerStatusLabel;

    @FXML
    private HBox progressHboxbar;

    @FXML
    private Button backButton;

    private ArrayList<Question> questionList = new ArrayList<>();

    // Fragen Zeug
    private boolean pickAnswer = false;      // wird hier je nach was der Nutzer gedrückt hat entweder true oder false gespeichert.
    private int currentQuestionNumber = 0;
    private int score = 0;
    private int questionCount = 0;          // Anzahl der Gesamten Fragen

    @FXML
    void answerButton_Clicked(ActionEvent event) {
        Button tappedButton = (Button) event.getSource();  // Gibt zurück, den Objekt für den Event, das beim Drücken stattgefunden ist

        // Bestimmer ob der Nutzer stimmt oder stimmt nicht Button geklickt hat
        if (tappedButton.getId().equals("trueButton")){
            // speichert der boolean Wert, um zu wissen beim welchem Button darum geht
            pickAnswer = true;
            score++;
        } else if (tappedButton.getId().equals("falseButton")){
            pickAnswer = false;
        }

        // 1. Antwort überprüfen
        checkAnswer();
        // 2. Eine Frage weiterspringen
        currentQuestionNumber ++;
        // 3. nächste anzeigen
        nextQuestion();
    }

    private void checkAnswer(){
        Question question = questionList.get(currentQuestionNumber);
        boolean answer = question.isQuestion_answer();
        String complement = question.getQuestion_complement();

        if (answer == pickAnswer ){
            if (complement != null){
                answerStatusLabel.setText("Richtig " + complement);
            } else {
                answerStatusLabel.setText("Richtig");
            }
        } else {
            if (complement != null){
                answerStatusLabel.setText("Falsch " + complement);
            } else {
                answerStatusLabel.setText("Falsch");
            }
        }
    }

    private void nextQuestion(){
        if (currentQuestionNumber <= questionCount - 1){
            questionLabel.setText(questionList.get(currentQuestionNumber).getQuestion_text());  // hole der current Question
            updateUi();
        } else {
            restart();
        }
    }

    private void updateUi(){
        scoreLabel.setText("Score: " + score);
        questionCountLabel.setText("" + (currentQuestionNumber+1) + "/" + questionCount);

        double progressHBoxBarWidth = (progressHboxbar.getWidth() / questionCount);
        progressHboxbar.getChildren().add(new Rectangle(progressHBoxBarWidth, 20, Color.BLUEVIOLET));
    }

    private void restart(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Glückwunsch");
        alert.setHeaderText("Glückwunsch zum beenden des Quizes");
        alert.setContentText("Neustart?");
        alert.showAndWait();

        currentQuestionNumber = 0;
        score = 0;
        nextQuestion();
        progressHboxbar.getChildren().clear();
        answerStatusLabel.setText("");
    }

    // Methoden
    public void setQuestion(ArrayList<Question> qList){
        questionList = qList;
        questionCount = qList.size();    // wie viele Elemente da sind
        System.out.println("Anzahl der Fragen: " + questionCount);

//        for (Question q : questionList) {
//            System.out.println("ID " + q.getQuestion_id());
//            System.out.println("Frage " + q.getQuestion_text());
//            System.out.println("Antwort " + q.isQuestion_answer());
//            System.out.println("Ergänzung " + q.getQuestion_complement());
//        }

        // Erste Grage Anzeigen
        if (questionList != null){
            String firstQuestion = questionList.get(0).getQuestion_text();
            questionLabel.setText(firstQuestion);
        }

        updateUi();
    }

    public void backButton_Clicked(ActionEvent event) {
        Button button = (Button) event.getSource();
        Stage primaryStage = (Stage) button.getScene().getWindow();
        primaryStage.close();

        try {
            ControllerMenu menu = new ControllerMenu();
            menu.goToCategoryOverview();
        } catch (IOException e) {
            e.printStackTrace();
            PopupWindow.display("Datei nicht gefunden \n Programm bitte neustarten");
        }
    }
}
