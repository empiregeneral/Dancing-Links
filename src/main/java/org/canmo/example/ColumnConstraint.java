package org.canmo.example;

import org.canmo.ColumnName;

/**
 * @author Administrator
 */
public class ColumnConstraint implements ColumnName {
    boolean isMark;
    int y;

    public ColumnConstraint(int y, boolean isMark) {
        this.y = y;
        this.isMark = isMark;
    }

    @Override
    public String toString() {
        return "ColumnConstraint{" +
                "isMark=" + isMark +
                ", y=" + y +
                '}';
    }
}
