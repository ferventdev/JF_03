package t02;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;
import java.util.Scanner;

/**
 * Created by Aleksandr Shevkunenko on 03.07.2017.
 */
public class QuestionsAndAnswers {
    final Scanner reader = new Scanner(System.in);

    private Locale language;
    private ResourceBundle questions;
    private ResourceBundle answers;


    public void setLanguage(Locale language) {
        this.language = language;
        questions = ResourceBundle.getBundle("questions", this.language);
        answers = ResourceBundle.getBundle("answers", this.language);
    }

    public Locale getLanguage() {
        return language;
    }

    int getEnteredNumber(int range) {
        for (int number = -1;;) {
            try {
                number = Integer.parseInt(reader.nextLine());
                if (0 <= number && number <= range) return number;
                throw new RuntimeException();
            } catch (RuntimeException e) {
                if (language == null) {
                    System.out.println("You should enter a positive integer from 0 to " + range + ".");
                    System.out.println("Вам следует ввести целое положительное число от 0 до " + range + ".");
                } else System.out.printf(language, "%s%d.%n", questions.getString("qq"), range);
            }
        }
    }

    String getQuestionNumber(String key) {
        StringBuilder qn = new StringBuilder();
        for (int i = 0, n = key.length(); i < n; i++) {
            char symbol = key.charAt(i);
            if (Character.isDigit(symbol)) qn.append(symbol);
        }
        return qn.toString();
    }

    String getQuestions() {
        StringBuilder text = new StringBuilder();
        text.append(questions.getString("qi") + "\n");  // heading
        for (Enumeration<String> keys = questions.getKeys(); keys.hasMoreElements();) {
            String key = keys.nextElement();
            if (key.matches("\\D+\\d+")) {
                text.append(getQuestionNumber(key) + " - ");
                text.append(questions.getString(key) + "\n");
            }
        }
        text.append("\n" + questions.getString("qw") + "\n");  // tip - what you can do (enter)
        text.append(questions.getString("qe") + "\n");         // how to exit
        return text.toString();
    }

    String getAnswer(int number) {
        return answers.getString("a" + number);
    }

    public static void main(String[] args) {
        String intro = "Please, choose your language (and press Enter):\n"
                + "Пожалуйста, выберите свой язык (и нажмите Enter):\n"
                + "1 - English\n"
                + "2 - Русский\n"
                + "For exit enter 0.\n"
                + "Для выхода введите 0.";

        System.out.println(intro);

        QuestionsAndAnswers qa = new QuestionsAndAnswers();
        int reply = qa.getEnteredNumber(2);
        if (reply == 0) {
            qa.reader.close();
            return;
        }
        qa.setLanguage((reply == 1) ? new Locale("en", "US") : new Locale("ru", "RU"));

        for (String allQuestions = qa.getQuestions();;) {
            System.out.print(allQuestions);
            reply = qa.getEnteredNumber(7);
            if (reply == 0) {
                qa.reader.close();
                return;
            }
            System.out.println(qa.getAnswer(reply));
            System.out.println();
        }
    }
}