package net.rroyo.jutils.utils.bbdd.data.value;

import net.rroyo.jutils.enums.bbdd.ValueType;

public class InOutValue extends Value{

    public InOutValue(Object value, int type) {
        super(value, type, ValueType.INOUT);
    }

}
