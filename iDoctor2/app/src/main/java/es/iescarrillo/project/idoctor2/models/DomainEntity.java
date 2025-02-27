package es.iescarrillo.project.idoctor2.models;

public abstract class DomainEntity {
    private String id;

    public DomainEntity() {

    }

    public DomainEntity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DomainEntity{" +
                "id='" + id + '\'' +
                '}';
    }
}
