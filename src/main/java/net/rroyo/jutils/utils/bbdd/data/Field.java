package net.rroyo.jutils.utils.bbdd.data;

import net.rroyo.jutils.enums.bbdd.OrderBy;

public class Field {

    private final String fieldName;

    protected Field(String fieldName) {
        this.fieldName = fieldName;
    }

    public static Field field(String fieldName) {
        return new Field(fieldName);
    }
    public static Field field(String fieldName, OrderBy order) {
        return new OrderedField(fieldName, order);
    }



    @Override
    public String toString() {
        return fieldName;
    }
}
