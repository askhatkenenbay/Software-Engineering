import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

import org.apache.commons.math3.primes.Primes;

public class Solution {
    public static void main(String[] args) {
        long startSingle = System.currentTimeMillis();
        singleThreadSolution();
        long endSingle = System.currentTimeMillis();
        long startConcurrent = System.currentTimeMillis();
        concurrentSolution();
        long endConcurrent = System.currentTimeMillis();
        System.out.println("Single thread Solution in " + (endSingle - startSingle) + " milliseconds");
        System.out.println("Concurrent Solution in " + (endConcurrent - startConcurrent) + " milliseconds");
    }

    public static List<Boolean> concurrentSolution() {
        CountDownLatch latch = new CountDownLatch(4);
        ExecutorService service = Executors.newFixedThreadPool(4);
        LinkedList<Future<Boolean[]>> futureList = new LinkedList<>();
        LinkedList<Boolean> result = new LinkedList<>();
        int interval = 2_500_000;
        try {
            for (int i = 0; i < 4; i++) {
                Future<Boolean[]> temp = service.submit(new PrimeTask(interval * i + 1, (i + 1) * interval, latch));
                futureList.add(temp);
            }
            latch.await();
            for (int i = 0; i < 4; i++) {
                Collections.addAll(result, futureList.get(i).get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        service.shutdown();
        return result;
    }

    public static List<Boolean> singleThreadSolution() {
        return Arrays.asList(new PrimeTask(1, 10_000_000).call());
    }
}

class PrimeTask implements Callable<Boolean[]> {
    private int startIndex;
    private int endIndex;
    private CountDownLatch latch;

    public PrimeTask(int startIndex, int endIndex, CountDownLatch latch) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.latch = latch;
    }

    public PrimeTask(int startIndex, int endIndex) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public Boolean[] call() {
        Boolean[] result = new Boolean[endIndex - startIndex + 1];
        for (int i = startIndex; i <= endIndex; i++) {
            result[i - startIndex] = Primes.isPrime(i);
        }
        if (latch != null) {
            latch.countDown();
        }
        return result;
    }
}