package com.assignment.simpleMultiplication;

import com.assignment.MatrixMultiplyAlgorithm;
import com.assignment.Matrix;

public class SimpleMultiplication implements MatrixMultiplyAlgorithm
{
  Matrix A;
  Matrix B;

  public SimpleMultiplication(Matrix A, Matrix B)
  {
    this.A = A;
    this.B = B;
  }

  @Override
  public Matrix multiply()
  {
    Matrix C = new Matrix(A.getRowSize(), B.getColSize());

    for (int i = 0; i < A.getRowSize(); i++)
    {
      for (int j = 0; j < B.getColSize(); j++)
      {
        C.matrix[i][j] = 0;
        for (int k = 0; k < A.getColSize(); k++)
        {
          C.matrix[i][j] += A.matrix[i][k] * B.matrix[k][j];
        }
      }
    }
    return C;
  }
}
