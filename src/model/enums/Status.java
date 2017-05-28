package model.enums;


public enum Status {
    ZREALIZOWANE("ZREALIZOWANE"),
    BRAK("BRAK");

    private String name;


    private Status(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
