package net.rroyo.jutils.utils.bbdd.generator;

import net.rroyo.jutils.enums.bbdd.SQLInstruction;

import java.util.ArrayList;
import java.util.List;

class SQLBuilder {

    private final StringBuilder sql = new StringBuilder();
    private final SQLInstruction instruction;
    private List<Object> values;

    public SQLBuilder(SQLInstruction instruction) {
        this.instruction = instruction;
    }

    public SQLBuilder append(String... string) {
        for (String s : string) {
            sql.append(s);
            sql.append(" ");
        }
        return this;
    }

    public void addValues(List<Object> value) {
        this.values = value;
    }

    public SQLInstruction getInstruction() {
        return instruction;
    }

    public List<Object> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return sql.toString();
    }
}
