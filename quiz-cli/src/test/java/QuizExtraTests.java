import edu.gatech.quiz.data.QuizDB;
import edu.gatech.quiz.helpers.Option;
import edu.gatech.quiz.helpers.Question;
import edu.gatech.quiz.helpers.QuizSession;
import edu.gatech.quiz.ui.QuizCLI;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;
import java.io.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class QuizExtraTests {
    QuizDB db;
    QuizCLI cli;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private ByteArrayInputStream inContent;
    private PrintStream originalOut = System.out;
    private InputStream originalIn = System.in;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @Before
    public void setUp() {
        db = new QuizDB();
        cli = new QuizCLI(db);
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }


    // TODO: Update test methods with correct arguments

    @Test
    public void testTitleDisplay() {
        cli.printWelcome();
        assertEquals("Welcome to GeeQuiz, a Computer Science quiz app!\n\n" +
                        "Please choose a question category from the list " +
                        "below:",
                outContent.toString());
    }

    @Test
    public void testCategoryPrint() {
        cli.printCategoryMenu();
        assertEquals(
        "1) C Programming Mock Tests\n\t" +
        "2) C++ Programming Mock Tests\n\t" +
        "3) Engineering Mathematics\n\t" +
        "4) Computer Organization and Architecture\n\t" +
        "5) Data Structures Mock Tests\n\t" +
        "6) Java Programming Mock Tests\n\t" +
        "7) Theory of Computation Mock Tests\n\t" +
        "8) GATE Mock Tests\n\t" +
        "9) Algorithms Mock Tests\n\t" +
        "10) Operating Systems Mock Tests\n\t" +
        "11) DBMS Mock Tests\n\t" +
        "12) Computer Networks Mock Tests\n\t" +
        "13) Aptitude Mock Tests\n\t" +
        "14) Other Topics in Computer Science\n\n" +
        "Please choose a category: ", outContent.toString());
    }

    @Test
    public void testChooseCategory() {
        String input = "1";
        inContent = new ByteArrayInputStream(input.getBytes());
        System.setIn(inContent);
        assertEquals("1", cli.getCategoryInput());
    }

    @Test
    public void testQuestionOutput() {
        // Parameter represents the question #
        String questionId = "1";
        String categoryId = "1";
        //cli.printQuestion(questionId, categoryId);
        assertEquals("Q1:\n" +
                "#include‹stdio.h›\n"+
                "int main()\n"+
                "{\n"+
                "    struct site\n"+
                "    {\n"+
                "        char name[] = \"GeeksQuiz\";\n"+
                "        int no_of_pages = 200;\n"+
                "    };\n"+
                "    struct site *ptr;\n"+
                "    printf(\"%d \", ptr->no_of_pages);\n"+
                "    printf(\"%s\", ptr->name);\n"+
                "    getchar();\n"+
                "    return 0;\n"+
                "}"+
                "\n\t" +
                "1) 200 GeeksQuiz\n\t" +
                "2) 200\n\t" +
                "3) Runtime Error\n\t" +
                "4) Compiler  Error\n\n" +
                "Please choose an answer: ", outContent.toString());
    }

    @Test
    public void testQuestionInput() {
        String input = "4";
        inContent = new ByteArrayInputStream(input.getBytes());
        System.setIn(inContent);
        assertEquals("4", cli.getQuestionInput());
    }

    @Test
    public void testEndOfQuizScore() {
        String categoryId = "1";
        //cli.printEndOfQuiz(categoryId);
        assertEquals(
                "Your score in this session: 0/10\n\n" +
                "Please choose any of the following options:\n" +
                "1) See explanation\n\t" +
                "2) Take a new quiz\n\t" +
                "3) Quiz\n\n" +
                "Please choose your option:",
                outContent.toString());
    }

    @Test
    public void testEndOfQuizToExplanation() {
        String input = "1";
        inContent = new ByteArrayInputStream(input.getBytes());
        System.setIn(inContent);
        assertEquals("1", cli.getEndOfQuizInput());
    }

    @Test
    public void testEndOfQuizToTakeNewQuiz() {
        String input = "2";
        inContent = new ByteArrayInputStream(input.getBytes());
        System.setIn(inContent);
        assertEquals("2", cli.getEndOfQuizInput());
    }

    @Test
    public void testEndOfQuizToDashboard() {
        String input = "3";
        inContent = new ByteArrayInputStream(input.getBytes());
        System.setIn(inContent);
        assertEquals("3", cli.getEndOfQuizInput());
    }

    @Test
    public void testEndOfQuizToQuit() {
        String input = "4";
        inContent = new ByteArrayInputStream(input.getBytes());
        System.setIn(inContent);
        assertEquals("4", cli.getEndOfQuizInput());
    }

    @Test
    public void testExplanationOutput() {
        QuizSession quizSession = QuizSession.createShortSession("Computer Organization and Architecture", db);
        Question question = quizSession.getQuestions().get(2);
        Option userOption = quizSession.getUserAnswer(question);
        //cli.printQuizExplanation(quizSession, question, userOption);
        assertEquals("GeeQuiz\n" +
        "The size of the cache tag directory is\n\n" +
        "16 bit address   2 bit valid   1 modified   1 replace  Total bits  = 20 20 × no. of blocks   = 160 K bits.\n\n\t" +
        "1) 160 Kbits (Correct)\n\t" +
        "2) 136 bits (Wrong)\n\t" +
        "3) 40 Kbits\n\t" +
        "4) 32 bits\n", outContent.toString());
    }

    @Test
    public void testUserAnswersCreate() {
        assertNotNull(cli.getUserSessionScore());
    }

    @Test
    public void testUserAnswersGetScores() {
        Map<Integer, List<Integer>> toCompare = new HashMap<Integer, List<Integer>>();
        for (String category : db.getQuestionCategories()) {
            List<Integer> runningScore = new ArrayList<>();
            runningScore.add(0);
            runningScore.add(0);
            toCompare.put(db.getQuestionCategories().indexOf(category), runningScore);
        }
        assertEquals(toCompare, cli.getUserSessionScore());
    }

    @Test
    public void testUserAnswersSetScores() {
        String cat = "Theory of Computation Mock Tests";
        int score = 1;
        int answered = 4;

        cli.setUserSessionScore(cat, score, answered);
        Map<String, List<Integer>> toCompare = new HashMap<String, List<Integer>>();
        for (String category : db.getQuestionCategories()) {
            List<Integer> runningScore = new ArrayList<>();
            runningScore.add(0);
            runningScore.add(0);
            toCompare.put(cat, runningScore);
        }
        List<Integer> runningScore = new ArrayList<>();
        runningScore.add(1);
        runningScore.add(4);
        toCompare.put(cat, runningScore);

        assertEquals(toCompare.get(cat), cli.getUserSessionScore());

        cat = "Theory of Computation Mock Tests";
        score = 2;
        answered = 4;

        cli.setUserSessionScore(cat, score, answered);

        runningScore = new ArrayList<>();
        runningScore.add(3);
        runningScore.add(8);
        toCompare.put(cat, runningScore);
        assertEquals(toCompare.get(cat), cli.getUserSessionScore());
    }

    @Test
    public void testPrintUserAnswers() {
        String cat = "Theory of Computation Mock Tests";
        int score = 1;
        int answered = 4;

        cli.setUserSessionScore(cat, score, answered);

        cli.printUserScoreList();
        assertEquals(
                "GeeQuiz: Dashboard \n\t" +
                        "1) C Programming Mock Tests\t\t" + "0/0\n\t" +
                        "2) C++ Programming Mock Tests\t\t" + "0/0\n\t" +
                        "3) Engineering Mathematics\t\t" + "0/0\n\t" +
                        "4) Computer Organization and Architecture\t\t" + "0/0\n\t" +
                        "5) Data Structures Mock Tests\t\t" + "0/0\n\t" +
                        "6) Java Programming Mock Tests\t\t" + "0/0\n\t" +
                        "7) Theory of Computation Mock Tests\t\t" + "1/4\n\t" +
                        "8) GATE Mock Tests\t\t" + "0/0\n\t" +
                        "9) Algorithms Mock Tests\t\t" + "0/0\n\t" +
                        "10) Operating Systems Mock Tests\t\t" + "0/0\n\t" +
                        "11) DBMS Mock Tests\t\t" + "0/0\n\t" +
                        "12) Computer Networks Mock Tests\t\t" + "0/0\n\t" +
                        "13) Aptitude Mock Tests\t\t" + "0/0\n\t" +
                        "14) Other Topics in Computer Science\t\t" + "0/0\n\n" +
                        "Press any key to return: ", outContent.toString());
    }
}
