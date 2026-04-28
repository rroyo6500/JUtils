package rroyo.JUtils.Utils.BBDD;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.jooq.impl.DSL.table;

public class DSLContextGenerator {

    public static SQLDialect defaultDialect = SQLDialect.DEFAULT;

    public static DSLContext createDSLContext (String url, String user, String passwd) {
        return createDSLContext(url, user, passwd, defaultDialect);
    }

    public static DSLContext createDSLContext (String url, String user, String passwd, SQLDialect dialect) {
        Connection con;
        try {
            con = DriverManager.getConnection(url, user, passwd);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return createDSLContext(con, dialect);
    }

    public static DSLContext createDSLContext (Connection connection) {
        return createDSLContext(connection, defaultDialect);
    }

    public static DSLContext createDSLContext (Connection connection, SQLDialect dialect) {
        return DSL.using(connection, dialect);
    }
}
