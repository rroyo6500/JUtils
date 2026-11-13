package net.rroyo.jutils.utils.bbdd.generator;

import net.rroyo.jutils.enums.bbdd.SQLJoin;
import net.rroyo.jutils.utils.bbdd.Syntax;
import net.rroyo.jutils.utils.bbdd.condition.Condition;
import net.rroyo.jutils.utils.bbdd.data.field.Field;
import net.rroyo.jutils.utils.bbdd.data.table.Table;

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
        if (joinType == SQLJoin.NAN) return Syntax.JOIN;
        return joinType + " " + Syntax.JOIN;
    }

    public Join on(Condition condition) {
        sqlBuilder.append(Syntax.ON, condition.toString());
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
