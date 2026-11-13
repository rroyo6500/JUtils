package net.rroyo.jutils.utils.bbdd.data.table;

import net.rroyo.jutils.utils.bbdd.data.field.Field;

public final class Table {

    private final String tableName;

    public Table(String tableName) {
        this.tableName = tableName;
    }

    public static Table table(String tableName) {
        return new Table(tableName);
    }

    public Field field(String fieldName) {
        return new Field(tableName + '.' + fieldName);
    }

    @Override
    public String toString() {
        return tableName;
    }
}
