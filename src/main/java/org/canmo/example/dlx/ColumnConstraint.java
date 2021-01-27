package org.canmo.example.dlx;

import org.canmo.ColumnName;

/**
 * @author Administrator
 */
public class ColumnConstraint implements ColumnName {
    String label;

    public ColumnConstraint(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "ColumnConstraint{" +
                "label='" + label + '\'' +
                '}';
    }
}
