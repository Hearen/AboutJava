package site.lhearen.ajava.base.future;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.out;
import static site.lhearen.ajava.mytools.util.Util.delay;
import static site.lhearen.ajava.mytools.util.Util.formatDouble;

public class BestPriceFinder {
    private static final String theProduct = "myPhone27S";
    private static BestPriceFinder bestPriceFinder = new BestPriceFinder();
    private final List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
//            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll"),
            new Shop("ShopEasy"));
    private final Executor executor = Executors.newFixedThreadPool(shops.size(), (Runnable r) -> {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    });

    public static void main(String[] args) {
//        execute("sequential", () -> bestPriceFinder.findPricesSequential(theProduct));
//        execute("parallel", () -> bestPriceFinder.findPricesParallel(theProduct));
        execute("composed completableFuture", () -> bestPriceFinder.findPricesFuture(theProduct));
//        bestPriceFinder.printPricesStream("myPhone27S");
        bestPriceFinder.testGettingPrices();
    }

    private static void execute(String msg, Supplier<List<String>> s) {
        long start = System.nanoTime();
        out.println(s.get());
        long duration = (System.nanoTime() - start) / 1_000_000;
        out.println(msg + " done in " + duration + " msecs");
    }

    public List<String> findPricesSequential(String product) {
        return shops.stream()
                .map(shop -> shop.getPrice(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(Collectors.toList());
    }

    public List<String> findPricesParallel(String product) {
        return shops.parallelStream()
                .map(shop -> shop.getPrice(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(Collectors.toList());
    }

    public List<String> findPricesFuture(String product) {
        List<CompletableFuture<String>> priceFutures = findPricesStream(product)
                .collect(Collectors.toList());

        // handle completableFuture<Void> using allOf()
//        completableFuture[] priceFutures0 = findPricesStream(product).toArray(size -> new completableFuture[size]);
//        completableFuture.allOf(priceFutures0).join();
        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public Stream<CompletableFuture<String>> findPricesStream(String product) {
        return shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(()
                        -> Discount.applyDiscount(quote), executor)));
    }

    public void printPricesStream(String product) {
        long start = System.nanoTime();
        CompletableFuture[] futures = findPricesStream(product)
                .map(f -> f.thenAccept(s -> out.println(s + " (done in " + ((System.nanoTime() - start) / 1_000_000) + " msecs)")))
                .toArray(size -> new CompletableFuture[size]);
        CompletableFuture.allOf(futures).join();
        out.println("All shops have now responded in " + ((System.nanoTime() - start) / 1_000_000) + " msecs");
    }

    public void testGettingPrices() {
        getAllPricesInParrallel();
        getAllPricesInCompletableFuture();
    }

    private void getAllPricesInParrallel() {
        out.println("Using parallelStream: ");
        long start = System.nanoTime();
        shops.parallelStream().map(shop -> shop.calculatePrice(theProduct)).forEach(out::println);
        out.println("All calculation done in " + ((System.nanoTime() - start) / 1_000_000) + " msecs");
        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        out.println(String.format("Active thread count: %d, pool size: %d", commonPool.getActiveThreadCount(),
                commonPool.getPoolSize()));
    }

    private void getAllPricesInCompletableFuture() {
        out.println("Using completableFuture: ");
        long start = System.nanoTime();
        List<CompletableFuture<Double>> futures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.calculatePrice(theProduct), executor))
                .collect(Collectors.toList());
        futures.stream().map(CompletableFuture::join).forEach(out::println);
        out.println("All calculation done in " + ((System.nanoTime() - start) / 1_000_000) + " msecs");
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) executor;
        out.println(String.format("Active thread count: %d, pool size: %d", executorService.getActiveCount(),
                executorService.getLargestPoolSize()));
    }
}

class Quote {

    private final String shopName;
    private final double price;
    private final Discount.Code discountCode;

    public Quote(String shopName, double price, Discount.Code discountCode) {
        this.shopName = shopName;
        this.price = price;
        this.discountCode = discountCode;
    }

    public static Quote parse(String s) {
        String[] split = s.split(":");
        String shopName = split[0];
        double price = Double.parseDouble(split[1]);
        Discount.Code discountCode = Discount.Code.valueOf(split[2]);
        return new Quote(shopName, price, discountCode);
    }

    public String getShopName() {
        return shopName;
    }

    public double getPrice() {
        return price;
    }

    public Discount.Code getDiscountCode() {
        return discountCode;
    }
}

class Shop {

    private final String name;
    private final Random random;

    public Shop(String name) {
        this.name = name;
        random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
    }

    public String getPrice(String product) {
        out.println(String.format("get price for: %s in thread: %s", product, Thread.currentThread().getName()));
        double price = calculatePrice(product);
        Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
        return name + ":" + price + ":" + code;
    }

    public double calculatePrice(String product) {
        out.println(String.format("calculate price for: %s in thread: %s", product, Thread.currentThread().getName()));
        delay();
        return formatDouble(ThreadLocalRandom.current().nextDouble(0, 5000));
    }

    public String getName() {
        return name;
    }
}

class Discount {

    public static String applyDiscount(Quote quote) {
        return quote.getShopName() + " price is " + Discount.apply(quote.getPrice(), quote.getDiscountCode());
    }

    private static double apply(double price, Code code) {
        delay();
        return formatDouble(price * (100 - code.percentage) / 100);
    }

    public enum Code {
        NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);

        private final int percentage;

        Code(int percentage) {
            this.percentage = percentage;
        }
    }
}
