package rroyo.JUtils.Utils.BBDD;

import rroyo.JUtils.Utils.Core.Validator;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

/**
 * Utility class that transforms a JDBC {@link ResultSet} into an in-memory data structure.
 * <p>
 * This class stores database results in a {@link LinkedHashMap} where keys represent
 * column names and values are lists of objects representing the rows. This allows
 * for disconnected data manipulation and easy console printing.
 * </p>
 *
 * @author rroyo
 */
public final class ResultTable {

    /**
     * Internal data structure representing the table.
     * Uses a LinkedHashMap to preserve the insertion order of columns.[cite: 16]
     */
    private final Map<String, ArrayList<Object>> table = new LinkedHashMap<>();

    /**
     * Formatted ASCCI table
     */
    private final String strTable;

    public ResultTable(ResultSet resultSet) throws SQLException {
        this(resultSet, true);
    }

    /**
     * Constructs a new ResultTable by processing an active {@link ResultSet}.
     * <p>
     * It extracts metadata to identify columns and iterates through all rows
     * to populate the internal map.
     * </p>
     *
     * @param resultSet The active database result set to process.
     * @throws SQLException If a database access error occurs.
     */
    public ResultTable(ResultSet resultSet, boolean closeResultSet) throws SQLException {
        Validator.assertTrue(resultSet.getType() == ResultSet.TYPE_SCROLL_INSENSITIVE,
                "ResultTable requires a ResultSet - 'TYPE_SCROLL_INSENSITIVE");

        resultSet.beforeFirst();

        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnCount = rsmd.getColumnCount();

        String[] colNames = new String[columnCount];
        for (int i = 1; i <= columnCount; i++) {
            colNames[i-1] = rsmd.getColumnLabel(i);
            table.put(colNames[i-1], new ArrayList<>());
        }

        while (resultSet.next()) {
            for (String colName : colNames) {
                table.get(colName).add(resultSet.getObject(colName));
            }
        }

        if (closeResultSet) resultSet.close();

        strTable = convertToString();
    }

    /**
     * Retrieves a specific value from the table based on column name and row index.
     *
     * @param colName The name of the column to look up.
     * @param row     The zero-based index of the row.
     * @return The object stored at the specified location.
     * @throws IllegalArgumentException if the column is blank or row index is out of bounds.
     */
    public Object getValue(String colName, int row) {
        Validator.notBlank(colName, "The column name cannot be blank");
        Validator.assertTrue(table.containsKey(colName), "The column " + colName + " does not exist");
        Validator.inRange(row, 1, table.get(colName).size(), "Row index out of range");
        return table.get(colName).get(row-1);
    }

    /**
     * Returns the underlying map structure of the table.
     *
     * @return A map where keys are column names and values are row data.
     */
    public Map<String, ArrayList<Object>> getTable() {
        return Collections.unmodifiableMap(table);
    }

    /**
     * Generates a formatted ASCII representation of the table.
     * <p>
     * Calculates dynamic column widths based on the longest content and
     * adds borders and headers for clear console visualization.
     * </p>
     *
     * @return A string containing the visual ASCII table.
     * @throws IllegalArgumentException if the table is empty.[cite: 14]
     */
    private String convertToString() {
        Validator.assertFalse(table.isEmpty(), "Cannot print an empty table");

        StringBuilder sb = new StringBuilder();
        List<String> keys = new ArrayList<>(table.keySet());

        Validator.assertFalse(keys.isEmpty(), "");
        int numRows = table.get(keys.getFirst()).size();

        Map<String, Integer> colWidths = new HashMap<>();
        keys.forEach(key -> {
            int maxWidth = key.length();
            for (Object obj : table.get(key)) {
                int valWidth = (obj != null) ? String.valueOf(obj).length() : 4;
                if (valWidth > maxWidth) maxWidth = valWidth;
            }
            colWidths.put(key, maxWidth + 2);
        });

        Runnable appendDivider = () -> {
            keys.forEach(key -> sb.append("+").repeat("-", colWidths.get(key)));
            sb.append("+\n");
        };

        appendDivider.run();
        keys.forEach(key -> sb.append(String.format("| %-" + (colWidths.get(key) - 1) + "s", key)));
        sb.append("|\n");
        appendDivider.run();

        if (numRows == 0) {
            keys.forEach(key -> sb.append(String.format("| %-" + (colWidths.get(key) - 1) + "s", "")));
            sb.append("|\n");
            appendDivider.run();
        } else {
            for (int i = 0; i < numRows; i++) {
                for (String key : keys) {
                    Object val = table.get(key).get(i);
                    String printVal = (val != null) ? val.toString() : "null";
                    sb.append(String.format("| %-" + (colWidths.get(key) - 1) + "s", printVal));
                }
                sb.append("|\n");
            }
            appendDivider.run();
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        return strTable;
    }
}