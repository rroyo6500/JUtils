package net.rroyo.jutils.utils.bbdd.generator;

import net.rroyo.jutils.utils.bbdd.data.Field;
import net.rroyo.jutils.utils.bbdd.data.Table;

public class Select extends SQLSyntax{

    protected Select(SQLBuilder sqlBuilder, Field... field) {
        super(sqlBuilder);

        StringBuilder f = new StringBuilder();
        if (field.length == 0) f.append("*");
        else if (field.length == 1) f.append(field[0]);
        else for (int i = 0; i < field.length; i++) {
            f.append(field[i]);
            if (i != field.length-1) f.append(", ");
        }

        sqlBuilder.append(syntax(), f.toString());
    }

    @Override
    protected String syntax() {
        return "SELECT";
    }

    public From from(Table table) {
        return new From(sqlBuilder, table);
    }

}
