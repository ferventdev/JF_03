package t02;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * Created by Aleksandr Shevkunenko on 03.07.2017.
 */
public class QuestionsAndAnswers {
    private final Scanner reader = new Scanner(System.in);
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
        for (int number = 0;;) {
            try {
                number = Integer.parseInt(reader.nextLine());
                if (0 <= number && number <= range) return number;
                throw new RuntimeException();
            } catch (RuntimeException e) {
                if (language == null) {
                    System.out.println("You should enter a positive integer from 0 to " + range + ".");
                    System.out.println("Вам следует ввести целое положительное число от 0 до " + range + ".");
                } else System.out.printf(language, "%s%d.", questions.getString("qq"), range);
            }
        }
    }

    String getQuestions() {
        StringBuilder text = new StringBuilder();
        for (Enumeration<String> keys = questions.getKeys(); keys.hasMoreElements();) {
            text.append(questions.getString(keys.nextElement()));
        }
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
        if (reply == 0) return;
        qa.setLanguage((reply == 1) ? new Locale("en", "US") : new Locale("ru", "RU"));

        for (String allQuestions = qa.getQuestions();;) {
            System.out.println(allQuestions);
            reply = qa.getEnteredNumber(7);
            if (reply == 0) return;
            System.out.println(qa.getAnswer(reply));
        }
    }
}