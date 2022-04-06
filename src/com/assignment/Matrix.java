package com.assignment;

import java.util.Arrays;

public class Matrix
{
  public double[][] matrix;
  private final int row;
  private final int col;

  public Matrix(int sizeAxis0, int sizeAxis1)
  {
    this.matrix = new double[sizeAxis0][sizeAxis1];
    this.row = sizeAxis0;
    this.col = sizeAxis1;
  }

  public void print()
  {
    for (int i = 0; i < this.row; i++)
    {
      for (int j = 0; j < this.col; j++)
      {
        System.out.printf("%10.1f", this.matrix[i][j]);
      }
      System.out.println();
    }
  }

  public void transpose()
  {
    double[][] newMatrix = new double[this.getColSize()][this.getRowSize()];
    for (int i = 0; i < matrix[0].length; i++)
    {
      for (int j = 0; j < matrix.length; j++)
      {
        newMatrix[i][j] = this.matrix[j][i];
      }
    }
    this.matrix = newMatrix;
  }

  public double[] Row(int index)
  {
    return this.matrix[index];
  }

  public double[] getColumn(int index)
  {
    return Arrays.stream(matrix).mapToDouble(doubles -> doubles[index]).toArray();
  }

  public int getRowSize()
  {
    return this.row;
  }

  public int getColSize()
  {
    return this.col;
  }

  public void generateRandomMatrix()
  {
    for (int i = 0; i < this.row; i++)
    {
      for (int j = 0; j < this.col; j++)
      {
        // this.matrix[i][j] = Math.random();
        this.matrix[i][j] = 1;

      }
    }
  }
}
