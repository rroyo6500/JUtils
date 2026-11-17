package net.rroyo.jutils.utils.bbdd.generator;

import net.rroyo.jutils.utils.bbdd.Syntax;

public class Limit extends SQLSyntax{

    public Limit(SQLBuilder sqlBuilder, int limit) {
        super(sqlBuilder);

        sqlBuilder.append(syntax(), String.valueOf(limit));
    }

    @Override
    protected String syntax() {
        return Syntax.LIMIT;
    }
}
