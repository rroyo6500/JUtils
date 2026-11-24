package net.rroyo.jutils.utils.bbdd.data.value;

import net.rroyo.jutils.enums.bbdd.ValueType;

public class Value {

    private Object value;
    private int type;
    private ValueType valueType = ValueType.IN;

    public Value(Object value) {
        this.value = value;
    }

    protected Value(Object value, int type, ValueType valueType) {
        this.value = value;
        this.type = type;
        this.valueType = valueType;
    }

    protected Value(int type, ValueType valueType) {
        this.type = type;
        this.valueType = valueType;
    }

    public Object getValue() {
        return value;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public static Value[] toArray(Object... values) {
        Value[] v = new Value[values.length];
        for (int i = 0; i < values.length; i++) {
            v[i] = new Value(values[i]);
        }
        return v;
    }
}
