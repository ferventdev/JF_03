package t01;

import java.util.Locale;

/**
 * Created by Aleksandr Shevkunenko on 01.07.2017.
 */
public class CrazyLogger {
    private final StringBuilder storage = new StringBuilder();
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

    public void log(String message) {

    }
}
