package net.rroyo.jutils.utils.bbdd.data.table;

import net.rroyo.jutils.utils.bbdd.SQL;
import net.rroyo.jutils.utils.bbdd.data.field.Field;

import java.sql.SQLException;
import java.util.List;

public final class Table {

    private final String tableName;
    private int colNum;
    private boolean error = false;

    public Table(String tableName) {
        this.tableName = tableName;
        this.colNum = getTableColumnCount();
    }

    private int getTableColumnCount() {
        int columnCount = 0;
        try (SQL sql = new SQL("SELECT * FROM " + tableName + " LIMIT 0", List.of())) {
            columnCount = sql.executeQuery().getMetaData().getColumnCount();
            error = false;
        } catch (SQLException ignored) {
            error = true;
        }
        return columnCount;
    }

    public static Table table(String tableName) {
        return new Table(tableName);
    }

    public Field field(String fieldName) {
        return new Field(this, fieldName);
    }

    public int getColNum() {
        if (error) {
            colNum = getTableColumnCount();
        }
        return colNum;
    }

    @Override
    public String toString() {
        return tableName;
    }
}
