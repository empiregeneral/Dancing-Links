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

    @Override
    public String toString() {
        return "ColumnHeader{" +
                "size=" + size +
                ", name=" + name +
                '}';
    }

    public static void main(String[] args) {
        ColumnHeader<org.canmo.ColumnName> header = new ColumnHeader<org.canmo.ColumnName>(null, 0);
        header.left = header;
        header.right = header;
        header.up = header;
        header.down = header;

        System.out.println(header.toString());
    }
}
