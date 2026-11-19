package net.rroyo.jutils.utils.bbdd.generator;

import net.rroyo.jutils.utils.bbdd.Syntax;
import net.rroyo.jutils.utils.bbdd.data.value.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Condition {

    private final String sql;
    private final List<Value> values;

    public Condition(String sql, List<Value> values) {
        this.sql = sql;
        this.values = values != null ? new ArrayList<>(values) : new ArrayList<>();
    }

    public Condition(String sql, Value... values) {
        this.sql = sql;
        this.values = new ArrayList<>();
        if (values != null) {
            Collections.addAll(this.values, values);
        }
    }

    public Condition and(Condition other) {
        return combine(Syntax.AND, other);
    }

    public Condition or(Condition other) {
        return combine(Syntax.OR, other);
    }

    public Condition not() {
        return new Condition(Syntax.Conditions.NOT + " (" + this.sql + ")", this.values);
    }

    private Condition combine(String operator, Condition other) {
        if (other == null) return this;

        // Agrupamos con paréntesis si se combina con OR para respetar precedencia SQL
        String combinedSql = "(" + this.sql + " " + operator + " " + other.sql + ")";
        List<Value> combinedValues = new ArrayList<>(this.values);
        combinedValues.addAll(other.values);

        return new Condition(combinedSql, combinedValues);
    }

    public List<Value> getValues() {
        return Collections.unmodifiableList(values);
    }

    @Override
    public String toString() {
        return sql;
    }

}
