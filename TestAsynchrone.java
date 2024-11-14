
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

public class TestAsynchrone {
    public static Integer random() {
        return new Random().nextInt(10);
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long begin = System.currentTimeMillis();

        CompletableFuture<Integer>[] futures = IntStream.range(0, 1000)
            .mapToObj(i -> CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(5); // (wait for 5 ms)
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return random();
            }))
            .toArray(CompletableFuture[]::new);

        int sum = CompletableFuture.allOf(futures)
            .thenApply(v -> IntStream.range(0, futures.length)
                .map(i -> futures[i].join())
                .sum())
            .get();

        System.out.println(sum);

        long end = System.currentTimeMillis();
        long time = end - begin;
        System.out.println("Elapsed Time: " + time + " Milliseconds");
    }
}