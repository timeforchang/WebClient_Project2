package edu.gatech.quiz.helpers;

import edu.gatech.quiz.data.QuizDB;

import java.util.*;

/**
 * CS3300 Project 2
 */
public class QuizSession {
    final private List<Question> questions;
    final private Map<Question, Option> userAnswers;
    private int score;

    private static int sessionType = -1;

    private QuizSession() {
        questions = new ArrayList<Question>();
        userAnswers = new HashMap<Question, Option>();
        score = 0;
    }

    public static QuizSession createShortSession(String category, QuizDB db) {
        QuizSession session = new QuizSession();
        List<Question> allQuestions = db.getCategoryQuestions(category);

        // Add code here

        return session;
    }

    public static QuizSession createLongSession(String category, QuizDB db) {
        QuizSession session = new QuizSession();

        // Add code here

        return session;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public Option getUserAnswer(Question q) {
        return userAnswers.get(q);
    }

    public void setUserAnswer(Question q, Option o) {
        userAnswers.put(q, o);
    }

    public int getScore() {
        return score;
    }

    public boolean solvedAll() {
        return (score == questions.size());
    }
}
