package net.rroyo.jutils.utils.bbdd.generator;

import net.rroyo.jutils.utils.bbdd.SQL;
import net.rroyo.jutils.utils.logging.Log;

import java.sql.PreparedStatement;
import java.sql.Statement;

public abstract class SQLSyntax {

    protected final SQLBuilder sqlBuilder;

    public SQLSyntax(SQLBuilder sqlBuilder, boolean appendSyntax) {
        this.sqlBuilder = sqlBuilder;
        if (appendSyntax) sqlBuilder.append(syntax());
    }

    public SQLSyntax(SQLBuilder sqlBuilder) {
        this(sqlBuilder, true);
    }

    protected abstract String syntax();

    protected Class<? extends Statement> getStatementClassType() {
        return PreparedStatement.class;
    }

    public SQL build() {
        Log.info("Generated SQL: " + sqlBuilder.toString());
        return new SQL(sqlBuilder.toString(), sqlBuilder.getValues(), getStatementClassType());
    }

}
