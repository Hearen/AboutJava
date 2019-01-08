package site.lhearen.ajava.base.queue;

import site.lhearen.ajava.base.entity.ReportDetails;
import site.lhearen.ajava.base.util.TheExecutor;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static java.lang.System.out;
import static java.util.stream.Collectors.toList;

public final class TheFeasibleQueue {
    // 2 is robust enough (5 with 500 MB dump analyzing job might take 100 CPU as an average and crash 40);
    public static final int GENERATING_MAX_COUNT = 2;
    public static final ExecutorService GENERATING_EXECUTOR = Executors.newFixedThreadPool(GENERATING_MAX_COUNT);
    /**
     * used to remove the too old jobs - longer than the TIME_LIMIT will be regard as "too old" and
     * should be terminated and removed from the execution queue and data store and inform the outside;
     */
    public static final long TIME_LIMIT = 1 * 1000L;

    /**
     * JOB_QUEUE & JOB_DETAIL_MAP are the data store used to store all the data required to terminate and start
     * jobs submitted from the outside;
     * for now at most 2 jobs can be executed in remote 40;
     */
    private static final Queue<String> JOB_QUEUE = new LinkedList();
    private static final Map<String, ReportDetails> JOB_DETAIL_MAP = new HashMap<>();
    private static final Map<String, Long> JOB_SUBMITTED_TIME_STAMP_MAP = new HashMap<>();
    private static TheExecutor theExecutor;

    private TheFeasibleQueue() {

    }

    public static void setGeneratingExecutor(TheExecutor dumpReportExecutor) {
        TheFeasibleQueue.theExecutor = dumpReportExecutor;
    }

    public static void getData() {
        out.println(String.format("[data store] queue: %s; map: %s", JOB_QUEUE, JOB_SUBMITTED_TIME_STAMP_MAP));
    }

    public static int getRunningJobCount() {
        return JOB_SUBMITTED_TIME_STAMP_MAP.size();
    }

    public static int getQueuedCount() {
        return JOB_QUEUE.size();
    }

    public static String getRandomRunningJob() {
        return JOB_SUBMITTED_TIME_STAMP_MAP.keySet().stream().findAny().orElse("");
    }

    public static synchronized void replaceDeadWithQueued(Consumer<String> terminateTheDead) {
        List<String> jobToRemoveList = getDeadJobNames();
        out.println("Killing: " + jobToRemoveList);
        jobToRemoveList.forEach(terminateTheDead);
        startQueued();
    }

    public static synchronized void replaceFinishedWithQueued(String logViewerJobName) {
        pop(logViewerJobName);
        startQueued();
    }

    /**
     * @param jobName: logViewerJobName
     * @return
     */
    public static int findIndex(String jobName) {
        int i = 0;
        Iterator<String> iter = JOB_QUEUE.iterator();
        while (iter.hasNext()) {
            String ele = iter.next();
            if (ele.equalsIgnoreCase(jobName)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    /**
     * only add bran-new data
     *
     * @param reportDetails
     * @return true when the job can be directly run (recorded in JOB_SUBMITTED_TIME_STAMP_MAP)
     */
    public static synchronized boolean append(ReportDetails reportDetails) {
//        String jobName = reportDetails.getJobName();
//        JOB_QUEUE.add(jobName);
//        JOB_DETAIL_MAP.put(jobName, reportDetails);
//        if (JOB_SUBMITTED_TIME_STAMP_MAP.size() < GENERATING_MAX_COUNT) {
//            JOB_SUBMITTED_TIME_STAMP_MAP.put(jobName, System.currentTimeMillis());
//            return true;
//        }
        return false;
    }

    /**
     * The data store will no change but tasks submitted will be updated based on the queue;
     */
    private static synchronized void startQueued() {
//        int countToStart = Math.min(GENERATING_MAX_COUNT - JOB_SUBMITTED_TIME_STAMP_MAP.size(), JOB_QUEUE.size());
//        for (int i = 0; i < countToStart; ++i) {
//            String logViewerJobName = JOB_QUEUE.peek();
//            ReportDetails reportDetails = JOB_DETAIL_MAP.get(logViewerJobName);
//            JOB_SUBMITTED_TIME_STAMP_MAP.put(logViewerJobName, System.currentTimeMillis());
//            GENERATING_EXECUTOR.submit(() ->
//                    theExecutor.run(reportDetails.getJobName(), reportDetails.getThePath()));
//        }
    }

    /**
     * The assumed-dead jobs will be returned as a list of logViewerJobNames;
     * The jobs will also be removed from the data store (JOB_QUEUE, JOB_DETAIL_MAP and JOB_SUBMITTED_TIME_STAMP_MAP);
     *
     * @return
     */
    private static synchronized List<String> getDeadJobNames() {
        List<String> deadJobs = JOB_SUBMITTED_TIME_STAMP_MAP.entrySet().stream()
                .filter(entry -> isTooOld(entry.getValue()))
                .map(Map.Entry::getKey)
                .collect(toList());
        for (String deadJob : deadJobs) {
            pop(deadJob);
        }
        return deadJobs;
    }

    private static boolean isTooOld(Long timestamp) {
        return Math.abs(System.currentTimeMillis() - timestamp) > TIME_LIMIT;
    }

    /**
     * just remove the data from the data store
     *
     * @param jobName: logViewerJobName
     */
    private static synchronized void pop(String jobName) {
        JOB_QUEUE.remove(jobName);
        JOB_SUBMITTED_TIME_STAMP_MAP.remove(jobName);
        JOB_DETAIL_MAP.remove(jobName);
    }

}
