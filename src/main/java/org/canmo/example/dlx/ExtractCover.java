package org.canmo.example.dlx;

import org.canmo.ColumnName;
import org.canmo.DancingLinks;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Administrator
 */
public class ExtractCover {
    private int[][] grid;
    private boolean[][] board;
    private int size;
    private String labels = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public ExtractCover(InputStream inputStream) throws IOException {
        readFromInputStream(inputStream);
        board = init(this.grid);
    }

    /**
     * ReadFrom IO InputStream into int[][] grid
     * @param inputStream
     * @throws IOException
     */
    private void readFromInputStream(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
        String line = reader.readLine();
        List<int[]> result = new ArrayList();
        while (line != null) {
            StringTokenizer tokenizer = new StringTokenizer(line);
            int size = tokenizer.countTokens();
            int[] col = new int[size];
            for (int i = 0; tokenizer.hasMoreElements(); i++) {
                col[i] = Integer.parseInt(tokenizer.nextToken());
            }
            result.add(col);
            line = reader.readLine();
        }
        this.size = result.size();
        this.grid = result.toArray(new int[size][]);
        reader.close();
    }

    private boolean[][] init(int[][] values) {
        List<boolean[]> booleanList = new ArrayList<boolean[]>(values.length);
        for (int i = 0; i < values.length; i++) {
            booleanList.add(transform01Matrix(values[i]));
        }
        return booleanList.toArray(new boolean[values.length][]);
    }

    private boolean[] transform01Matrix(int[] items) {
        boolean[] tmp = new boolean[items.length];
        for (int i = 0; i < items.length; i++) {
            if ((items[i] == 1)) {
                tmp[i] = true;
            } else {
                tmp[i] = false;
            }
        }
        return tmp;
    }


    public void solve() {
        DancingLinks<ColumnName> model = makeModel();
        int result = model.solve((new SolutionPrinter()));

    }

    private DancingLinks<ColumnName> makeModel() {
        DancingLinks<ColumnName> model = new DancingLinks<ColumnName>();
        for ( int y = 0; y < this.board[0].length; y++) {
            model.addColumn(new ColumnConstraint(""+labels.charAt(y)));
        }

        for ( int x = 0; x < this.board.length; x++) {
            model.addRow(board[x]);
        }
        return model;
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("");
            System.exit(-1);
        }
        for ( int i = 0; i < args.length; ++i) {
            ExtractCover problem = new ExtractCover(new FileInputStream(args[i]));
            problem.solve();
        }

    }
}
