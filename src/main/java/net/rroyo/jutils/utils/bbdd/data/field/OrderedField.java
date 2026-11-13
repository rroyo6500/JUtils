package net.rroyo.jutils.utils.bbdd.data.field;

import net.rroyo.jutils.enums.bbdd.OrderBy;

public class OrderedField extends Field{

    private final OrderBy order;

    public OrderedField(String fieldName, OrderBy order) {
        super(fieldName);
        this.order = order;
    }

    @Override
    public String toString() {
        return super.toString() + " " + order;
    }
}
