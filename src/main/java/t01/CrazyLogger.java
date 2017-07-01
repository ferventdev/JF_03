package t01;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Created by Aleksandr Shevkunenko on 01.07.2017.
 */
public class CrazyLogger {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-YYYY : hh-mm");

    private StringBuilder storage = new StringBuilder();

    private Locale loggerLocale;


    public CrazyLogger(Locale loggerLocale) {
        this.loggerLocale = loggerLocale;
    }

    public CrazyLogger() {
        this.loggerLocale = Locale.getDefault();
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
}
