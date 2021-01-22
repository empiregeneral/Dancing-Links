package org.canmo.example;

import org.canmo.ColumnName;
import org.canmo.DancingLinks;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author Administrator
 */
public class ExtractCover {
    private int[][] grid;

    public ExtractCover(InputStream inputStream) throws IOException {
        readFromInputStream(inputStream);
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
        int size = result.size();
        this.grid = result.toArray(new int[size][]);
        reader.close();
    }

    public void solve() {
        DancingLinks<ColumnName> model = makeModel();
    }

    private DancingLinks<ColumnName> makeModel() {
        DancingLinks<ColumnName> model = new DancingLinks<ColumnName>();
        return model;
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("");
            System.exit(-1);
        }
        for ( int i = 0; i < args.length; ++i) {
            ExtractCover problem = new ExtractCover(new FileInputStream(args[i]));
            System.out.println("Solving " + args[i]);
            System.out.println(Arrays.deepToString(problem.grid));
        }

    }
}
