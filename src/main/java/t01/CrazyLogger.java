package t01;

import com.sun.istack.internal.NotNull;
import lombok.NonNull;

import java.io.*;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by Aleksandr Shevkunenko on 01.07.2017.
 */
public class CrazyLogger {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-YYYY : hh-mm");

    private StringBuilder storage = new StringBuilder();
    private PrintWriter destination;
    private Locale loggerLocale;


    public CrazyLogger(@NonNull OutputStream destination, @NonNull Locale loggerLocale) {
        this.destination = new PrintWriter(new BufferedWriter(new OutputStreamWriter(destination)));
        this.loggerLocale = loggerLocale;
    }

    public CrazyLogger(@NonNull Writer destination, @NonNull Locale loggerLocale) {
        this.destination = new PrintWriter(new BufferedWriter(destination));
        this.loggerLocale = loggerLocale;
    }

    public CrazyLogger(OutputStream destination) {
        this(destination, Locale.getDefault());
    }

    public CrazyLogger(Writer destination) {
        this(destination, Locale.getDefault());
    }

    public CrazyLogger(Locale loggerLocale) {
        this(System.out, loggerLocale);
    }

    public CrazyLogger() {
        this(System.out, Locale.getDefault());
    }


    public void setDestination(OutputStream destination) {
        this.destination = new PrintWriter(new BufferedWriter(new OutputStreamWriter(destination)));
    }

    public void setDestination(Writer destination) {
        this.destination = new PrintWriter(new BufferedWriter(destination));
    }

    public Locale getLoggerLocale() {
        return loggerLocale;
    }

    public void setLoggerLocale(Locale loggerLocale) {
        this.loggerLocale = loggerLocale;
    }


    public void reset() {
        storage = new StringBuilder();
    }

    public void log(String message) {
        String timestamp = FORMATTER.format(Instant.now());
        String record = String.format(loggerLocale, "%s - %s%n", timestamp, message);
        storage.append(record);
    }

    public void pickFullLog() {
        destination.print(storage);
    }

    public void pickLogLines(String phrase) {
        try (Scanner reader = new Scanner(storage.toString())) {
            String line;
            while (reader.hasNextLine()) {
                line = reader.nextLine();
                if (line.contains(phrase)) destination.print(line);
            }
        }
    }


}