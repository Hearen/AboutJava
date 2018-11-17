package site.lhearen.ajava.base.queue;

import site.lhearen.ajava.base.entity.ReportDetails;
import site.lhearen.ajava.base.util.TheExecutor;

import java.util.stream.IntStream;

import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static site.lhearen.ajava.base.queue.TheFeasibleQueue.GENERATING_MAX_COUNT;
import static site.lhearen.ajava.base.queue.TheFeasibleQueue.TIME_LIMIT;
import static org.junit.Assert.assertTrue;
import static site.lhearen.ajava.mytools.util.Util.delay;



public class TheFeasibleQueueTest {
    static final Logger LOG = LoggerFactory.getLogger(TheFeasibleQueue.class);
    private static final int TEST_SIZE = 10;
    private static TheExecutor theExecutor;

    @BeforeClass
    public static void setTheExecutor() {
        theExecutor = new TheExecutor();
        TheFeasibleQueue.setGeneratingExecutor(theExecutor);
        IntStream.range(0, TEST_SIZE)
                .boxed()
                .map(i -> ReportDetails.builder().jobName("Job - " + i).thePath("test").build())
                .forEach(reportDetails -> {
                    if (TheFeasibleQueue.append(reportDetails)) {
                        theExecutor.run(reportDetails.getJobName(), reportDetails.getThePath());
                    }
                });
    }

    @Test
    public void testAppend() {
        IntStream.range(TEST_SIZE, TEST_SIZE * 2)
                .boxed()
                .map(i -> ReportDetails.builder().jobName("Job - " + i).thePath("test").build())
                .forEach(reportDetails -> {
                    if (TheFeasibleQueue.append(reportDetails)) {
                        theExecutor.run(reportDetails.getJobName(), reportDetails.getThePath());
                        assertTrue("Running job must be restricted",
                                TheFeasibleQueue.getRunningJobCount() <= GENERATING_MAX_COUNT);
                    }
                    TheFeasibleQueue.getData();
                });
    }

    @Test
    public void testRemove() {
        IntStream.range(0, TEST_SIZE).forEach(i -> {
            LOG.info("Kill some old jobs");
            delay(TIME_LIMIT * 2);
            TheFeasibleQueue.replaceDeadWithQueued((jobName) -> {
            });
            assertTrue("Running job must be restricted",
                    TheFeasibleQueue.getRunningJobCount() <= GENERATING_MAX_COUNT);
            TheFeasibleQueue.getData();
        });
        assertTrue("No running jobs", TheFeasibleQueue.getRunningJobCount() == 0);
        assertTrue("No queued jobs", TheFeasibleQueue.getQueuedCount() == 0);
    }

    @Test
    public void testFinish() {
        IntStream.range(0, TEST_SIZE).forEach(i -> {
            String finishedJob = TheFeasibleQueue.getRandomRunningJob();
            LOG.info("Finish a job: " + finishedJob);
            TheFeasibleQueue.replaceFinishedWithQueued(finishedJob);
            assertTrue("Running jobs must be restricted",
                    TheFeasibleQueue.getRunningJobCount() <= GENERATING_MAX_COUNT);
            TheFeasibleQueue.getData();
        });
        assertTrue("No running jobs", TheFeasibleQueue.getRunningJobCount() == 0);
        assertTrue("No queued jobs", TheFeasibleQueue.getQueuedCount() == 0);
    }
}
