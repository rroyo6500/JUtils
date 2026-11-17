package net.rroyo.jutils.utils.bbdd.generator;

import net.rroyo.jutils.enums.bbdd.OrderBy;
import net.rroyo.jutils.utils.bbdd.Syntax;
import net.rroyo.jutils.utils.bbdd.data.field.Field;
import net.rroyo.jutils.utils.bbdd.data.table.Table;
import net.rroyo.jutils.utils.bbdd.data.field.OrderedField;

import java.sql.SQLException;

public final class SQLGenerator {

    private SQLBuilder sqlBuilder = new SQLBuilder();

    private SQLGenerator() {}

    public static SQLGenerator sql() throws SQLException {
        return new SQLGenerator();
    }

    public static Field field(Table table, String fieldName) {
        return new Field(table, fieldName);
    }
    public static OrderedField field(Table table, String fieldName, OrderBy order) {
        return new OrderedField(table, fieldName, order);
    }
    public static Table table(String tableName) {
        return Table.table(tableName);
    }

    public static Condition and(Condition... conditions) {
        return combineAll(Syntax.AND, conditions);
    }

    public static Condition or(Condition... conditions) {
        return combineAll(Syntax.OR, conditions);
    }

    public static Condition not(Condition condition) {
        return condition.not();
    }

    private static Condition combineAll(String operator, Condition... conditions) {
        if (conditions == null || conditions.length == 0) return null;
        Condition result = conditions[0];
        for (int i = 1; i < conditions.length; i++) {
            result = operator.equals(Syntax.AND) ? result.and(conditions[i]) : result.or(conditions[i]);
        }
        return result;
    }

    public Select select(Field... field) {
        return new Select(sqlBuilder, field);
    }

    public InsertInto insertInto(Table table, Field... field) {
        return new InsertInto(sqlBuilder, table, field);
    }

    public DeleteFrom deleteFrom(Table table) {
        return new DeleteFrom(sqlBuilder, table);
    }

    public Update update(Table table) {
        return new Update(sqlBuilder, table);
    }

}
