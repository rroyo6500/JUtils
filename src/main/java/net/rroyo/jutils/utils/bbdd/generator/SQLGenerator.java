package net.rroyo.jutils.utils.bbdd.generator;

import net.rroyo.jutils.enums.bbdd.OrderBy;
import net.rroyo.jutils.enums.bbdd.SQLInstruction;
import net.rroyo.jutils.utils.bbdd.condition.conditions.ConditionBuilder;
import net.rroyo.jutils.utils.bbdd.condition.conditions.ConditionGenerator;
import net.rroyo.jutils.utils.bbdd.data.field.Field;
import net.rroyo.jutils.utils.bbdd.data.table.Table;
import net.rroyo.jutils.utils.bbdd.data.field.OrderedField;

import java.sql.SQLException;

public final class SQLGenerator {

    private SQLBuilder sqlBuilder;

    private SQLGenerator() {}

    public static SQLGenerator sql() throws SQLException {
        return new SQLGenerator();
    }

    public static Field field(String fieldName) {
        return new Field(fieldName);
    }
    public static OrderedField field(String fieldName, OrderBy order) {
        return new OrderedField(fieldName, order);
    }
    public static Table table(String tableName) {
        return Table.table(tableName);
    }

    public static ConditionGenerator c() {
        return ConditionGenerator.c();
    }

    public Select select(Field... field) {
        sqlBuilder = new SQLBuilder(SQLInstruction.SELECT);
        return new Select(sqlBuilder, field);
    }

}
