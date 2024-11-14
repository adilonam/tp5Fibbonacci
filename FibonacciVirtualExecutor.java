import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FibonacciVirtualExecutor implements Callable<Integer> {
    private final int n;

    public FibonacciVirtualExecutor(int n) {
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

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
        FibonacciVirtualExecutor task = new FibonacciVirtualExecutor(45);
        Future<Integer> future = executor.submit(task);
        try {
            int result = future.get();
            System.out.println("Résultat final : " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
        long end = System.currentTimeMillis();
        System.out.println("Time : " + (end - start) + " ms");
    }
}