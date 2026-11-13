package net.rroyo.jutils.utils.bbdd.generator;

import net.rroyo.jutils.utils.bbdd.Syntax;
import net.rroyo.jutils.utils.bbdd.data.field.Field;
import net.rroyo.jutils.utils.bbdd.data.table.Table;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Select extends SQLSyntax{

    protected Select(SQLBuilder sqlBuilder, Field... field) {
        super(sqlBuilder);

        if (field.length == 0) sqlBuilder.append(syntax(), "*");
        else {
            String fieldsString = Arrays.stream(field)
                    .map(Field::toString)
                    .collect(Collectors.joining(", "));
            sqlBuilder.append(syntax(), fieldsString);
        }

    }

    @Override
    protected String syntax() {
        return Syntax.SELECT;
    }

    public From from(Table table) {
        return new From(sqlBuilder, table);
    }

}
