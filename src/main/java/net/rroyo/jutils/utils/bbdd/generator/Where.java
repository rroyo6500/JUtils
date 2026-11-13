package net.rroyo.jutils.utils.bbdd.generator;

import net.rroyo.jutils.utils.bbdd.data.Condition;
import net.rroyo.jutils.utils.bbdd.data.Field;

import java.sql.SQLException;

public class Where extends SQLSyntax{

    protected Where(SQLBuilder sqlBuilder, Condition condition) {
        super(sqlBuilder);

        sqlBuilder.append(syntax(), condition.toString());
    }

    @Override
    protected String syntax() {
        return "WHERE";
    }

    public Where and(Condition condition) {
        sqlBuilder.append("AND", condition.toString());
        return this;
    }

    public Where or(Condition condition) {
        sqlBuilder.append("OR", condition.toString());
        return this;
    }

    public GroupBy groupBy(Field... field) throws SQLException {
        return new GroupBy(sqlBuilder, field);
    }

    public OrderBy orderBy(Field... field) throws SQLException {
        return new OrderBy(sqlBuilder, field);
    }
}
