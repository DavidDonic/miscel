package dado.lab.managed_objects;

import dado.lab.aux_fields.PackageSize;

public class Package {
    public String code;
    public String receiver;
    public PackageSize size;
    public long arrivedAt;

    public Package(String code, String receiver, PackageSize size, long arrivedAt) {
        this.code = code;
        this.receiver = receiver;
        this.size = size;
        this.arrivedAt = arrivedAt;
    }

    public Package(String receiver, PackageSize size, long arrivedAt) {
        this.receiver = receiver;
        this.size = size;
        this.arrivedAt = arrivedAt;
    }

    public Package(String receiver, PackageSize size) {
        this.receiver = receiver;
        this.size = size;
    }
}
