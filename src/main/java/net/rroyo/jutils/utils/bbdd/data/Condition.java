package net.rroyo.jutils.utils.bbdd.data;

import java.sql.Connection;

public final class Condition {

    private final String sqlCondition;

    private Condition(String sqlCondition) {
        this.sqlCondition = sqlCondition;
    }

    public static Condition not(Condition condition) {
        return new Condition("NOT " + condition.toString());
    }

    public static Condition equals(Field f1) {
        return new Condition(f1 + " = ?");
    }
    public static Condition equals(Field f1, Field f2) {
        return new Condition(f1 + " = " + f2);
    }

    @Override
    public String toString() {
        return sqlCondition;
    }
}
