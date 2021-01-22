package org.canmo;

/**
 * Column headers record the name of the column and the number of rows that
 * satisfy this column. The names are provided by the application and can
 * be anything. The size is used for the heuristic for picking the next
 * column to explore.
 * @author canzuo
 */
public class ColumnHeader<ColumnName> extends Node<ColumnName> {

    int size;
    ColumnName name;

    public ColumnHeader(ColumnName n,int s) {
        name = n;
        size = s;
        head = this;
    }
}
