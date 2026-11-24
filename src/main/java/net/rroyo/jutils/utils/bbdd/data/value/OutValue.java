package net.rroyo.jutils.utils.bbdd.data.value;

import net.rroyo.jutils.enums.bbdd.ValueType;

public class OutValue extends Value{

    public OutValue(int type) {
        super(type, ValueType.OUT);
    }

}
