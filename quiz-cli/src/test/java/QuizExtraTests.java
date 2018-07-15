import edu.gatech.quiz.data.QuizDB;
import edu.gatech.quiz.ui.QuizCLI;
import static org.junit.Assert.*;

import java.util.*;
import java.io.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class QuizExtraTests {
    QuizDB db;
    QuizCLI cli;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @Before
    public void setUp() {
        db = new QuizDB();
        cli = new QuizCLI(db);
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    // Create multiple tests of the same feature/concept if testing
    // different cases
    // Example, testChooseCategory1 and testChooseCategory2

    @Test
    public void testTitleDisplay() {

    }

    @Test
    public void testCategoryPrint() {

    }

    @Test
    public void testChooseCategory() {

    }

    @Test
    public void testQuestionOutput() {

    }

    @Test
    public void testQuestionInput() {

    }

    @Test
    public void testEndOfQuizScore() {

    }

    @Test
    public void testDisplayEndOfQuizOptions() {

    }

    @Test
    public void testEndOfQuizInput() {

    }

    @Test
    public void testEndOfQuizToExplanation() {

    }

    @Test
    public void testEndOfQuizToTakeNewQuiz() {

    }

    @Test
    public void testEndOfQuizToDashboard() {

    }

    @Test
    public void testEndOfQuizToQuit() {

    }

    @Test
    public void testExplanationOutput() {

    }

    @Test
    public void testDashBoardOutput() {

    }

    @Test
    public void testUserAnswersCreate() {
        assertNotNull(cli.getUserAnswers());
    }

    @Test
    public void testUserAnswersGetScores() {
        Map<String, List<Integer>> toCompare = new HashMap<String, List<Integer>>();
        for (String category : db.getQuestionCategories()) {
            List<Integer> runningScore = new ArrayList<>();
            runningScore.add(0);
            runningScore.add(0);
            toCompare.put(category, runningScore);
        }
        assertEquals(toCompare, cli.getUserAnswers());
    }

    @Test
    public void testUserAnswersSetScores() {
        String cat = "Theory of Computation Mock Tests";
        int score = 1;
        int answered = 4;

        cli.setUserAnswers(cat, score, answered);
        Map<String, List<Integer>> toCompare = new HashMap<String, List<Integer>>();
        for (String category : db.getQuestionCategories()) {
            List<Integer> runningScore = new ArrayList<>();
            runningScore.add(0);
            runningScore.add(0);
            toCompare.put(category, runningScore);
        }
        List<Integer> runningScore = new ArrayList<>();
        runningScore.add(1);
        runningScore.add(4);
        toCompare.put("Theory of Computation Mock Tests", runningScore);

        assertEquals(toCompare.get("Theory of Computation Mock Tests"), cli.getUserAnswers());

        cat = "Theory of Computation Mock Tests";
        score = 2;
        answered = 4;

        cli.setUserAnswers(cat, score, answered);

        runningScore = new ArrayList<>();
        runningScore.add(3);
        runningScore.add(8);
        toCompare.put("Theory of Computation Mock Tests", runningScore);
        assertEquals(toCompare.get("Theory of Computation Mock Tests"), cli.getUserAnswers());
    }

    @Test
    public void testPrintUserAnswers() {
        String cat = "Theory of Computation Mock Tests";
        int score = 1;
        int answered = 4;

        cli.setUserAnswers(cat, score, answered);

        cli.printUserAnswers();
        assertEquals(
                "GeeQuiz: Dashboard \n\t" +
                        "1) C Programming Mock Tests\t\t\t\t" + "0/0\n\t" +
                        "2) C++ Programming Mock Tests\t\t\t" + "0/0\n\t" +
                        "3) Engineering Mathematics\t\t\t\t\t" + "0/0\n\t" +
                        "4) Computer Organization and Architecture\t" + "0/0\n\t" +
                        "5) Data Structures Mock Tests\t\t\t\t" + "0/0\n\t" +
                        "6) Java Programming Mock Tests\t\t\t" + "0/0\n\t" +
                        "7) Theory of Computation Mock Tests\t\t" + "1/4\n\t" +
                        "8) GATE Mock Tests\t\t\t\t\t\t" + "0/0\n\t" +
                        "9) Algorithms Mock Tests\t\t\t\t\t" + "0/0\n\t" +
                        "10) Operating Systems Mock Tests\t\t\t" + "0/0\n\t" +
                        "11) DBMS Mock Tests\t\t\t\t\t\t" + "0/0\n\t" +
                        "12) Computer Networks Mock Tests\t\t\t" + "0/0\n\t" +
                        "13) Aptitude Mock Tests\t\t\t\t\t" + "0/0\n\t" +
                        "14) Other Topics in Computer Science\t\t" + "0/0\n\n" +
                        "Press any key to return: ", outContent.toString());
    }
}
