import edu.gatech.quiz.data.QuizDB;
import edu.gatech.quiz.helpers.Option;
import edu.gatech.quiz.helpers.Question;
import edu.gatech.quiz.helpers.QuizSession;
import edu.gatech.quiz.ui.QuizCLI;
import java.util.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class QuizExtraTests {
    private QuizDB db;
    private QuizCLI cli;

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    @Rule
    public final TextFromStandardInputStream systemInRule = emptyStandardInputStream();

    @Before
    public void setUp() {
        db = new QuizDB();
        cli = new QuizCLI(db);
    }

    @Test
    public void testTitleDisplay() {
        cli.printWelcome();
        assertEquals("Welcome to GeeQuiz, a Computer Science quiz app!\n\n" +
                        "Please choose a question category from the list " +
                        "below:\n",
                systemOutRule.getLog());
    }

    @Test
    public void testCategoryPrint() {
        cli.printCategoryMenu();
        assertEquals(
        "\t1) C Programming Mock Tests\n" +
        "\t2) C++ Programming Mock Tests\n" +
        "\t3) Engineering Mathematics\n" +
        "\t4) Computer Organization and Architecture\n" +
        "\t5) Data Structures Mock Tests\n" +
        "\t6) Java Programming Mock Tests\n" +
        "\t7) Theory of Computation Mock Tests\n" +
        "\t8) GATE Mock Tests\n" +
        "\t9) Algorithms Mock Tests\n" +
        "\t10) Operating Systems Mock Tests\n" +
        "\t11) DBMS Mock Tests\n" +
        "\t12) Computer Networks Mock Tests\n" +
        "\t13) Aptitude Mock Tests\n" +
        "\t14) Other Topics in Computer Science\n" +
        "\nPlease choose a category: ", systemOutRule.getLog());
    }

    @Test
    public void testChooseCategory() {
        systemInRule.provideLines("1");
        assertEquals("C Programming Mock Tests", cli.getCategoryInput());
    }

    @Test
    public void testQuestionOutput() {
        List<Option> options = new ArrayList<>();
        options.add(new Option("a",false));
        options.add(new Option("b", false));
        options.add(new Option("c", true));
        options.add(new Option("d", false));
        Question q = new Question("Test", "This is the body text.", options, "Explanation");
        cli.printQuestion(q, 1);
        assertEquals("Q1:\n" +
                "This is the body text.\n\n" +
                "1) a\n" +
                "2) b\n" +
                "3) c\n" +
                "4) d\n" +
                "\nPlease choose an answer: ", systemOutRule.getLog());
    }

    @Test
    public void testQuestionInput() {
        systemInRule.provideLines("4");
        assertEquals(4, cli.getQuestionInput());
    }

    @Test
    public void testEndOfQuizScore() {
        QuizSession qs = QuizSession.createShortSession(
                "Theory of Computation Mock Tests", db);
        List<Question> questions = qs.getQuestions();
        for (Question q : questions) {
            qs.setUserAnswer(q, q.getOptions().get(1));
        }
        cli.printEndOfQuiz(qs);
        assertEquals("Your score in this session: 0/4\n\n" +
                "Please choose any of the following options:\n" +
                "\t 1) See explanation\n" +
                "\t 2) Take a new quiz\n" +
                "\t 3) See dashboard\n" +
                "\t 4) Quit\n\nPlease choose your option: ",
                systemOutRule.getLog());
    }
//
//    @Test
//    public void testEndOfQuizToExplanation() {
//        String input = "1";
//        inContent = new ByteArrayInputStream(input.getBytes());
//        System.setIn(inContent);
//        assertEquals("1", cli.getEndOfQuizInput());
//    }
//
//    @Test
//    public void testEndOfQuizToTakeNewQuiz() {
//        String input = "2";
//        inContent = new ByteArrayInputStream(input.getBytes());
//        System.setIn(inContent);
//        assertEquals("2", cli.getEndOfQuizInput());
//    }
//
//    @Test
//    public void testEndOfQuizToDashboard() {
//        String input = "3";
//        inContent = new ByteArrayInputStream(input.getBytes());
//        System.setIn(inContent);
//        assertEquals("3", cli.getEndOfQuizInput());
//    }
//
//    @Test
//    public void testEndOfQuizToQuit() {
//        String input = "4";
//        inContent = new ByteArrayInputStream(input.getBytes());
//        System.setIn(inContent);
//        assertEquals("4", cli.getEndOfQuizInput());
//    }
//
//    @Test
//    public void testExplanationOutput() {
//        QuizSession quizSession = QuizSession.createShortSession("Computer Organization and Architecture", db);
//        Question question = quizSession.getQuestions().get(2);
//        Option userOption = quizSession.getUserAnswer(question);
//        //cli.printQuizExplanation(quizSession, question, userOption);
//        assertEquals("GeeQuiz\n" +
//        "The size of the cache tag directory is\n\n" +
//        "16 bit address   2 bit valid   1 modified   1 replace  Total bits  = 20 20 × no. of blocks   = 160 K bits.\n\n\t" +
//        "1) 160 Kbits (Correct)\n\t" +
//        "2) 136 bits (Wrong)\n\t" +
//        "3) 40 Kbits\n\t" +
//        "4) 32 bits\n", outContent.toString());
//    }
//
//    @Test
//    public void testUserAnswersCreate() {
//        assertNotNull(cli.getUserSessionScore());
//    }
//
//    @Test
//    public void testUserAnswersGetScores() {
//        Map<Integer, List<Integer>> toCompare = new HashMap<Integer, List<Integer>>();
//        for (String category : db.getQuestionCategories()) {
//            List<Integer> runningScore = new ArrayList<>();
//            runningScore.add(0);
//            runningScore.add(0);
//            toCompare.put(db.getQuestionCategories().indexOf(category), runningScore);
//        }
//        assertEquals(toCompare, cli.getUserSessionScore());
//    }
//
//    @Test
//    public void testUserAnswersSetScores() {
//        String cat = "Theory of Computation Mock Tests";
//        int score = 1;
//        int answered = 4;
//
//        cli.setUserSessionScore(cat, score, answered);
//        Map<String, List<Integer>> toCompare = new HashMap<String, List<Integer>>();
//        for (String category : db.getQuestionCategories()) {
//            List<Integer> runningScore = new ArrayList<>();
//            runningScore.add(0);
//            runningScore.add(0);
//            toCompare.put(cat, runningScore);
//        }
//        List<Integer> runningScore = new ArrayList<>();
//        runningScore.add(1);
//        runningScore.add(4);
//        toCompare.put(cat, runningScore);
//
//        assertEquals(toCompare.get(cat), cli.getUserSessionScore());
//
//        cat = "Theory of Computation Mock Tests";
//        score = 2;
//        answered = 4;
//
//        cli.setUserSessionScore(cat, score, answered);
//
//        runningScore = new ArrayList<>();
//        runningScore.add(3);
//        runningScore.add(8);
//        toCompare.put(cat, runningScore);
//        assertEquals(toCompare.get(cat), cli.getUserSessionScore());
//    }
//
//    @Test
//    public void testPrintUserAnswers() {
//        String cat = "Theory of Computation Mock Tests";
//        int score = 1;
//        int answered = 4;
//
//        cli.setUserSessionScore(cat, score, answered);
//
//        cli.printUserScoreList();
//        assertEquals(
//                "GeeQuiz: Dashboard \n\t" +
//                        "1) C Programming Mock Tests\t\t" + "0/0\n\t" +
//                        "2) C++ Programming Mock Tests\t\t" + "0/0\n\t" +
//                        "3) Engineering Mathematics\t\t" + "0/0\n\t" +
//                        "4) Computer Organization and Architecture\t\t" + "0/0\n\t" +
//                        "5) Data Structures Mock Tests\t\t" + "0/0\n\t" +
//                        "6) Java Programming Mock Tests\t\t" + "0/0\n\t" +
//                        "7) Theory of Computation Mock Tests\t\t" + "1/4\n\t" +
//                        "8) GATE Mock Tests\t\t" + "0/0\n\t" +
//                        "9) Algorithms Mock Tests\t\t" + "0/0\n\t" +
//                        "10) Operating Systems Mock Tests\t\t" + "0/0\n\t" +
//                        "11) DBMS Mock Tests\t\t" + "0/0\n\t" +
//                        "12) Computer Networks Mock Tests\t\t" + "0/0\n\t" +
//                        "13) Aptitude Mock Tests\t\t" + "0/0\n\t" +
//                        "14) Other Topics in Computer Science\t\t" + "0/0\n\n" +
//                        "Press any key to return: ", outContent.toString());
//    }
}
