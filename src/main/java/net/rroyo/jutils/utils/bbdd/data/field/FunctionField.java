package net.rroyo.jutils.utils.bbdd.data.field;

import net.rroyo.jutils.utils.bbdd.data.table.Table;

public class FunctionField extends Field{

    public FunctionField(Field field, String function) {
        this(field.table, field.fieldName, function);
    }

    public FunctionField(Table table, String fieldName, String function) {
        super(table, fieldName);
        fieldStr = function + '(' + ((fieldName==null)?"*":fieldName) + ')';
    }

}
