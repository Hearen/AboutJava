package site.lhearen.ajava.base.thread;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;
import java.util.stream.IntStream;

import static java.lang.System.out;
import static java.util.stream.Collectors.toList;

public class TestReentrantLock {
    static final String IP_PREFIX = "10.20.30.";
    static final int IP_COUNT = 1;
    static final int TASK_COUNT = 20;
    static final Map<String, List<String>> input = initInput();
    public static Map<String, List<String>> output = new ConcurrentHashMap<>();

    public static void main(String... args) throws InterruptedException {
        out.println(input);
//        output = input.entrySet().stream()
//                .collect(Collectors.toMap(e -> e.getKey(), e -> new ArrayList<>(e.getValue())));
//        out.println(isOutputValid());
//        ExecutorService executorService = Executors.newCachedThreadPool((r) -> {
//            Thread thread = new Thread();
//            return thread;
//        });
        ExecutorService executorService = Executors.newCachedThreadPool();

        input.entrySet().stream()
                .forEach(entry -> {
                    String groupName = entry.getKey();
                    entry.getValue().stream().forEach(taskId -> {
                        executorService.submit(() -> {
                            MyTaskExecutor taskExecutor = new MyTaskExecutor();
                            taskExecutor.execute(groupName, taskId, MyTaskQueue::enque, MyTaskQueue::deque);
                        });
                    });
                });
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        out.println(isOutputValid());
    }

    private static Map<String, List<String>> initInput() {
        List<String> ips = new ArrayList<>();
        Map<String, List<String>> input = new HashMap<>();
        IntStream.range(0, IP_COUNT).forEach(i -> ips.add(String.format("%s%d", IP_PREFIX, i)));
        ips.stream().forEach(ip -> {
            IntStream.range(0, TASK_COUNT).forEach(t ->
                    input.computeIfAbsent(ip, (unused) -> new ArrayList<>()).add(String.format("%s-task-%d", ip, t)));
        });
        return input;
    }


    public static boolean isOutputValid() {
        return output.equals(input);
    }
}

class MyTaskExecutor {
    public void execute(String name, String taskId,
                        BiConsumer<String, String> preHook,
                        BiConsumer<String, String> postHook) {
//        out.println(Thread.currentThread().getName());
        preHook.accept(name, taskId);
        progressing(taskId, 10L, 500L);
        postHook.accept(name, taskId);
    }

    public void progressing(String taskName, Long min, Long max) {
        int blockCount = 1;
        Long randomTotal = ThreadLocalRandom.current().nextLong(min, max);
        Long blockTime = randomTotal / blockCount;
        try {
            for (int i = 0; i < blockCount; ++i) {
                out.println("processing " + taskName + " ...");
                Thread.sleep(blockTime);
            }
        } catch (InterruptedException ignored) {
            ignored.printStackTrace();
            out.println("Task Processing Interrupted!");
        }
    }
}

class MyTaskQueue {
    private static final int CONCURRENT_TASK_MAX = 1;
    private static final Object THE_QUEUE_LOCK = new Object();
    public static Map<String, Vector<String>> taskGroup = new HashMap<>();
    public static Map<String, ReentrantLock> taskGroupLock = new HashMap<>();

    public static Map<String, Condition> taskGroupCondition = new HashMap<>();

    public static void enque(String name, String taskId) {
        // ensure the sequence of the task queue;
        // this is not going to work since the sequence is determined by the sequence of the thread invoking order;
        // have to use another indicator (timestamp for example) to ensure the order (PriorityQueue can be used);
        synchronized (THE_QUEUE_LOCK) {
            taskGroup.computeIfAbsent(name, (unused) -> new Vector<>()).add(taskId);
            taskGroupLock.putIfAbsent(name, new ReentrantLock());
            taskGroupCondition.computeIfAbsent(name, (unused) -> taskGroupLock.get(name).newCondition());
        }
        Object groupLock = taskGroupLock.get(name);
        // ensure only one task in a group will check the available slot to run;
        synchronized (groupLock) {
            while (true) { // blocked threads need to check the updated condition again;
                List<String> taskList = taskGroup.get(name);
                List<String> runnableTasks =  getTopK(taskList, Integer.min(taskList.size(), CONCURRENT_TASK_MAX));
                if (runnableTasks.contains(taskId)) {
                    break; // runnable;
                } else {
                    try {
                        taskGroupCondition.get(name).wait(); // blocked if it's not allowed;
                    } catch (InterruptedException ignored) {
                        ignored.printStackTrace();
                    }
                }
            }
        }
    }

    public static void deque(String name, String taskId) {
        if (taskGroup.containsKey(name) && taskGroup.get(name).contains(taskId)) {
            synchronized (THE_QUEUE_LOCK) {
                taskGroup.get(name).remove(taskId);
                TestReentrantLock.output.computeIfAbsent(name, (unused) -> new ArrayList<>()).add(taskId);
                if (taskGroup.get(name).isEmpty()) {
                    taskGroup.remove(name);
                }
                synchronized (taskGroupLock.get(name)) {
                    taskGroupCondition.get(name).notifyAll();
                }
            }
        }
    }

    private static List<String> getTopK(List<String> taskIdList, int k) {
        List<String> sortedList = taskIdList.stream()
                .sorted(Comparator.comparing(id -> getNumFromTaskId(id))).collect(toList());
        return new ArrayList<>(sortedList.subList(0, k));
    }

    private static Integer getNumFromTaskId(String taskId) {
        return Integer.valueOf(taskId.substring(taskId.lastIndexOf("-")+1));
    }

}
