package net.rroyo.jutils.utils.bbdd.generator;

import net.rroyo.jutils.enums.bbdd.SQLJoin;
import net.rroyo.jutils.utils.bbdd.data.Condition;
import net.rroyo.jutils.utils.bbdd.data.Field;
import net.rroyo.jutils.utils.bbdd.data.Table;

import java.sql.SQLException;

public class Join extends SQLSyntax{

    private final SQLJoin joinType;

    protected Join(SQLBuilder sqlBuilder, SQLJoin joinType, Table table) {
        super(sqlBuilder);
        this.joinType = joinType;

        sqlBuilder.append(syntax(), table.toString());
    }

    @Override
    protected String syntax() {
        if (joinType == SQLJoin.NAN) return "JOIN";
        return joinType + " JOIN";
    }

    public Join on(Condition condition) {
        sqlBuilder.append("ON", condition.toString());
        return this;
    }

    public Where where(Condition condition) {
        return new Where(sqlBuilder, condition);
    }

    public GroupBy groupBy(Field... field) throws SQLException {
        return new GroupBy(sqlBuilder, field);
    }

    public OrderBy orderBy(Field... field) throws SQLException {
        return new OrderBy(sqlBuilder, field);
    }

}
