package net.rroyo.jutils.utils.bbdd.generator;

import net.rroyo.jutils.utils.bbdd.Syntax;
import net.rroyo.jutils.utils.bbdd.data.value.Value;
import net.rroyo.jutils.utils.bbdd.data.field.Field;

import java.sql.SQLException;
import java.util.List;

public class Where extends SQLSyntax {

    protected Where(SQLBuilder sqlBuilder, Condition condition) {
        super(sqlBuilder, false);

        init(syntax(), condition);
    }

    private void init(String syntax, Condition condition) {
        sqlBuilder.append(syntax, condition.toString());
        List<Value> values = condition.getValues();
        if (values != null && !values.isEmpty()) sqlBuilder.addValues(values);
    }

    @Override
    protected String syntax() {
        return Syntax.WHERE;
    }

    public Where and(Condition condition) {
        init(Syntax.AND, condition);
        return this;
    }

    public Where or(Condition condition) {
        init(Syntax.OR, condition);
        return this;
    }

    public GroupBy groupBy(Field... field) throws SQLException {
        return new GroupBy(sqlBuilder, field);
    }

    public OrderBy orderBy(Field... field) throws SQLException {
        return new OrderBy(sqlBuilder, field);
    }

    public Limit limit(int limit) {
        return new Limit(sqlBuilder, limit);
    }

}
