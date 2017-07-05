package t01;

import org.hamcrest.core.Is;
import org.junit.Test;

import java.io.StringWriter;

import static org.hamcrest.core.Is.*;
import static org.junit.Assert.*;

/**
 * Created by Aleksandr Shevkunenko on 02.07.2017.
 */
public class CrazyLoggerTest {
    @Test
    public void pickFullLogTest() throws Exception {
        StringWriter logfile = new StringWriter();
        CrazyLogger logger = new CrazyLogger(logfile);

        for (int i = 1; i <= 20; i++) logger.log("This is message #" + i + ".");

        logger.pickFullLog();
        assertEquals(logger.getStorage().toString(), logfile.toString());

        logger.close();
    }

    @Test
    public void pickLogLinesTest() throws Exception {
        StringWriter logfile = new StringWriter();
        CrazyLogger logger = new CrazyLogger(logfile);

        for (int i = 1; i <= 20; i++) logger.log("This is message #" + i + ".");

        logger.pickLogLines("abcdef");
        assertTrue(logfile.toString().isEmpty());

        logger.pickLogLines("message #5");
        String[] lines = logger.getStorage().toString().split("\\n");
        assertThat(logfile.toString().trim(), is(lines[4].trim()));

        logger.close();
    }
}