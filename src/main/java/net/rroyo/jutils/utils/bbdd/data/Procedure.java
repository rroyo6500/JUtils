package net.rroyo.jutils.utils.bbdd.data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Procedure {

    private final String name;
    private int valueCount = 0;

    public Procedure(String name) {
        this.name = name;
    }

    public void addValueGetter() {
        this.valueCount++;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < valueCount; i++) {
            s.append('?');
            if (i != valueCount-1) s.append(", ");
        }
        return name + "(" + s + ")";
    }
}
