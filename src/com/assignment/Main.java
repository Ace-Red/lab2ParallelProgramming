package com.assignment;

import com.assignment.simpleMultiplication.SimpleMultiplication;
import com.assignment.multiplicationAlgorithms.FoxMultiplication;
import com.assignment.multiplicationAlgorithms.ParallelMultiplication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main
{
  public static void main(String[] args)
  {
    int row = 1500;
    int col = 1500;
    int nExperiments = 4;

    //runAlgorithm(row,col);
    //threadExperiment(row,col,nExperiments);
    sizeExperiment(nExperiments);
  }

  public static void runAlgorithm(int row, int col)
  {
    Matrix A = new Matrix(row, col);
    Matrix B = new Matrix(row, col);


    A.generateRandomMatrix();
    B.generateRandomMatrix();


    int nThread = Runtime.getRuntime().availableProcessors();

    SimpleMultiplication simpleMultiplication = new SimpleMultiplication(A, B);
    ParallelMultiplication parallelMultiplication = new ParallelMultiplication(A, B, nThread);
    FoxMultiplication foxMultiplication = new FoxMultiplication(A, B, nThread);

    long currTime = System.nanoTime();
    Matrix C = simpleMultiplication.multiply();
    currTime = System.nanoTime() - currTime;

    System.out.println("Basic Algorithm: " + currTime / 1_000_000 + "ms");

    currTime = System.nanoTime();
    C = parallelMultiplication.multiply();
    currTime = System.nanoTime() - currTime;

    System.out.println("Striped Algorithm: " + currTime / 1_000_000 + "ms");

    currTime = System.nanoTime();
    C = foxMultiplication.multiply();
    currTime = System.nanoTime() - currTime;

    System.out.println("Fox Algorithm: " + currTime / 1_000_000 + "ms");
    System.out.println("\n");
  }

  public static void threadExperiment(int row, int col, int nExperiments)
  {
    int[] threadsNStriped = new int[] {5, 10,20, 30, 50, 70};
    int[] threadsNFox = new int[] {5, 10, 20, 30, 50, 70};
    Map<Integer, Long> timeResultParallel = new HashMap<>();
    Map<Integer, Long> timeResultFox = new HashMap<>();

    Matrix A = new Matrix(row, col);
    Matrix B = new Matrix(row, col);

    A.generateRandomMatrix();
    B.generateRandomMatrix();

    for (int nThread : threadsNStriped)
    {
      ParallelMultiplication parallelMultiplication = new ParallelMultiplication(A, B, nThread);

      long acc = 0;
      for (int i = 0; i < nExperiments; i++)
      {
        long currTime = System.nanoTime();
        Matrix C = parallelMultiplication.multiply();
        acc += System.nanoTime() - currTime;
      }
      acc /= nExperiments;

      timeResultParallel.put(nThread, acc / 1_000_000);
    }

    for (int nThread : threadsNFox)
    {
      FoxMultiplication foxMultiplication = new FoxMultiplication(A, B, nThread);

      long avg = 0;
      for (int i = 0; i < nExperiments; i++)
      {
        long currTime = System.nanoTime();
        Matrix C = foxMultiplication.multiply();
        avg += System.nanoTime() - currTime;
      }
      avg /= nExperiments;

      timeResultFox.put(nThread, avg / 1_000_000);
    }

    List<Integer> keysStriped =
        timeResultParallel.keySet().stream().sorted().collect(Collectors.toList());

    System.out.println("Parallel Algorithm:");
    outputThreadsResult(keysStriped,timeResultParallel);

    List<Integer> keysFox = timeResultFox.keySet().stream().sorted().collect(Collectors.toList());

    System.out.println("Fox Algorithm:");
    outputThreadsResult(keysFox,timeResultFox);

    System.out.println("\n");
  }

  public static void sizeExperiment(int nExperiments)
  {
    int nThread = Runtime.getRuntime().availableProcessors();
    //int[] sizesArray = new int[] {10,20,30,100,200};
    int[] sizesArray = new int[] {10, 100, 500, 1000, 1500,2000};
    Map<Integer, Long> timeResultStriped = new HashMap<>();
    Map<Integer, Long> timeResultFox = new HashMap<>();

    for (int size : sizesArray)
    {
      Matrix A = new Matrix(size, size);
      Matrix B = new Matrix(size, size);

      A.generateRandomMatrix();
      B.generateRandomMatrix();

      ParallelMultiplication sa = new ParallelMultiplication(A, B, nThread);

      long avg = 0;
      for (int i = 0; i < nExperiments; i++)
      {
        long currTime = System.nanoTime();
        Matrix C = sa.multiply();
        avg += System.nanoTime() - currTime;
      }
      avg /= nExperiments;

      timeResultStriped.put(size, avg / 1_000_000);

      FoxMultiplication fa = new FoxMultiplication(A, B, nThread);

      avg = 0;
      for (int i = 0; i < nExperiments; i++)
      {
        long currTime = System.nanoTime();
        Matrix C = fa.multiply();
        avg += System.nanoTime() - currTime;
      }
      avg /= nExperiments;

      timeResultFox.put(size, avg / 1_000_000);
    }

    List<Integer> keys = timeResultStriped.keySet().stream().sorted().collect(Collectors.toList());

    outputMatrixResult(keys,timeResultStriped,timeResultFox);
  }

  public static void outputThreadsResult(List<Integer> keysStriped, Map<Integer, Long> timeResult )
  {
    for (int key : keysStriped)
    {
      System.out.println("Threads quantity: " + key + ", time: " + timeResult.get(key) + " ms");
    }
    System.out.println("____________________________________________________\n");
  }

  public static void outputMatrixResult(List<Integer> keys, Map<Integer, Long> timeResultStriped,Map<Integer, Long> timeResultFox)
  {
    for (int key : keys)
    {
      System.out.println("Size: " + key);
      System.out.println("Parallel / Fox: " + timeResultStriped.get(key) + " ms vs " + timeResultFox.get(key) + " ms");
    }
    System.out.println("\n");
  }
}
