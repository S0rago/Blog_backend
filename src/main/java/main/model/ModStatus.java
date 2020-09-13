package main.model;

public enum ModStatus {
    NEW("NEW"),
    ACCEPTED("ACCEPTED"),
    DECLINED("DECLINED");

    private final String status;

    ModStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
