package net.rroyo.jutils.utils.bbdd.data;

public final class Table {

    private final String tableName;

    private Table(String tableName) {
        this.tableName = tableName;
    }

    public static Table table(String tableName) {
        return new Table(tableName);
    }

    @Override
    public String toString() {
        return tableName;
    }
}
