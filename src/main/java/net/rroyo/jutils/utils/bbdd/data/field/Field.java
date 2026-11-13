package net.rroyo.jutils.utils.bbdd.data.field;

public class Field {

    private final String fieldName;

    public Field(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String toString() {
        return fieldName;
    }
}
