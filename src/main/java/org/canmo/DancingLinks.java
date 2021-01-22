package org.canmo;

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
public class DancingLinks<ColumnHeader> {


}
