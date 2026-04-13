package dado.lab.large_spaces;

/*Geometrically the inclusion relationship is:
 *->locker -> cells -> packages
 *Functions needed:
 */

/*
 * improvement:
 * 1. Region, Node move to Locker as they are just managed container
 * 2. single source of truth of code
 * 3. uniqueness of pickup code at the time
 */

import dado.lab.exceptions.*;
import dado.lab.aux_fields.Cell;
import dado.lab.aux_fields.PackageSize;
import org.w3c.dom.Node;

import javax.swing.plaf.synth.Region;
import java.util.HashMap;
import java.util.Map;

public class Locker {

    //Inner classes
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
        private PackageSize capacity;
        private Node head = new Node();
        private Node tail = new Node();

        Region(PackageSize capacity) {
            this.capacity = capacity;
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

        void toHead(Node node) {
            if (node.prev != null && node.next != null) {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }
            head.next.prev = node;
            node.prev = head;
            node.next = head.next;
            head.next = node;
        }

        Node assignCell(dado.lab.managed_objects.Package pkg) throws RuntimeException{
            //Node avail = check(size);
            // --- current logic: exact size matched only ---
            // --- future extension: larger size escalation until found 1 empty
            Node avail = head.next;
            if (avail.cell == null || avail.cell.isFull()) {
                throw new NoSuitableCellRemainedException("No cell available");
            }
            avail.cell.put(pkg);
            toTail(avail);
            return avail;
        }

        Node clearCell(Node node) {
            node.cell.pick();
            toHead(node);
            return node;
        }
    }


    private Map<PackageSize, Region> cells = new HashMap<>();
    private Map<String, Node> occupied = new HashMap<>();//id - cell
    //private int defaultRegionSize = 100;

    public Locker(int small, int medium, int large) {
        for (PackageSize size : PackageSize.values()) {
            cells.put(size, new Region(size));
        }
        initCells(small, medium, large);
    }

    public Locker(int uniformCapacity) throws Exception {
        if (uniformCapacity < 1) {
            throw new InvalidCapacityValueException("Positive integer only as region capacity");
        }
        initCells(uniformCapacity);
    }

    public String store(dado.lab.managed_objects.Package pkg) throws Exception {
        //TODO: receiver info. check (specified / not specified / not require (anyone with code)
        if (pkg.size == null) {
            throw new PackageSizeMissingException("Package size is missing !!!");
        } else if (pkg.code != null) {
            throw new DuplicateStorageException("Package is already stored");
        }

        Region location = cells.get(pkg.size);
        Node assigned = location.assignCell(pkg);
        String code = genCode(pkg);
        pkg.code = code;
        occupied.put(code, assigned);

        return code;
    }

    public String retrieve(String code) throws InvalidPickUpCodeException {
        //TODO: retriever identity check
        if (!occupied.containsKey(code)) {
            throw new InvalidPickUpCodeException("Invalid pick up code !!!");
        }

        Region location = cells.get(occupied.get(code).cell.getCapacity());
        Node cleaned = location.clearCell(occupied.get(code));
        occupied.remove(code);

        return "retrieved: " + code;
    }

    // --- helper functions ---

    private Node check(PackageSize size) throws NoSuitableCellRemainedException {
        //is stored / no cell
        //return cell if able to be stored else return null
        Region targetRegion = cells.get(size);
        Node node = targetRegion.head.next;
        if (node == null || node.cell.isFull()) {
            throw new NoSuitableCellRemainedException("No cell available");
        }
        targetRegion.toTail(node);
        return node;
    }

    private String genCode(dado.lab.managed_objects.Package pkg) {
        return pkg.receiver + Long.toString(System.currentTimeMillis());
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

    private void initCells(int capacity) {
        for (PackageSize size : PackageSize.values()) {
            cells.put(size, new Region(size));
        }
        for (PackageSize size : PackageSize.values()) {
            Region region = cells.get(size);
            for (int i = 0; i < capacity; i++) {
                Node node = new Node(new Cell(size));
                region.toTail(node);
            }
        }
    }

}