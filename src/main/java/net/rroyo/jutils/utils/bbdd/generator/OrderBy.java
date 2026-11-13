package net.rroyo.jutils.utils.bbdd.generator;

import net.rroyo.jutils.utils.bbdd.Syntax;
import net.rroyo.jutils.utils.bbdd.data.field.Field;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class OrderBy extends SQLSyntax{

    protected OrderBy(SQLBuilder sqlBuilder, Field... field) throws SQLException {
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
        return Syntax.ORDER_BY;
    }
}
