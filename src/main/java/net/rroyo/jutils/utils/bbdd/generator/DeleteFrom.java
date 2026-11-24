package net.rroyo.jutils.utils.bbdd.generator;

import net.rroyo.jutils.utils.bbdd.Syntax;
import net.rroyo.jutils.utils.bbdd.data.table.Table;

public class DeleteFrom extends SQLSyntax {

    protected DeleteFrom(SQLBuilder sqlBuilder, Table table) {
        super(sqlBuilder);

        sqlBuilder.append(table.toString());
    }

    @Override
    protected String syntax() {
        return Syntax.DELETE;
    }

    public Where where(Condition condition) {
        return new Where(sqlBuilder, condition);
    }

}
