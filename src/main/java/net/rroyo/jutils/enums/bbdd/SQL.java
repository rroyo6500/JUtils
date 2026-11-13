package net.rroyo.jutils.enums.bbdd;

public enum SQL {

    SELECT,
    UPDATE,
    INSERT_INTO, VALUES,
    DELETE,

    FROM,
    WHEN, AND, OR, NOT,
    JOIN, INNER, LEFT, RIGHT,
    ON,
    ORDER_BY, ASC, DESC,


    ;

    @Override
    public String toString() {
        return super.toString().replace("_", " ");
    }
}
