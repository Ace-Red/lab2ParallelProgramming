package com.assignment.multiplicationAlgorithms;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class ParallelCallable implements Callable<Map<String, Number>>
{
  private final int rowIndex;
  private final int colIndex;
  private final double[] row;
  private final double[] col;

  public ParallelCallable(double[] row, double[] col, int rowIndex, int colIndex)
  {
    this.row = row;
    this.rowIndex = rowIndex;
    this.col = col;
    this.colIndex = colIndex;
  }

  @Override
  public Map<String, Number> call()
  {
    Map<String, Number> result = new HashMap<>();
    double value = 0;
    for (int i = 0; i < row.length; i++)
    {
      value += row[i] * col[i];
    }

    result.put("value", value);
    result.put("rowIndex", this.rowIndex);
    result.put("columnIndex", this.colIndex);
    return result;
  }
}
