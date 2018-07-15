import edu.gatech.quiz.data.QuizDB;
import edu.gatech.quiz.ui.QuizCLI;
import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

public class QuizExtraTests {
    QuizDB db;
    QuizCLI cli;

    @Before
    public void setUp() {
        db = new QuizDB();
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
        cli = new QuizCLI(db);
        assertNotNull(cli.getUserAnswers());
    }

    @Test
    public void testUserAnswersGetScores() {
        UserAnswers ua = new UserAnswers();
        assertEquals(new HashMap<String, Integer>, ua.getScores());
    }

    @Test
    public void testUserAnswersSetScores() {
        UserAnswers ua = new UserAnswers();
        String cat = "C Programming Mock Tests";
        int score = 10;
        ua.setScores(cat, score);
        assertEquals(10, ua.getScores().get(cat));

        score = 20;
        ua.setScores(cat, score);
        assertEquals(20, ua.getScores().get(cat));
        assertEquals(1, ua.getScores().size());
    }
}
