package net.rroyo.jutils.utils.bbdd;

public class SQLGenerator {

    private static final String SELECT = " SELECT %s ";

    private static final String FROM = " FROM %s ";

    private static final String WHEN = " WHEN %s ";

    private static final String C_EQUALS = " %s = %s ";

    private static final String J_INNER = " INNER ";
    private static final String J_LEFT = " LEFT ";
    private static final String J_RIGHT = " RIGHT ";
    private static final String JOIN = " JOIN %s ";

    private static final String ON = " ON " + C_EQUALS;



}
