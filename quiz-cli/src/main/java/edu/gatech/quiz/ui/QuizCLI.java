package edu.gatech.quiz.ui;

import edu.gatech.quiz.data.QuizDB;
import edu.gatech.quiz.helpers.*;

import java.util.*;

/**
 * CS3300 Project 2
 * This class can be left AS IS. No implementation needed.
 *
 * Extra Credit will be given if the interface (IE this class) is implemented correctly.
 */
public class QuizCLI {
    final private QuizDB db;
    Scanner input = new Scanner(System.in);
    Map<String, List<Integer>> userAnswers;

    public static void main(String[] args) {
       // Provide menu driven quiz access via CLI
    }

    public QuizCLI(QuizDB db) {
        this.db = db;
        userAnswers = new HashMap<String, List<Integer>>();
        for (String category : db.getQuestionCategories()) {
            List<Integer> toPut = new ArrayList<Integer>();
            toPut.add(0);
            toPut.add(0);
            userAnswers.put(category, toPut);
        }
    }

    public void run() {
        // Add code here
    }

    public void printWelcome() {
        System.out.println("Welcome to GeeQuiz, a Computer Science quiz app!\n\n" +
                "Please choose a question category from the list " +
                "below:");
    }

    public void printCategoryMenu() {
        StringBuilder output = new StringBuilder();
        List<String> categories = db.getQuestionCategories();
        for (int i = 1; i <= categories.size(); i++) {
            output.append("\t").append(i).append(") ").append(categories.get(i - 1)).append("\n");
        }
        output.append("\nPlease choose a category: ");
        System.out.println(output.toString());
    }

    public void printQuestion(QuizSession session, int questionNumber){
        StringBuilder output = new StringBuilder();
        Question question = session.getQuestions().get(questionNumber);
        output.append("Q").append(questionNumber).append(":\n");
        output.append(question.getBodyText()).append("\n");
        List<Option> options = question.getOptions();
        for(int i = 1; i <= options.size(); i++) {
            output.append(i).append(") ").append(options.get(i - 1)).append("\n");
        }
        output.append("\nPlease choose an answer: ");


        System.out.print(output.toString());
    }

    public void printEndOfQuiz(QuizSession session) {
        String output = "Your score in this session:" +
                session.getScore() + "/" +
                session.getQuestions().size() + "\n\n" +
                "Please choose any of the following options:\n" +
                "\t 1) See explanation\n" +
                "\t 2) Take a new quiz\n" +
                "\t 3) See dashboard\n" +
                "\t 4) Quit\n\nPlease choose your option: ";
        System.out.println(output);
    }

    public void printQuizExplanation(QuizSession session){

    }

    public Map<String, List<Integer>> getUserSessionScore() {
        return userAnswers;
    }

    public void setUserSessionScore(String category, int score, int answered) {
        if (!userAnswers.containsKey(category)) {
            List<Integer> toPut = new ArrayList<Integer>();
            toPut.add(score);
            toPut.add(answered);

            userAnswers.put(category, toPut);
        } else {
            List<Integer> toPut = new ArrayList<Integer>();
            toPut.add(score);
            int previouslyAnswered = userAnswers.get(category).get(1);
            toPut.add((answered + previouslyAnswered));

            userAnswers.put(category, toPut);
        }
    }

    public void printUserScoreList() {
        System.out.println("GeeQuiz: Dashboard \n\t");
        int i = 1;
        for (String category : db.getQuestionCategories()) {
            System.out.println(Integer.toString(i++) + ") " + category + "\t\t"
                + userAnswers.get(category).get(0) + "/" + userAnswers.get(category).get(1) + "\n\t");
        }
        System.out.println("Press any key to return: ");
    }
}
