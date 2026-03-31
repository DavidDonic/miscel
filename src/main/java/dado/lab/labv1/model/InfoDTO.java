package dado.lab.labv1.model;

public record InfoDTO(long id, String name, Status status) {

    InfoDTO(Info info) {
        this(info.id, info.name, info.statue);
    }

    public InfoDTO(long id, String name, Status status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }
}
