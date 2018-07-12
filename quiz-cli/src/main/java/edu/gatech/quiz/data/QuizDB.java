package edu.gatech.quiz.data;

import edu.gatech.quiz.helpers.*;

import java.sql.*;
import java.util.*;

/**
 * CS3300 Project 2
 */
public class QuizDB {
    public List<String> getQuestionCategories() {
        Connection c = getConnection();
        List<String> categories = new ArrayList<>();

        try {
            String query = "SELECT title FROM categories";
            PreparedStatement statement = c.prepareStatement(query);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                String categoryName = result.getString("title");
                categories.add(categoryName);
                // System.out.println(categoryName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    public List<Question> getCategoryQuestions(String category) {
        Connection c = getConnection();
        List<Question> questions = null;

        try {
            String catQuery = "SELECT * FROM categories WHERE title=?";
            PreparedStatement catStatement = c.prepareStatement(catQuery);
            catStatement.setString(1, category);
            ResultSet catResult = catStatement.executeQuery();
            int catID = 0;

            while (catResult.next()) {
                catID = catResult.getInt("id");
                questions = new ArrayList<>();
                // System.out.println(categoryName);
            }

            String qQuery = "SELECT * FROM questions WHERE category_id=?";
            PreparedStatement qStatement = c.prepareStatement(qQuery);
            qStatement.setString(1, Integer.toString(catID));
            ResultSet qResult = qStatement.executeQuery();

            while (qResult.next()) {
                String body = qResult.getString("body");
                String explanation = qResult.getString("explanation");
                int qID = qResult.getInt("id");
                List<Option> options = new ArrayList<>();

                String aQuery = "SELECT * FROM answers WHERE question_id=?";
                PreparedStatement aStatement = c.prepareStatement(aQuery);
                aStatement.setString(1, Integer.toString(qID));
                ResultSet aResult = aStatement.executeQuery();

                while (aResult.next()) {
                    options.add(new Option(aResult.getString("body"), (aResult.getInt("correct") == 1)));
                }

                questions.add(new Question(category, body, options, explanation));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return questions;
    }

    private Connection getConnection() {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:quiz.db");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        return c;
    }
}