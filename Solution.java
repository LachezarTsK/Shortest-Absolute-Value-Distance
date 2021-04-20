import java.util.PriorityQueue;

public class Solution {

  int[][] moves = {
    {-1, 0}, // up
    {1, 0}, // down
    {0, -1}, // left
    {0, 1} // right
  };

  /*
    By the problem design on www.binarysearch.com, we have to work
    around the given method 'public int solve(int[][] matrix)' in order for the code
    to be able to run on the website. Even though the name 'solve' does not make
    a lot of sense, it is left as it is, so that the code can be run directly on the website,
    without any modifications.
  */
  public int solve(int[][] matrix) {
    return dijkstraSearch_shortestAbsoluteValueDistance(matrix);
  }

  /*
    @return Shortest Absolute Value Distance(as defined by the problem statement) for the path
            from matrix[0][0] to matrix[matrix.length-1][matrix[0].length-1].
  */
  public int dijkstraSearch_shortestAbsoluteValueDistance(int[][] matrix) {

    int[][] distanceFromStart = new int[matrix.length][matrix[0].length];
    boolean[][] visited = new boolean[matrix.length][matrix[0].length];
    PriorityQueue<Point> minHeap = new PriorityQueue<Point>();

    Point start = new Point(0, 0);
    minHeap.add(start);

    fill_distanceFromStart_with_integerMaxValue(distanceFromStart);
    distanceFromStart[0][0] = 0;

    while (!minHeap.isEmpty()) {

      Point current = minHeap.poll();
      int r = current.row;
      int c = current.column;

      if (r == matrix.length - 1 && c == matrix[0].length - 1) {
        break;
      }
      visited[r][c] = true;

      for (int i = 0; i < moves.length; i++) {
        int new_r = r + moves[i][0];
        int new_c = c + moves[i][1];

        if (isInMatrix(matrix, new_r, new_c) && !visited[new_r][new_c]) {

          int previousDistanceFromStart =
              distanceFromStart[r][c] + Math.abs(matrix[r][c] - matrix[new_r][new_c]);

          if (distanceFromStart[new_r][new_c] > previousDistanceFromStart) {

            distanceFromStart[new_r][new_c] = previousDistanceFromStart;
            Point neighbour = new Point(new_r, new_c);
            neighbour.distanceFromStart = distanceFromStart[new_r][new_c];
            minHeap.add(neighbour);
          }
        }
      }
    }
    return distanceFromStart[matrix.length - 1][matrix[0].length - 1];
  }

  public void fill_distanceFromStart_with_integerMaxValue(int[][] distanceFromStart) {
    for (int row = 0; row < distanceFromStart.length; row++) {
      Arrays.fill(distanceFromStart[row], Integer.MAX_VALUE);
    }
  }

  public boolean isInMatrix(int[][] matrix, int r, int c) {
    if (r < 0 || c < 0 || r > matrix.length - 1 || c > matrix[0].length - 1) {
      return false;
    }
    return true;
  }
}

class Point implements Comparable<Point> {
  int row;
  int column;
  int distanceFromStart;

  public Point(int row, int column) {
    this.row = row;
    this.column = column;
  }

  @Override
  public int compareTo(Point other) {
    return this.distanceFromStart - other.distanceFromStart;
  }
}
