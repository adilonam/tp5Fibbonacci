

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FibonacciExecutorService implements Callable<Integer> {
    private final int n;

    public FibonacciExecutorService(int n) {
        this.n = n;
    }

    private int calculElementaire(int n) {
        if (n <= 1) {
            return n;
        } else {
            return calculElementaire(n - 1) + calculElementaire(n - 2);
        }
    }

    @Override
    public Integer call() {
        return calculElementaire(n);
    }

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        FibonacciExecutorService task = new FibonacciExecutorService(45);
        Future<Integer> future = executor.submit(task);
        int result = future.get();
        executor.shutdown();
        long end = System.currentTimeMillis();
        System.out.println("Résultat final : " + result);
        System.out.println("Time : " + (end - start) + " ms");
    }
}