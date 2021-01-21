package org.canmo;


 /**
 * A cell in the table with up/down and left/right links that form doubly
 * linked lists in both directions. It also includes a link to the column
 * head.
 */
public class Node<ColumnName> {
    /** Left */
    Node<ColumnName> left;
    /** Right */
    Node<ColumnName> right;
    /** Upper */
    Node<ColumnName> up;
    /** Down */
    Node<ColumnName> down;

    ColumnHeader<ColumnName> head;

    public Node(Node<ColumnName> l, Node<ColumnName> r,
                Node<ColumnName> u, Node<ColumnName> d,
                ColumnHeader<ColumnName> h) {
        this.left = l;
        this.right = r;
        this.up = u;
        this.down = d;
        this.head = h;
    }

    public Node() {
        this(null, null, null, null, null);
    }

     @Override
     public String toString() {
         return "Node{" +
                 "left=" + left.toString() +
                 ", right=" + right.toString() +
                 ", up=" + up.toString() +
                 ", down=" + down.toString() +
                 ", head=" + head.toString() +
                 '}';
     }
 }
