package net.rroyo.jutils.utils.bbdd.generator;

import net.rroyo.jutils.utils.bbdd.SQL;
import net.rroyo.jutils.utils.logging.Log;

public abstract class SQLSyntax {

    protected final SQLBuilder sqlBuilder;

    public SQLSyntax(SQLBuilder sqlBuilder) {
        this.sqlBuilder = sqlBuilder;
    }

    protected abstract String syntax();

    public SQL build() {
        Log.info("Generated SQL: " + sqlBuilder.toString());
        return new SQL(sqlBuilder.toString(), sqlBuilder.getValues());
    }

}
