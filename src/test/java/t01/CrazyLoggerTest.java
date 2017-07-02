package t01;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Aleksandr Shevkunenko on 02.07.2017.
 */
public class CrazyLoggerTest {
    @Test
    public void crazyLoggerTest() throws Exception {
        CrazyLogger logger = new CrazyLogger();

        for (int i = 1; i <= 20; i++) logger.log("This is message #" + i + ".");

        logger.pickFullLog();
    }
}