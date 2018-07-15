import edu.gatech.quiz.data.QuizDB;
import edu.gatech.quiz.ui.QuizCLI;
import static org.junit.Assert.*;

import java.util.*;

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
        assertEquals(new HashMap<String, Integer>, cli.getUserAnswers());
    }

    @Test
    public void testUserAnswersSetScores() {
        cli.run();

    }
}
