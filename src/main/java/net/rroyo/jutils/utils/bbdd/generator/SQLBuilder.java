package net.rroyo.jutils.utils.bbdd.generator;

class SQLBuilder {

    private final StringBuilder sql = new StringBuilder();

    public SQLBuilder() {
    }

    public SQLBuilder append(String... string) {
        for (String s : string) {
            sql.append(s);
            sql.append(" ");
        }
        return this;
    }

    @Override
    public String toString() {
        return sql.toString();
    }
}
