package net.rroyo.jutils.utils.bbdd.data;

import net.rroyo.jutils.enums.bbdd.OrderBy;

public class OrderedField extends Field{

    private final OrderBy order;

    protected OrderedField(String fieldName, OrderBy order) {
        super(fieldName);
        this.order = order;
    }

    @Override
    public String toString() {
        return super.toString() + " " + order;
    }
}
