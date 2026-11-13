package net.rroyo.jutils.utils.bbdd.generator;

import net.rroyo.jutils.enums.bbdd.OrderBy;
import net.rroyo.jutils.utils.bbdd.data.Field;
import net.rroyo.jutils.utils.bbdd.data.Table;

import java.sql.SQLException;

public final class SQLGenerator {

    private final SQLBuilder sqlBuilder = new SQLBuilder();

    private SQLGenerator() {}

    public static SQLGenerator sql() throws SQLException {
        return new SQLGenerator();
    }

    public static Field field(String fieldName) {
        return Field.field(fieldName);
    }
    public static Field field(String fieldName, OrderBy order) {
        return Field.field(fieldName, order);
    }
    public static Table table(String tableName) {
        return Table.table(tableName);
    }

    public Select select(Field... field) {
        return new Select(sqlBuilder, field);
    }

}
