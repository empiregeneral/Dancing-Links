package org.canmo.example.suduko;

import java.io.*;
import java.util.*;

import org.canmo.DancingLinks;

/**
 * This class uses the dancing links algorithm from Knuth to solve sudoku
 * puzzles. It has solved 42x42 puzzles in 1.02 seconds.
 */
public class Sudoku {

    /**
     * The preset values in the board
     * board[y][x] is the value at x,y with -1 = any
     */
    private int[][] board;

    /**
     * The size of the board
     */
    private int size;

    /**
     * The size of the sub-squares in cells across
     */
    private int squareXSize;

    /**
     * The size of the sub-squares in celss up and down
     */
    private int squareYSize;

    /**
     * This interface is a marker class for the columns created for the
     * Sudoku solver.
     */
    protected static interface ColumnName {
        // NOTHING
    }

    /**
     * A string containing a representation of the solution.
     * @param size the size of the board
     * @param solution a list of list of column names
     * @return a string of the solution matrix
     */
    static String stringifySolution(int size, List<List<ColumnName>> solution) {
        int[][] picture = new int[size][size];
        StringBuffer result = new StringBuffer();
        // go through the rows selected in the model and build a picture of the
        // solution.
        for(List<ColumnName> row: solution) {
            int x = -1;
            int y = -1;
            int num = -1;
            for(ColumnName item: row) {
                if (item instanceof ColumnConstraint) {
                    x = ((ColumnConstraint) item).column;
                    num = ((ColumnConstraint) item).num;
                } else if (item instanceof RowConstraint) {
                    y = ((RowConstraint) item).row;
                }
            }
            picture[y][x] = num;
        }
        // build the string
        for(int y=0; y < size; ++y) {
            for (int x=0; x < size; ++x) {
                result.append(picture[y][x]);
                result.append(" ");
            }
            result.append("\n");
        }
        return result.toString();
    }

    /**
     * An acceptor to get the solutions to the puzzle as they are generated and
     * print them to the console.
     */
    private static class SolutionPrinter
            implements DancingLinks.SolutionAcceptor<ColumnName> {
        int size;

        public SolutionPrinter(int size) {
            this.size = size;
        }

        /**
         * A debugging aid that just prints the raw information about the
         * dancing link columns that were selected for each row.
         * @param solution a list of list of column names
         */
        void rawWrite(List solution) {
            for (Iterator itr=solution.iterator(); itr.hasNext(); ) {
                Iterator subitr = ((List) itr.next()).iterator();
                while (subitr.hasNext()) {
                    System.out.print(subitr.next().toString() + " ");
                }
                System.out.println();
            }
        }


        public void solution(List<List<ColumnName>> names) {
            System.out.println(stringifySolution(size, names));
        }
    }

    /**
     * Set up a puzzle board to the given size.
     * Boards may be asymmetric, but the squares will always be divided to be
     * more cells wide than they are tall. For example, a 6x6 puzzle will make
     * sub-squares that are 3x2 (3 cells wide, 2 cells tall). Clearly that means
     * the board is made up of 2x3 sub-squares.
     * @param stream The input stream to read the data from
     */
    public Sudoku(InputStream stream) throws IOException {
        BufferedReader file = new BufferedReader(
                new InputStreamReader(stream));
        String line = file.readLine();
        List<int[]> result = new ArrayList<int[]>();
        while (line != null) {
            StringTokenizer tokenizer = new StringTokenizer(line);
            int size = tokenizer.countTokens();
            int[] col = new int[size];
            int y = 0;
            while(tokenizer.hasMoreElements()) {
                String word = tokenizer.nextToken();
                if ("?".equals(word)) {
                    col[y] = - 1;
                } else {
                    col[y] = Integer.parseInt(word);
                }
                y += 1;
            }
            result.add(col);
            line = file.readLine();
        }
        size = result.size();
        board = result.toArray(new int [size][]);
        squareYSize = (int) Math.sqrt(size);
        squareXSize = size / squareYSize;
        file.close();
    }

    public Sudoku(int[][] board, int size) {
        this.board = board;
        this.size = size;
        this.squareYSize = (int) Math.sqrt(size);
        this.squareXSize = size / squareYSize;
    }


