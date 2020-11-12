package com.company;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class Main {

    public static long fibRecur(long x)                             //fibonacci function that uses recursion.
    {
        if (x <= 1)                                                 //Checks if x is either 0 or 1
        {
            return x;
        }
        else{
            return fibRecur(x - 1) + fibRecur(x - 2);         //recursive call until x = 0,1.
        }
    }
    public static long fibCache(long x)                             //fibonacci function that utilizes a cache
    {
        long[] cache = new long[(int)x+1];                          // creates the cache array
        return fibCacheHelper(x, cache);                            //use of a cache function to find numbers
    }
    public static long fibCacheHelper(long x, long[] cache)         //fibonacci cache helper function
    {
        if(x <= 1)                                                  // checks if x is 0 or 1
        {
            return x;
        }
        else if (cache[(int) x] != 0)                               //if the index at x doesn't equal 0 returns that index
        {
            return cache[(int) x];
        } else{                                                     // otherwise it recursively calls to reach 0 or 1
            cache[(int) x] = fibCacheHelper(x -1 , cache) + fibCacheHelper(x - 2 , cache);
            return cache[(int) x];
        }
    }
    public static long fibLoop(long x)                              // looping fibonacci function
    {
        long a = 0;
        long b = 1;
        long temp;
        if( x <= 1 )                                                //checks if x is 0 or 1
        {
            return x;
        }else{
            for (int i = 2; i <= x; i++)                            //loops through until x is reached.
            {
                temp = a + b;                                       // sets the values for the next loop
                a = b;
                b = temp;
            }
        }
        return b;
    }
    public static long fibMatrix(long x) {
        long exponential = x - 1;
        long[][] expMatrix = new long[][]{{1, 1}, {1, 0}};
        long[][] resultMatrix = new long[][]{{1, 0}, {0, 1}};
        if (x == 0 || x == 1) return x;

        while (exponential > 0) {
            if (exponential % 2 == 1) {
                resultMatrix = new long[][]{
                        {expMatrix[0][0] * resultMatrix[0][0] + expMatrix[0][1] * resultMatrix[1][0],
                                expMatrix[0][0] * resultMatrix[0][1] + expMatrix[0][1] * resultMatrix[1][1]},
                        {expMatrix[1][0] * resultMatrix[0][0] + expMatrix[1][1] * resultMatrix[1][0],
                                expMatrix[1][0] * resultMatrix[0][1] + expMatrix[1][1] * resultMatrix[1][1]}
                };
            }
            expMatrix = new long[][]{
                    {expMatrix[0][0] * expMatrix[0][0] + expMatrix[0][1] * expMatrix[1][0],
                            expMatrix[0][0] * expMatrix[0][1] + expMatrix[0][1] * expMatrix[1][1]},
                    {expMatrix[1][0] * expMatrix[0][0] + expMatrix[1][1] * expMatrix[1][0],
                            expMatrix[1][0] * expMatrix[0][1] + expMatrix[1][1] * expMatrix[1][1]}
            };
            exponential /= 2;
        }
        return resultMatrix[0][0];
    }
    public static void RunTimeTests() {
        int N;
        int x;
        int y;
        long resultTime = 0;
        long startTime = 0;
        long endTime = 0;
        long maxRunTime = 240000000L;
        long endFibRecurRunTime = 0;
        int maxFibRecurLoops = 100;
        long[] fibRecurTime = new long[93];
        long[] fibCacheTime = new long[93];
        long[] fibLoopTime = new long[93];
        long[] fibMatrixTime = new long[93];
        int numberLoops = 1000;

        System.out.printf("FibRecur\n");
        System.out.printf("| %10s| %10s| %22s| %15s| %10s| %10s|\n", "N", "X", "Fib(x)", "Time", "DRation", "Exp DRatio");
        for (N = 1; N < 10; N++) {
            for (x = (int) Math.pow(2, (double) N - 1); x < Math.pow(2, N) && x < 93; x++) {

                if (endFibRecurRunTime < maxRunTime) {
                    if (x > 0 && x % 10 == 0) {
                        maxFibRecurLoops -= 20;
                    }
                    for (y = 0; y < maxFibRecurLoops; y++) {
                        startTime = getCpuTime();
                        resultTime = fibRecur(x);
                        endTime = getCpuTime();
                        fibRecurTime[x] += endTime - startTime;
                    }
                    fibRecurTime[x] = fibRecurTime[x] / maxFibRecurLoops;
                    endFibRecurRunTime = fibRecurTime[x];
                    System.out.printf("| %10s| %10s| %22s| %15s|", N, x, resultTime, fibRecurTime[x]);
                    if (x != 0 && x % 2 == 0) {
                        float doubling = (float) fibRecurTime[x] / fibRecurTime[x / 2];
                        double expectedDoubling = Math.pow(2, N / 2) / Math.pow(2, N / 2 / 2);
                        System.out.printf(" %10.2f| %10.2f|\n", doubling, expectedDoubling);
                    } else {
                        System.out.printf(" %10s| %10s|\n", "NA", "NA");
                    }
                }
            }
        }
        System.out.printf("FibCache\n");
        System.out.printf("| %10s| %10s| %22s| %15s| %10s| %10s|\n", "N", "X", "Fib(x)", "Time", "DRation", "Exp DRatio");
        for (N = 1; N < 10; N++) {
            for (x = (int) Math.pow(2, (double) N - 1); x < Math.pow(2, N) && x < 93; x++) {
                for (y = 0; y < numberLoops; y++) {
                    startTime = getCpuTime();
                    resultTime = fibCache(x);
                    endTime = getCpuTime();
                    fibCacheTime[x] += endTime - startTime;
                }
                fibCacheTime[x] = fibCacheTime[x] / numberLoops;
                System.out.printf("| %10s| %10s| %22s| %15s|", N, x, resultTime, fibCacheTime[x]);
                if (x != 0 && x % 2 == 0) {
                    float doubling = (float) fibCacheTime[x] / fibCacheTime[x / 2];
                    double expectedDoubling = 2;
                    System.out.printf(" %10.2f| %10.2f|\n", doubling, expectedDoubling);
                } else {
                    System.out.printf(" %10s| %10s|\n", "NA", "NA");
                }
            }
        }
        System.out.printf("FibLoop\n");
        System.out.printf("| %10s| %10s| %22s| %15s| %10s| %10s|\n", "N", "X", "Fib(x)", "Time", "DRation", "Exp DRatio");
        for (N = 1; N < 10; N++) {
            for (x = (int) Math.pow(2, (double) N - 1); x < Math.pow(2, N) && x < 93; x++) {
                for (y = 0; y < numberLoops; y++) {
                    startTime = getCpuTime();
                    resultTime = fibLoop(x);
                    endTime = getCpuTime();
                    fibLoopTime[x] += endTime - startTime;
                }
                fibLoopTime[x] = fibLoopTime[x] / numberLoops;
                System.out.printf("| %10s| %10s| %22s| %15s|", N, x, resultTime, fibLoopTime[x]);
                if (x != 0 && x % 2 == 0) {
                    float doubling = (float) fibLoopTime[x] / fibLoopTime[x / 2];
                    double expectedDoubling = 2;
                    System.out.printf(" %10.2f| %10.2f|\n", doubling, expectedDoubling);
                } else {
                    System.out.printf(" %10s| %10s|\n", "NA", "NA");
                }
            }
        }
        System.out.printf("FibMatrix\n");
        System.out.printf("| %10s| %10s| %22s| %15s| %10s| %10s|\n", "N", "X", "Fib(x)", "Time", "DRation", "Exp DRatio");
        for (N = 1; N < 10; N++) {
            for (x = (int) Math.pow(2, (double) N - 1); x < Math.pow(2, N) && x < 93; x++) {
                for (y = 0; y < numberLoops; y++) {
                    startTime = getCpuTime();
                    resultTime = fibMatrix(x);
                    endTime = getCpuTime();
                    fibMatrixTime[x] += endTime - startTime;
                }
                fibMatrixTime[x] = fibMatrixTime[x] / numberLoops;
                System.out.printf("| %10s| %10s| %22s| %15s|", N, x, resultTime, fibMatrixTime[x]);
                if (x != 0 && x % 2 == 0) {
                    float doubling = (float) fibMatrixTime[x] / fibMatrixTime[x / 2];
                    double expectedDoubling = (Math.log(N)/Math.log(2)) / (Math.log(N/2)/Math.log(2));
                    System.out.printf(" %10.2f| %10.2f|\n", doubling, expectedDoubling);
                } else {
                    System.out.printf(" %10s| %10s|\n", "NA", "NA");
                }
            }
        }
    }
    /** Get CPU time in nanoseconds since the program(thread) started. */
    /** from: http://nadeausoftware.com/articles/2008/03/java_tip_how_get_cpu_and_user_time_benchmarking#TimingasinglethreadedtaskusingCPUsystemandusertime **/
    public static long getCpuTime( )
    {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
        return bean.isCurrentThreadCpuTimeSupported( ) ?
                bean.getCurrentThreadCpuTime( ) : 0L;

    }
    public static void main(String[] args)
    {
        RunTimeTests();
    }
}
