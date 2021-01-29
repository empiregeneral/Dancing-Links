
import org.canmo.example.suduko.Sudoku;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String separator = ",";

        String line;
        List<int[][]> puzzles = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (;((line = reader.readLine()) != null) ;) {
            if (line.equals("")) {
                sb.append(separator);
            }
            sb.append(line);
        }

        for (String puzzleWithStr : sb.toString().split(separator)) {
            int[] puzzle = new int[256];
            for (int i = 0; i < puzzle.length; i++) {
                char ch = puzzleWithStr.charAt(i);
                if (ch == '-' || ch == '0') {
                    puzzle[i] = -1;
                } else {
                    puzzle[i] = ch - 'A' + 1;
                }
            }
            puzzles.add(transformArray(puzzle, 16));
        }

        for (int[][] puzzle : puzzles) {
            Sudoku problem = new Sudoku(puzzle, 16);
            problem.solve();
        }

        reader.close();

        System.out.println("Elapse time: " + (System.currentTimeMillis() - start) + "ms.");
    }

    static int[][] transformArray(int[] arr, int n) {
        assert arr.length % n == 0;
        int[][] result = new int[n][n];
        for (int index = 0; index < arr.length; index++) {
            int num = arr[index];
            int row = index / n;
            int col = index % n;
            result[row][col] = num;
        }

        return result;
    }
}
