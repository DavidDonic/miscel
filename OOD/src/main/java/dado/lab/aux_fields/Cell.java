package dado.lab.aux_fields;
import dado.lab.managed_objects.Package;

public class Cell {
    PackageSize capacity;
    Package pkg;

    public Cell(PackageSize capacity, Package pkg) {
        this.capacity = capacity;
        this.pkg = pkg;
    }

    public Cell(PackageSize capacity) {
        this.capacity = capacity;
    }

    public String put(Package pkg) {
        //give a package, locker will assign a cell to store it
        //future retrieve request ->
        //given pacage, assign the cell, check the cell & package, put
        this.pkg = pkg;
        return pkg.code;
    }

    public void pick() {
        this.pkg = null;
    }

    public boolean isFull() {
        return this.pkg != null;
    }
}
