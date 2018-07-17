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
        QuizDB database = new QuizDB();
        QuizCLI cli = new QuizCLI(database);
        cli.run();
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
        String userReply;

        printWelcome();
        printCategoryMenu();
        userReply = getCategoryInput();
        System.out.println("Selected category: " + userReply + "\n");
        QuizSession quizSession = QuizSession.createShortSession(userReply, db);
        printQuestion(quizSession, 1); // Test output
        boolean stillPlaying = true;
        while (stillPlaying) {

            printWelcome();
            printCategoryMenu();
            userReply = getCategoryInput();
            System.out.println("Selected category: " + userReply);
            QuizSession quizSession = QuizSession.createShortSession(userReply, db);
            List<Question> questions = quizSession.getQuestions();
            int qNum = 1;
            for (Question q : questions) {
                printQuestion(q, qNum);
                int answer = getQuestionInput();
                quizSession.setUserAnswer(q, q.getOptions().get(answer - 1));
                qNum++;
            }

            boolean onEndOfQuiz = true;
            while (onEndOfQuiz) {
                printEndOfQuiz(quizSession);
                int decision = getEndOfQuizInput();
                switch (decision) {
                    case 1:
                        printQuizExplanation(quizSession);
                        input.nextLine();
                        input.nextLine();
                        break;
                    case 2:
                        onEndOfQuiz = false;
                        break;
                    case 3:
                        printUserScoreList();
                        input.nextLine();
                        input.nextLine();
                        break;
                    case 4:
                        stillPlaying = false;
                        onEndOfQuiz = false;
                        break;
                    default:
                        System.out.print("Invalid input. Try again:");
                        break;
                }
            }
        }
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

    public void printQuestion(Question question, int qNum){
        StringBuilder output = new StringBuilder();

        output.append("Q").append(qNum).append(":\n");
        output.append(question.getBodyText()).append("\n");
        List<Option> options = question.getOptions();
        for(int i = 1; i <= options.size(); i++) {
            output.append(i).append(") ").append(options.get(i - 1).getOptionText()).append("\n");
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
        StringBuilder output = new StringBuilder("GeeQuiz\n");
        List <Question> questions = session.getQuestions();
        int questionNumber = 1;
        for (Question q: questions) {
            output.append("Q").append(questionNumber).append(":\n");
            output.append(q.getBodyText()).append("\n\n");
            output.append(q.getExplanation()).append("\n\n");
            List<Option> options = q.getOptions();
            int optionNumber = 1;
            for (Option o : options) {
                output.append(optionNumber).append(") ").append(o.getOptionText());
                if (o.isCorrect())
                    output.append(" (Correct)");
                // Todo: Add (Wrong) for when user answer is not the correct option
                optionNumber++;
            }
            questionNumber++;
        }
        System.out.println(output.toString());
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

    public String getCategoryInput() {
            int chosen = 1;
            if(input.hasNextInt()) {
                chosen = input.nextInt();
            }
            return db.getQuestionCategories().get(chosen - 1);
    }

    public int getQuestionInput() {
        int chosen = 1;
        if(input.hasNextInt()) {
            chosen = input.nextInt();
        }
        return chosen;
    }

    public int getEndOfQuizInput() {
        int chosen = 1;
        if(input.hasNextInt()) {
            chosen = input.nextInt();
        }
        return chosen;
    }
}
