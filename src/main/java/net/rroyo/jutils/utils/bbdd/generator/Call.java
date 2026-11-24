package net.rroyo.jutils.utils.bbdd.generator;

import net.rroyo.jutils.utils.bbdd.SQL;
import net.rroyo.jutils.utils.bbdd.Syntax;
import net.rroyo.jutils.utils.bbdd.data.Procedure;
import net.rroyo.jutils.utils.bbdd.data.value.InOutValue;
import net.rroyo.jutils.utils.bbdd.data.value.OutValue;
import net.rroyo.jutils.utils.bbdd.data.value.Value;

import java.sql.CallableStatement;
import java.sql.Statement;
import java.util.List;

public class Call extends SQLSyntax{

    private final Procedure procedure;

    public Call(SQLBuilder sqlBuilder, Procedure procedure) {
        super(sqlBuilder);

        this.procedure = procedure;
    }

    public Call inValue(Object value) {
        return inValue(new Value(value));
    }

    public Call inValue(Value value) {
        procedure.addValueGetter();
        sqlBuilder.addValues(List.of(value));
        return this;
    }

    public Call outValue(int type) {
        return outValue(new OutValue(type));
    }

    public Call outValue(OutValue value) {
        procedure.addValueGetter();
        sqlBuilder.addValues(List.of(value));
        return this;
    }

    public Call inoutValue(Object value, int type) {
        return inoutValue(new InOutValue(value, type));
    }

    public Call inoutValue(InOutValue value) {
        procedure.addValueGetter();
        sqlBuilder.addValues(List.of(value));
        return this;
    }

    @Override
    protected String syntax() {
        return Syntax.CALL;
    }

    @Override
    protected Class<? extends Statement> getStatementClassType() {
        return CallableStatement.class;
    }

    @Override
    public SQL build() {
        sqlBuilder.append(procedure.toString());
        return super.build();
    }

}
