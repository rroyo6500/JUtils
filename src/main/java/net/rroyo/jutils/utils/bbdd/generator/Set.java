package net.rroyo.jutils.utils.bbdd.generator;

import net.rroyo.jutils.utils.bbdd.Syntax;
import net.rroyo.jutils.utils.bbdd.data.value.Value;
import net.rroyo.jutils.utils.bbdd.data.field.Field;

import java.util.List;

public class Set extends SQLSyntax{

    public Set(SQLBuilder sqlBuilder) {
        super(sqlBuilder);
    }

    @Override
    protected String syntax() {
        return Syntax.SET;
    }

    public Set field(Field field, Object newValue) {
        return field(field, new Value(newValue));
    }

    public Set field(Field field, Value newValue) {
        sqlBuilder.append(field.toString(), "= ?");
        sqlBuilder.addValues(List.of(newValue));
        return this;
    }

    public Where where(Condition condition) {
        return new Where(sqlBuilder, condition);
    }

}
