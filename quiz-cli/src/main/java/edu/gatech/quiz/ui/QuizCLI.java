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
        QuizSession quizSession;
        boolean stillPlaying = true;

        while (stillPlaying) {
            printWelcome();
            printCategoryMenu();
            userReply = getCategoryInput();
            System.out.println("\n\nSelected category: " + userReply);
            printChooseSession();
            int sessionChoice = getSessionInput();
            if (sessionChoice == 1) {
                quizSession = QuizSession.createLongSession(userReply, db);
            } else {
                quizSession = QuizSession.createShortSession(userReply, db);
            }
            List<Question> questions = quizSession.getQuestions();
            int qNum = 1;
            for (Question q : questions) {
                System.out.println("\n");
                printQuestion(q, qNum);
                int answer = getQuestionInput();
                quizSession.setUserAnswer(q, q.getOptions().get(answer - 1));
                qNum++;
            }
            setUserSessionScore(userReply, quizSession.getScore(), quizSession.getAnswered());
            boolean onEndOfQuiz = true;
            int decision;
            do {
                printEndOfQuiz(quizSession);
                decision = getEndOfQuizInput();
                switch (decision) {
                    case 1:
                        printQuizExplanation(quizSession);
                        System.out.println("See explanations for each question above.\n");
                        break;
                    case 2:
                        onEndOfQuiz = false;
                        break;
                    case 3:
                        printUserScoreList();
                        System.out.println("\nSee running total of scores above.\n");
                        break;
                    case 4:
                        stillPlaying = false;
                        System.out.println("Terminating.\n");
                        System.exit(0);
                        break;
                    default:
                        System.out.print("Invalid input. Try again: ");
                        break;
                }
            } while (onEndOfQuiz);
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
        System.out.print(output.toString());
    }

    public void printChooseSession() {
        StringBuilder output = new StringBuilder();
        output.append("\n\nPlease choose a session: \n");
        output.append("\t1) Long session\n");
        output.append("\t2) Short session\n");
        output.append("\nPlease choose a category: ");
        System.out.print(output.toString());
    }

    public void printQuestion(Question question, int qNum){
        StringBuilder output = new StringBuilder();

        output.append("Q").append(qNum).append(":\n");
        output.append(question.getBodyText()).append("\n\n");
        List<Option> options = question.getOptions();
        for(int i = 1; i <= options.size(); i++) {
            output.append(i).append(") ").append(options.get(i - 1).getOptionText()).append("\n");
        }
        output.append("\nPlease choose an answer: ");
        System.out.print(output.toString());
    }

    public void printEndOfQuiz(QuizSession session) {
        String output = "Your score in this session: " +
                session.getScore() + "/" +
                session.getQuestions().size() + "\n\n" +
                "Please choose any of the following options:\n" +
                "\t 1) See explanation\n" +
                "\t 2) Take a new quiz\n" +
                "\t 3) See dashboard\n" +
                "\t 4) Quit\n\nPlease choose your option: ";
        System.out.print(output);
    }

    public void printQuizExplanation(QuizSession session){
        StringBuilder output = new StringBuilder("\nGeeQuiz Answer Explanations: \n");
        List <Question> questions = session.getQuestions();
        int questionNumber = 1;
        for (Question q: questions) {
            output.append("Q").append(questionNumber).append(": ");
            output.append(q.getBodyText()).append("\n\n");
            output.append(q.getExplanation()).append("\n\n");
            List<Option> options = q.getOptions();

            Option userOption = session.getUserAnswer(q);

            int optionNumber = 1;
            for (Option o : options) {
                output.append(optionNumber).append(") ").append(o.getOptionText());
                if (o.isCorrect()) {
                    output.append(" (Correct)");
                } else if (o == userOption) {
                    output.append(" (Wrong)");
                }
                output.append("\n");
                optionNumber++;
            }
            output.append("\n");
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
            int previousCorrectScore = userAnswers.get(category).get(0);
            int previouslyAnswered = userAnswers.get(category).get(1);
            toPut.add(score + previousCorrectScore);
            toPut.add((answered + previouslyAnswered));
            userAnswers.put(category, toPut);
        }
    }

    public void printUserScoreList() {
        System.out.println("\nGeeQuiz: Dashboard\t");
        int i = 1;
        for (String category : db.getQuestionCategories()) {
            System.out.println("\t" + Integer.toString(i++) + ") " + category + "\t\t"
                + userAnswers.get(category).get(0) + "/" + userAnswers.get(category).get(1));
        }
    }

    public String getCategoryInput() {
            int chosen = 0;
            if (input.hasNextInt()) {
                chosen = input.nextInt();
            }

            while (chosen > db.getQuestionCategories().size() || chosen < 1) {
                System.out.println("Invalid category, please choose again: ");
                if (input.hasNextInt()) {
                    chosen = input.nextInt();
                }
            }
            return db.getQuestionCategories().get(chosen - 1);
    }

    public int getSessionInput() {
        int chosen = 0;
        if (input.hasNextInt()) {
            chosen = input.nextInt();
        }

        while (chosen > 2 || chosen < 1) {
            System.out.println("Invalid session choice, please choose again: ");
            if (input.hasNextInt()) {
                chosen = input.nextInt();
            }
        }
        return chosen;
    }

    public int getQuestionInput() {
        int chosen = 0;
        if(input.hasNextInt()) {
            chosen = input.nextInt();
        }

        while (chosen > 4 || chosen < 1) {
            System.out.println("Invalid option, please choose again: ");
            if (input.hasNextInt()) {
                chosen = input.nextInt();
            }
        }
        return chosen;
    }

    public int getEndOfQuizInput() {
        int chosen = 0;
        if(input.hasNextInt()) {
            chosen = input.nextInt();
        }
        return chosen;
    }
}
