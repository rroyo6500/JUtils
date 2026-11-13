package net.rroyo.jutils.utils.bbdd;

public class SQL {

    private final String sql;

    public SQL(String sql) {
        this.sql = sql;
    }

    public void execute() {

    }

    @Override
    public String toString() {
        return sql;
    }
}
