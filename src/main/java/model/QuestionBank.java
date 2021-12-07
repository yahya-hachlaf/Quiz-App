package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class QuestionBank {
    // Fragen aus der Datenbank laden
    // Fragen Objekte erstellen
    // Fragen Objekte in eine Liste laden
    // List zurückgeben

    // Database Stuff
    private ArrayList<Question> questionList = new ArrayList<>();

    public void loadCategoryQuestions(Statement statement, String categoryTable) {
        String QUERY_DATA_FROM_TABLE = "SELECT*FROM " + categoryTable;    // SQL Anfrage
        ResultSet rs = null;
        try {
            rs = statement.executeQuery(QUERY_DATA_FROM_TABLE);
            while (rs.next()) {
                int question_id = rs.getInt(1);
                String question_text = rs.getString(2);

                boolean question_answer;
                question_answer = rs.getInt(3) == 1;

                String question_complement = rs.getString(4);

                Question question = new Question(question_id, question_text, question_answer, question_complement);

                questionList.add(question);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(ArrayList<Question> questionList) {
        this.questionList = questionList;
    }
}