    private static int[][] transformArray(int[] arr, int n) {
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

    /**
     * A constraint that each number can appear just once in a column.
     */
    static private class ColumnConstraint implements ColumnName {
        ColumnConstraint(int num, int column) {
            this.num = num;
            this.column = column;
        }
        int num;
        int column;

        @Override
        public String toString() {
            return num + " in column " + column;
        }
    }

    /**
     * A constraint that each number can appear just once in a row.
     */
    static private class RowConstraint implements ColumnName {
        RowConstraint(int num, int row) {
            this.num = num;
            this.row = row;
        }
        int num;
        int row;

        @Override
        public String toString() {
            return num + " in row " + row;
        }
    }

    /**
     * A constraint that each number can appear just once in a square.
     */
    static private class SquareConstraint implements ColumnName {
        SquareConstraint(int num, int x, int y) {
            this.num = num;
            this.x = x;
            this.y = y;
        }
        int num;
        int x;
        int y;
        @Override
        public String toString() {
            return num + " in square " + x + "," + y;
        }
    }

    /**
     * A constraint that each cell can only be used once.
     */
    static private class CellConstraint implements ColumnName {
        CellConstraint(int x, int y) {
            this.x = x;
            this.y = y;
        }
        int x;
        int y;

        @Override
        public String toString() {
            return "cell " + x + "," + y;
        }
    }

    /**
     * Create a row that places num in cell x, y.
     * @param rowValues a scratch pad to mark the bits needed
     * @param x the horizontal offset of the cell
     * @param y the vertical offset of the cell
     * @param num the number to place
     * @return a bitvector of the columns selected
     */
    private boolean[] generateRow(boolean[] rowValues, int x, int y, int num) {
        // clear the scratch array
        for(int i=0; i < rowValues.length; ++i) {
            rowValues[i] = false;
        }
        // find the square coordinates
        int xBox = x / squareXSize;
        int yBox = y / squareYSize;
        // mark the column
        rowValues[x*size + num - 1] = true;
        // mark the row
        rowValues[size*size + y*size + num - 1] = true;
        // mark the square
        rowValues[2*size*size + (xBox*squareXSize + yBox)*size + num - 1] = true;
        // mark the cell
        rowValues[3*size*size + size*x + y] = true;
        return rowValues;
    }

    private DancingLinks<ColumnName> makeModel() {
        DancingLinks<ColumnName> model = new DancingLinks<ColumnName>();
        // create all of the columns constraints
        for(int x=0; x < size; ++x) {
            for(int num=1; num <= size; ++num) {
                model.addColumn(new ColumnConstraint(num, x));
            }
        }
        // create all of the row constraints
        for(int y=0; y < size; ++y) {
            for(int num=1; num <= size; ++num) {
                model.addColumn(new RowConstraint(num, y));
            }
        }
        // create the square constraints
        for(int x=0; x < squareYSize; ++x) {
            for(int y=0; y < squareXSize; ++y) {
                for(int num=1; num <= size; ++num) {
                    model.addColumn(new SquareConstraint(num, x, y));
                }
            }
        }
        // create the cell constraints
        for(int x=0; x < size; ++x) {
            for(int y=0; y < size; ++y) {
                model.addColumn(new CellConstraint(x, y));
            }
        }
        boolean[] rowValues = new boolean[size*size*4];
        for(int x=0; x < size; ++x) {
            for(int y=0; y < size; ++y) {
                if (board[y][x] == -1) {
                    // try each possible value in the cell
                    for(int num=1; num <= size; ++num) {
                        model.addRow(generateRow(rowValues, x, y, num));
                    }
                } else {
                    // put the given cell in place
                    model.addRow(generateRow(rowValues, x, y, board[y][x]));
                }
            }
        }
        return model;
    }

    public void solve() {
        DancingLinks<ColumnName> model = makeModel();
        System.out.println("Puzzle solve: ");
        int results = model.solve(new SolutionPrinter(size));
        System.out.println("Found " + results + " solutions");
        System.out.println("-----------------");
    }

    /**
     * Solves a set of sudoku puzzles.
     * @param args a list of puzzle filenames to solve
     */

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String line;
        List<int[][]> puzzles = new ArrayList<int[][]>();
        for (; ((line = reader.readLine()) != null);) {
            int[] puzzle = new int[81];
            for (int i = 0; i < puzzle.length; i++) {
                char ch = line.charAt(i);
                if (ch == '.' || ch == '0') {
                    puzzle[i] = -1;
                } else {
                    puzzle[i] = ch - '0';
                }
            }
            puzzles.add(transformArray(puzzle, 9));
        }

        for (int[][] puzzle : puzzles) {
            Sudoku problem = new Sudoku(puzzle, 9);
            problem.solve();
        }

        reader.close();
        System.out.println("Elapse time: " + (System.currentTimeMillis() - start) + " ms.");
    }



}
