package dado.lab.aux_fields;
import dado.lab.managed_objects.Package;

public class Cell {
    private PackageSize capacity;
    private dado.lab.managed_objects.Package pkg;

    public Cell(PackageSize capacity, Package pkg) {
        this.capacity = capacity;
        this.pkg = pkg;
    }

    public Cell(PackageSize capacity) {
        this.capacity = capacity;
    }

    public String put(Package pkg) {
        //giving a package, locker will assign a cell to store it
        //future retrieve request ->
        //given package, assign the cell, check the cell & package, put
        this.pkg = pkg;
        return pkg.code;
    }

    public void pick() {
        this.pkg.code = null;
        this.pkg = null;
    }

    public boolean isFull() {
        return this.pkg != null;
    }

    public PackageSize getCapacity() {
        return capacity;
    }
}
