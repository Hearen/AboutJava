package site.lhearen.ajava.base.future;

import java.net.PasswordAuthentication;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.lang.System.out;

public class Sol_11 {
    public static void main(String... args) {
        CompletableFuture<PasswordAuthentication> validAccount = repeat(Sol_11::readAccount, Sol_11::isValidAccount);
        out.println("Valid Account: " + validAccount.join().getUserName());
    }

    public static <T> CompletableFuture<T> repeat(Supplier<T> supplier, Predicate<T> isValid) {
        T ret = supplier.get();
        if (!isValid.test(ret)) {
            repeat(supplier, isValid);
        }
        return CompletableFuture.supplyAsync(() -> ret);
    }

    private static boolean isValidAccount(PasswordAuthentication authentication) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
            ignored.printStackTrace();
        }
        return authentication.getUserName().equalsIgnoreCase("Hearen")
                && Arrays.equals(authentication.getPassword(), "Katherine".toCharArray());
    }

    private static PasswordAuthentication readAccount() {
        out.println(Thread.currentThread().getName());
        Scanner inputReader = new Scanner(System.in);
        out.println("please enter name:");
        String name = inputReader.nextLine();
        out.println("password:");
        String password = inputReader.nextLine();
        return new PasswordAuthentication(name, password.toCharArray());
    }
}
