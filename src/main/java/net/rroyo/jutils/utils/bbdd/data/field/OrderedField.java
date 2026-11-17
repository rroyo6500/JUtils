package net.rroyo.jutils.utils.bbdd.data.field;

import net.rroyo.jutils.enums.bbdd.OrderBy;
import net.rroyo.jutils.utils.bbdd.data.table.Table;

public class OrderedField extends Field{

    private final OrderBy order;

    public OrderedField(Table table, String fieldName, OrderBy order) {
        super(table, fieldName);
        this.order = order;
    }

    @Override
    public String toString() {
        return super.toString() + " " + order;
    }
}
