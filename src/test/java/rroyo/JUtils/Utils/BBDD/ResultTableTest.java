package rroyo.JUtils.Utils.BBDD;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rroyo.JUtils.Utils.Logging.LoggerAux;

import java.lang.reflect.Proxy;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResultTableTest {

    @BeforeEach
    void silenceLogger() {
        LoggerAux.setConsoleOutputEnabled(false);
    }

    @Test
    void resultTableReadsRowsPreservesColumnOrderAndClosesResultSet() throws SQLException {
        FakeResultSet fake = new FakeResultSet(
                List.of("id", "name"),
                List.of(List.of(1, "Alice"), List.of(2, "Bob"))
        );

        ResultTable table = new ResultTable(fake.proxy(), true);

        assertEquals(List.of(1, 2), table.getTable().get("id"));
        assertEquals(List.of("Alice", "Bob"), table.getTable().get("name"));
        assertEquals("Alice", table.getValue("name", 1));
        assertEquals(2, table.getValue("id", 2));
        assertTrue(fake.closed);
        assertTrue(table.toString().contains("Alice"));
    }

    @Test
    void getValueValidatesColumnAndOneBasedRowIndex() throws SQLException {
        ResultTable table = new ResultTable(new FakeResultSet(
                List.of("id"),
                List.of(List.of(1))
        ).proxy(), false);

        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> table.getValue("", 1)),
                () -> assertThrows(IllegalArgumentException.class, () -> table.getValue("missing", 1)),
                () -> assertThrows(IllegalArgumentException.class, () -> table.getValue("id", 0)),
                () -> assertThrows(IllegalArgumentException.class, () -> table.getValue("id", 2))
        );
    }

    @Test
    void constructorRequiresScrollInsensitiveResultSet() {
        FakeResultSet fake = new FakeResultSet(List.of("id"), List.of(List.of(1)));
        fake.type = ResultSet.TYPE_FORWARD_ONLY;

        assertThrows(IllegalArgumentException.class, () -> new ResultTable(fake.proxy()));
    }

    private static final class FakeResultSet {
        private final List<String> columns;
        private final List<List<Object>> rows;
        private int cursor = -1;
        private int type = ResultSet.TYPE_SCROLL_INSENSITIVE;
        private boolean closed;

        private FakeResultSet(List<String> columns, List<List<Object>> rows) {
            this.columns = columns;
            this.rows = rows;
        }

        private ResultSet proxy() {
            return (ResultSet) Proxy.newProxyInstance(
                    ResultTableTest.class.getClassLoader(),
                    new Class<?>[]{ResultSet.class},
                    (proxy, method, args) -> switch (method.getName()) {
                        case "getType" -> type;
                        case "beforeFirst" -> {
                            cursor = -1;
                            yield null;
                        }
                        case "getMetaData" -> metaDataProxy();
                        case "next" -> ++cursor < rows.size();
                        case "getObject" -> rows.get(cursor).get(columns.indexOf((String) args[0]));
                        case "close" -> {
                            closed = true;
                            yield null;
                        }
                        default -> throw new UnsupportedOperationException(method.getName());
                    }
            );
        }

        private ResultSetMetaData metaDataProxy() {
            return (ResultSetMetaData) Proxy.newProxyInstance(
                    ResultTableTest.class.getClassLoader(),
                    new Class<?>[]{ResultSetMetaData.class},
                    (proxy, method, args) -> switch (method.getName()) {
                        case "getColumnCount" -> columns.size();
                        case "getColumnLabel" -> columns.get((Integer) args[0] - 1);
                        default -> throw new UnsupportedOperationException(method.getName());
                    }
            );
        }
    }
}
