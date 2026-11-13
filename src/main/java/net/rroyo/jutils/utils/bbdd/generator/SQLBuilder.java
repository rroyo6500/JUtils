package net.rroyo.jutils.utils.bbdd.generator;

import java.util.ArrayList;
import java.util.List;

public final class SQLBuilder {

    private final StringBuilder sql = new StringBuilder();
    private final List<Object> values = new ArrayList<>();

    public SQLBuilder() {
    }

    public SQLBuilder append(String... string) {
        for (String s : string) {
            sql.append(s);
            sql.append(" ");
        }
        return this;
    }

    public void addValues(List<Object> value) {
        this.values.addAll(value);
    }

    public List<Object> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return sql.toString();
    }
}
