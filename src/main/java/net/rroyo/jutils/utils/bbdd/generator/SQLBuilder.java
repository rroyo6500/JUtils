package net.rroyo.jutils.utils.bbdd.generator;

import net.rroyo.jutils.utils.bbdd.data.value.Value;

import java.util.ArrayList;
import java.util.List;

public final class SQLBuilder {

    private final StringBuilder sql = new StringBuilder();
    private final List<Value> values = new ArrayList<>();

    public SQLBuilder() {
    }

    public SQLBuilder append(String... string) {
        for (String s : string) {
            sql.append(s);
            sql.append(" ");
        }
        return this;
    }

    public void addValues(List<Value> value) {
        this.values.addAll(value);
    }

    public List<Value> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return sql.toString();
    }
}
