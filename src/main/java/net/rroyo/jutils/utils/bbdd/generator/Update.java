package net.rroyo.jutils.utils.bbdd.generator;

import net.rroyo.jutils.utils.bbdd.Syntax;
import net.rroyo.jutils.utils.bbdd.data.table.Table;

public class Update extends SQLSyntax{

    public Update(SQLBuilder sqlBuilder, Table table) {
        super(sqlBuilder);

        sqlBuilder.append(table.toString());
    }

    @Override
    protected String syntax() {
        return Syntax.UPDATE;
    }

    public Set set() {
        return new Set(sqlBuilder);
    }

}
