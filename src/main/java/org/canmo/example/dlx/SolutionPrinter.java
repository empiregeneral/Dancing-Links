package org.canmo.example.dlx;

import org.canmo.ColumnName;
import org.canmo.DancingLinks;

import java.util.Iterator;
import java.util.List;

/**
 * An acceptor to get the solutions to the puzzle as they are generated and
 * print them to the console.
 *
 * @author canzuo
 */
public class SolutionPrinter implements DancingLinks.SolutionAcceptor<ColumnName> {

    @Override
    public void solution(List<List<ColumnName>> value) {
        System.out.println(stringifySolution(value));
    }

    private String stringifySolution(List<List<ColumnName>> solution) {
        StringBuilder sb = new StringBuilder();
        Iterator<List<ColumnName>> rows = solution.listIterator();
        for (;rows.hasNext();) {
            final List<ColumnName> row = rows.next();
            final Iterator<ColumnName> nodes = row.iterator();
            while (nodes.hasNext()) {
                ColumnName node = nodes.next();
                sb.append(node.toString());
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
