package org.canmo;

import java.util.ArrayList;
import java.util.List;

/**
 * A generic solver for tile laying problems using Knuth's dancing link
 * algorithm. It provides a very fast backtracking data structure for problems
 * that can expressed as a sparse boolean matrix where the goal is to select a
 * subset of the rows such that each column has exactly 1 true in it.
 *
 * The application gives each column a name and each row is named after the
 * set of columns that it has as true. Solutions are passed back by giving the
 * selected rows' names.
 *
 * The type parameter ColumnName is the class of application's column names.
 * @author Administrator
 */
public class DancingLinks<ColumnName> {
    /**
     * The head of the table. Left/Right from the head are the unsatisfied
     * ColumnHeader objects.
     */
    private ColumnHeader<ColumnName> head;

    /**
     * The complete list of columns.
     */
    private List<ColumnHeader<ColumnName>> columns;

    public DancingLinks() {
        head = new ColumnHeader<ColumnName>(null, 0);
        head.left = head;
        head.right = head;
        head.up = head;
        head.down = head;
        columns = new ArrayList<ColumnHeader<ColumnName>>(200);
    }

    /**
     * Add a column to the table
     * @param name The name of the column, which will be returned as part of
     *             solutions
     * @param primary Is the column required for a solution?
     */
    public void addColumn(ColumnName name, boolean primary) {
        ColumnHeader<ColumnName> top = new ColumnHeader<ColumnName>(name, 0);
        top.up = top;
        top.down = top;
        if (primary) {

        } else {

        }
    }

    /**
     * Add a column to the table
     * @param name The name of the column, which will be included in the solution
     */
    public void addColumn(ColumnName name) {
        addColumn(name, true);
    }

    /**
     * Get the number of columns.
     * @return the number of columns
     */
    public int getNumberColumns() {
        return columns.size();
    }

    /**
     * Get the name of a given column as a string
     * @param index the index of the column
     * @return a string representation of the name
     */
    public String getColumnName(int index) {
        return columns.get(index).name.toString();
    }

    /**
     * Add a row to the table.
     * @param values the columns that are satisfied by this row
     */
    public void addRow(boolean[] values) {

    }

    /**
     * Given a prefix, find solutions under it.
     * @param prefix a list of row choices that control which part of the search
     *               tree to explore
     * @param output the output for each solution
     * @return the number of solutions
     */
    public int solve(int[] prefix, SolutionAcceptor<ColumnName> output) {
        return 0;
    }

    /**
     * Solve a complete problem
     * @param output the acceptor to receive answers
     * @return the number of solutions
     */
    public int solve(SolutionAcceptor<ColumnName> output) {
        return 0;
    }









}
