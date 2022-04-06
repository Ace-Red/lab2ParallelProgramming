package com.assignment.multiplicationAlgorithms;

import com.assignment.Matrix;

public class FoxThread extends Thread
{
  private final Matrix A;
  private final Matrix B;
  private final Matrix C;

  private final int stepI;
  private final int stepJ;

  public FoxThread(Matrix A, Matrix B, Matrix C, int stepI, int stepJ) {

    this.A = A;
    this.B = B;
    this.C = C;

    this.stepI = stepI;
    this.stepJ = stepJ;
  }

  @Override
  public void run()
  {
    Matrix blockRes = multiplyBlock();

    for (int i = 0; i < blockRes.getRowSize(); i++)
    {
      for (int j = 0; j < blockRes.getColSize(); j++)
      {
        C.matrix[i + stepI][j + stepJ] += blockRes.matrix[i][j];
      }
    }
  }

  private Matrix multiplyBlock()
  {
    Matrix blockRes = new Matrix(A.getColSize(), B.getRowSize());
    for (int i = 0; i < A.getRowSize(); i++)
    {
      for (int j = 0; j < B.getColSize(); j++)
      {
        for (int k = 0; k < A.getColSize(); k++)
        {
          blockRes.matrix[i][j] += A.matrix[i][k] * B.matrix[k][j];
        }
      }
    }
    return blockRes;
  }
}
