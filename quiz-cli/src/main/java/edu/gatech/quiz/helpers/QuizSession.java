package edu.gatech.quiz.helpers;

import edu.gatech.quiz.data.QuizDB;

import java.util.*;

/**
 * CS3300 Project 2
 */
public class QuizSession {
    static private List<Question> questions;
    final private Map<Question, Option> userAnswers;
    private int score;

    private QuizSession() {
        questions = new ArrayList<Question>();
        userAnswers = new HashMap<Question, Option>();
        score = 0;
    }

    public static QuizSession createShortSession(String category, QuizDB db) {
        QuizSession session = new QuizSession();
        List<Question> allQuestions = db.getCategoryQuestions(category);
        List<Question> questionsToShow = new ArrayList<Question>();

        if (allQuestions.size() <= 10) {
            Collections.shuffle(allQuestions);
            setQuestions(allQuestions);
        } else {
            Collections.shuffle(allQuestions);
            for (int i = 0; i < 10; i++) {
                questionsToShow.add(allQuestions.get(i));
            }
            setQuestions(questionsToShow);
        }

        return session;
    }

    public static QuizSession createLongSession(String category, QuizDB db) {
        QuizSession session = new QuizSession();

        List<Question> questionsToShow = db.getCategoryQuestions(category);
        Collections.shuffle(questionsToShow);
        setQuestions(questionsToShow);

        return session;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    private static void setQuestions(List<Question> list) {
        for (Question q : list) {
            questions.add(q);
        }
    }

    public Option getUserAnswer(Question q) {
        return userAnswers.get(q);
    }

    public void setUserAnswer(Question q, Option o) {
        if (o.isCorrect()) {
            if (!userAnswers.containsKey(q) || !userAnswers.get(q).isCorrect()) {
                score++;
            }
        }

        userAnswers.put(q, o);
    }

    public int getScore() {
        return score;
    }

    public boolean solvedAll() {
        return (score == questions.size());
    }
}
