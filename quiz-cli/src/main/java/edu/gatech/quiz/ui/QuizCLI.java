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

    public static void main(String[] args) {
       // Provide menu driven quiz access via CLI
    }

    public QuizCLI(QuizDB db) {
        this.db = db;
    }

    public void run() {
        // Add code here
    }

    public void printUserAnswers() {
        System.out.println("these are the user answers:");
    }
}
