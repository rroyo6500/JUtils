package net.rroyo.jutils.utils.bbdd.generator;

import net.rroyo.jutils.enums.bbdd.SQLJoin;
import net.rroyo.jutils.utils.bbdd.data.Condition;
import net.rroyo.jutils.utils.bbdd.data.Field;
import net.rroyo.jutils.utils.bbdd.data.Table;

import java.sql.SQLException;

public class From extends SQLSyntax{

    protected From(SQLBuilder sqlBuilder, Table table) {
        super(sqlBuilder);

        sqlBuilder.append(syntax(), table.toString());
    }

    @Override
    protected String syntax() {
        return "FROM";
    }

    public Where where(Condition condition) {
        return new Where(sqlBuilder, condition);
    }

    public Join join(Table table) {
        return new Join(sqlBuilder, SQLJoin.NAN, table);
    }
    public Join innerJoin(Table table) {
        return new Join(sqlBuilder, SQLJoin.INNER, table);
    }
    public Join leftJoin(Table table) {
        return new Join(sqlBuilder, SQLJoin.LEFT, table);
    }
    public Join rightJoin(Table table) {
        return new Join(sqlBuilder, SQLJoin.RIGHT, table);
    }

    public GroupBy groupBy(Field... field) throws SQLException {
        return new GroupBy(sqlBuilder, field);
    }

    public OrderBy orderBy(Field... field) throws SQLException {
        return new OrderBy(sqlBuilder, field);
    }

}
