package org.canmo;

import java.util.List;

/**
 * Applications should implement this to receive the solutions to their
 * problems.
 * @author canzuo
 */
public interface SolutionAcceptor<ColumnName> {
    /**
     * A callback to return a solution to the application.
     * @param value a List of List of ColumnNames that were satisfied by each
     *              selected row
     */
    void solution(List<List<ColumnName>> value);
}
