package com.assignment.multiplicationAlgorithms;

import com.assignment.*;
import com.assignment.Matrix;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class ParallelMultiplication implements MatrixMultiplyAlgorithm
{
  Matrix A;
  Matrix B;
  private final int nThread;

  public ParallelMultiplication(Matrix A, Matrix B, int nThread)
  {
    this.A = A;
    this.B = B;
    this.nThread = nThread;
  }

  @Override
  public Matrix multiply()
  {
    Matrix C = new Matrix(A.getRowSize(), B.getColSize());

    B.transpose();

    ExecutorService executor = Executors.newFixedThreadPool(this.nThread);

    List<Future<Map<String, Number>>> list = new ArrayList<>();

    for (int j = 0; j < B.getRowSize(); j++)
    {
      for (int i = 0; i < A.getRowSize(); i++)
      {

        Callable<Map<String, Number>> worker =
                new ParallelCallable(A.Row(i), B.Row(j),i, j);
        Future<Map<String, Number>> submit = executor.submit(worker);
        list.add(submit);
      }
    }

    for (Future<Map<String, Number>> mapFuture : list)
    {
      try
      {

        Map<String, Number> res = mapFuture.get();
        C.matrix[(int) res.get("rowIndex")][(int) res.get("columnIndex")] =
                (double) res.get("value");
      } catch (InterruptedException | ExecutionException e)
      {
        e.printStackTrace();
      }
    }

    executor.shutdown();
    B.transpose();
    return C;
  }
}
