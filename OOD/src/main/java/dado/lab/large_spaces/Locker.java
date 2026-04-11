package dado.lab.large_spaces;

/*Geometrically the inclusion relationship is:
 *->locker -> cells -> packages
 *Functions needed:
 */

import dado.lab.NoSuitableCellRemainedException;
import dado.lab.aux_fields.Capacity;
import dado.lab.aux_fields.Cell;
import dado.lab.aux_fields.PackageSize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Locker {


    Map<PackageSize, Region> cells = new HashMap<>();
    Map<String, Node> occupied = new HashMap<>();//id - cell

    public Locker(int small, int medium, int large) {
        for (PackageSize size : PackageSize.values()) {
            cells.put(size, new Region());
        }
        initCells(small, medium, large);
    }

    public Locker() {
        for (PackageSize size : PackageSize.values()) {
            cells.put(size, new Region());
        }
        initCells(100, 100, 100);
    }

    public String store(Package pkg) {
        //TODO
        return "";
    }

    // --- helper functions ---

    private Node check(PackageSize size) {
        //is stored / no cell
        //return cell if able to be stored else return null
        Node node = cells.get(size).head.next;
        return node;
    }

    private String genCode(Package pkg, long timeStamp) {
        return "";
    }

    private Node assignCell(PackageSize size)
            throws NoSuitableCellRemainedException
    {
        Node avail = check(size);

        if (avail == null || avail.cell.isFull()) {
            throw new NoSuitableCellRemainedException("No cell available");
        }

        return avail;
    }

    private void initCells(int small, int medium, int large) {
        int[] sizes = new int[]{small, medium, large};
        for (int i = 0; i < sizes.length; i++) {
            Region region = cells.get(PackageSize.values()[i]);
            for (int j = 0; j < sizes[i]; j++) {
                Node node = new Node(new Cell(PackageSize.values()[i]));
                region.toTail(node);
            }
        }
    }

}

class Node {
    Cell cell;
    Node next;
    Node prev;

    Node(Cell cell) {
        this.cell = cell;
    }

    Node() {}
}

class Region {
    PackageSize capacity;
    Node head = new Node();
    Node tail = new Node();

    Region() {
        this.head.next = this.tail;
        this.tail.prev = this.head;
    }

    void toTail(Node node) {
        if (node.prev != null && node.next != null) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
        tail.prev.next = node;
        node.prev = tail.prev;
        node.next = tail;
        tail.prev = node;
    }
}