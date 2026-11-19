package net.rroyo.jutils.utils.bbdd.data.field;

import net.rroyo.jutils.utils.bbdd.Syntax;
import net.rroyo.jutils.utils.bbdd.data.value.Value;
import net.rroyo.jutils.utils.bbdd.data.table.Table;
import net.rroyo.jutils.utils.bbdd.generator.Condition;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class Field {

    protected final Table table;
    protected final String fieldName;

    protected String fieldStr;

    public Field(Table table, String fieldName) {
        this.table = table;
        this.fieldName = fieldName;

        fieldStr = table + "." + fieldName;
    }

    public Field as(String colName) {
        fieldStr = this + " AS " + colName;
        return this;
    }

    // --- Condiciones ---

    public Condition eq(Object value) {
        if (value == null) return isNull();
        return new Condition(fieldName + " " + Syntax.Conditions.EQUAL + " ?", new Value(value));
    }

    public Condition ne(Object value) {
        if (value == null) return isNotNull();
        return new Condition(fieldName + " " + Syntax.Conditions.NOT_EQUAL + " ?", new Value(value));
    }

    public Condition gt(Object value) {
        return new Condition(fieldName + " " + Syntax.Conditions.GREATER + " ?", new Value(value));
    }

    public Condition ge(Object value) {
        return new Condition(fieldName + " >= ?", new Value(value));
    }

    public Condition lt(Object value) {
        return new Condition(fieldName + " " + Syntax.Conditions.LESS + " ?", new Value(value));
    }

    public Condition le(Object value) {
        return new Condition(fieldName + " <= ?", new Value(value));
    }

    public Condition like(String pattern) {
        return new Condition(fieldName + " " + Syntax.Conditions.LIKE + " ?", new Value(pattern));
    }

    public Condition between(Object min, Object max) {
        return new Condition(fieldName + " " + Syntax.Conditions.BETWEEN + "?", new Value(min), new Value(max));
    }

    public Condition in(Object... values) {
        Value[] v = Value.toArray(values);
        return in(Arrays.asList(v));
    }

    public Condition in(Collection<Value> values) {
        if (values == null || values.isEmpty()) {
            return new Condition("1 = 0");
        }
        String placeholders = values.stream()
                .map(v -> "?")
                .collect(Collectors.joining(", "));
        return new Condition(
                fieldName + " " + Syntax.Conditions.IN + " (" + placeholders + ")",
                values.stream().toList()
        );
    }

    public Condition isNull() {
        return new Condition(fieldName + " " + Syntax.Conditions.IS_NULL, Collections.emptyList());
    }

    public Condition isNotNull() {
        return new Condition(fieldName + " " + Syntax.Conditions.IS_NOT_NULL, Collections.emptyList());
    }

    // --- Comparaciones entre columnas (Ideal para JOIN ... ON) ---

    public Condition eq(Field otherField) {
        return new Condition(fieldName + " " + Syntax.Conditions.EQUAL + " " + otherField.toString());
    }

    public Condition ne(Field otherField) {
        return new Condition(fieldName + " " + Syntax.Conditions.NOT_EQUAL + " " + otherField.toString());
    }

    public Table getTable() {
        return table;
    }

    public String getFieldName() {
        return fieldName;
    }

    @Override
    public String toString() {
        return fieldStr;
    }

}
