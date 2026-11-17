package net.rroyo.jutils.utils.bbdd.data.field;

import net.rroyo.jutils.utils.bbdd.Syntax;
import net.rroyo.jutils.utils.bbdd.data.table.Table;
import net.rroyo.jutils.utils.bbdd.generator.Condition;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

public class Field {

    private final Table table;
    private final String fieldName;

    private final String fieldStr;

    public Field(Table table, String fieldName) {
        this.table = table;
        this.fieldName = fieldName;

        fieldStr = table + "." + fieldName;
    }

    // Constructor privado para funciones
    private Field(String function, Field field) {
        this.table = field.table;
        this.fieldName = field.fieldName;

        fieldStr = function + '(' + field + ')';
    }

    // --- Condiciones ---

    public Condition eq(Object value) {
        if (value == null) return isNull();
        return new Condition(fieldName + " " + Syntax.Conditions.EQUAL + " ?", value);
    }

    public Condition ne(Object value) {
        if (value == null) return isNotNull();
        return new Condition(fieldName + " " + Syntax.Conditions.NOT_EQUAL + " ?", value);
    }

    public Condition gt(Object value) {
        return new Condition(fieldName + " " + Syntax.Conditions.GREATER + " ?", value);
    }

    public Condition ge(Object value) {
        return new Condition(fieldName + " >= ?", value);
    }

    public Condition lt(Object value) {
        return new Condition(fieldName + " " + Syntax.Conditions.LESS + " ?", value);
    }

    public Condition le(Object value) {
        return new Condition(fieldName + " <= ?", value);
    }

    public Condition like(String pattern) {
        return new Condition(fieldName + " " + Syntax.Conditions.LIKE + " ?", pattern);
    }

    public Condition between(Object min, Object max) {
        return new Condition(fieldName + " " + Syntax.Conditions.BETWEEN + "?", min, max);
    }

    public Condition in(Object... values) {
        return in(Arrays.asList(values));
    }

    public Condition in(Collection<?> values) {
        if (values == null || values.isEmpty()) {
            return new Condition("1 = 0");
        }
        String placeholders = values.stream()
                .map(v -> "?")
                .collect(Collectors.joining(", "));
        return new Condition(
                fieldName + " " + Syntax.Conditions.IN + " (" + placeholders + ")",
                values.toArray()
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

    // --- Funciones SELECT ---

    public Field min() {
        return new Field(Syntax.Functions.MIN, this);
    }

    public Field max() {
        return new Field(Syntax.Functions.MAX, this);
    }

    public Field sum() {
        return new Field(Syntax.Functions.SUM, this);
    }

    public Field avg() {
        return new Field(Syntax.Functions.AVG, this);
    }

    public Table getTable() {
        return table;
    }

    @Override
    public String toString() {
        return fieldStr;
    }

}
