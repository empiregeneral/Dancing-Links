package org.canmo.example;

import org.canmo.ColumnName;
import org.canmo.DancingLinks;

import java.util.List;

/**
 * An acceptor to get the solutions to the puzzle as they are generated and
 * print them to the console.
 *
 * @author canzuo
 */
public class SolutionPrinter implements DancingLinks.SolutionAcceptor<ColumnName> {
    private int size;

    public SolutionPrinter(int size) {
        this.size = size;
    }

    public void solution(List<List<ColumnName>> value) {
        System.out.println(stringifySolution(this.size, value));
    }

    private String stringifySolution(int size, List<List<ColumnName>> solution) {
        int[] picture = new int[size];
        StringBuilder sb = new StringBuilder();
        for (List<ColumnName> row : solution) {
            int y = -1;
            int num = -1;
            for (ColumnName item : row) {
                if (item instanceof ColumnName) {
                    y = ((ColumnConstraint) item).y;
                    num = y;
                }
            }
            picture[y] = num;
        }

        for (int i = 0; i < size; i++) {
            sb.append(picture[i]);
            sb.append(" ");
        }
        sb.append("\n");
        return sb.toString();
    }
}
