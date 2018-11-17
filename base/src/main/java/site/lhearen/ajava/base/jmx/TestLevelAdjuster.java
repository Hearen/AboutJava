package site.lhearen.ajava.base.jmx;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TestLevelAdjuster {
    private static Logger LOGGER = Logger.getLogger(TestLevelAdjuster.class.getCanonicalName());

    public static void main(final String[] arguments)
    {
        for (int i=0; i < 500; i++)
        {
            try
            {
                LOGGER.info("Sleep: " + i);
                LOGGER.warning("This is a warning: " + i);
                Thread.sleep(2500);
                LOGGER.setLevel(Level.WARNING);
            }
            catch (InterruptedException threadException)
            {
                LOGGER.severe("InterruptedException: " + threadException.toString());
            }
        }
    }
}
