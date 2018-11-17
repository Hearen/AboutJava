package site.lhearen.ajava.base.util;

import static java.lang.System.out;
//import static Util.delay;

public class TheExecutor {
    public boolean run(String jobName, String thePath) {
//        delay(TIME_LIMIT);
        out.println(String.format("Running: jobName: %s; thePath: %s", jobName, thePath));
        return true;
    }
}
