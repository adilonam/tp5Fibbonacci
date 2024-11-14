
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FibonacciForkJoin extends RecursiveTask<Integer> {
    private final int nombre;
    protected static int SEUIL = 10;
    private int resultat;

    public FibonacciForkJoin(int n) {
        this.nombre = n;
    }

    @Override
    protected Integer compute() {
        if (nombre <= SEUIL) {
            resultat = calculElementaire(nombre);

        }

        else {
            FibonacciForkJoin f1 = new FibonacciForkJoin(nombre - 1);

            FibonacciForkJoin f2 = new FibonacciForkJoin(nombre - 2);
            invokeAll(f1, f2);
            resultat = f1.resultat() + f2.resultat();

        }
        return resultat;
    }

    private int calculElementaire(int n) {
        if (n <= 1) {
            return n;
        }
        return calculElementaire(n - 1) + calculElementaire(n - 2);
    }

    public int resultat() {
        return resultat;
    }

    public static void main(String[] args) {
        long depart = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        FibonacciForkJoin task = new FibonacciForkJoin(45);
        int resultatFinal = pool.invoke(task);
        System.out.println("Résultat final : " + resultatFinal);

        long fin = System.currentTimeMillis();
        System.out.println("Time : " + (fin - depart) + " ms");
    }
}