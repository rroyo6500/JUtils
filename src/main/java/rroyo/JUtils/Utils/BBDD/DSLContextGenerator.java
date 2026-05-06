package rroyo.JUtils.Utils.BBDD;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import rroyo.JUtils.Utils.Core.Validator;
import rroyo.JUtils.Utils.Logging.LoggerAux;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for generating and configuring jOOQ {@link DSLContext} objects.
 * Provides static methods to abstract database context creation using different
 * combinations of credentials, connections, and SQL dialects.
 *
 * @author rroyo
 */
public final class DSLContextGenerator {

    private DSLContextGenerator() {}

    /** Default SQL dialect used when none is explicitly specified. */
    public static SQLDialect defaultDialect = SQLDialect.DEFAULT;

    /**
     * Creates a {@link DSLContext} from connection credentials using the default dialect.
     *
     * @param url    JDBC database connection URL.
     * @param user   Database user.
     * @param passwd User password.
     * @return A configured {@link DSLContext} instance.
     * @throws SQLException If an error occurs trying to establish the database connection.
     */
    public static DSLContext createDSLContext (String url, String user, String passwd) throws SQLException {
        return createDSLContext(url, user, passwd, defaultDialect);
    }

    /**
     * Creates a {@link DSLContext} from connection credentials and a specific dialect.
     *
     * @param url     JDBC database connection URL.
     * @param user    Database user.
     * @param passwd  User password.
     * @param dialect Specific SQL dialect (e.g., POSTGRES, MYSQL).
     * @return A configured {@link DSLContext} instance.
     * @throws SQLException If an error occurs trying to establish the database connection.
     */
    public static DSLContext createDSLContext (String url, String user, String passwd, SQLDialect dialect) throws SQLException {
        Validator.notBlank(url, "JDBC URL cannot be empty");
        Validator.notBlank(user, "Database user cannot be empty");
        Connection con = DriverManager.getConnection(url, user, passwd);
        return createDSLContext(con, dialect);
    }

    /**
     * Creates a {@link DSLContext} using an existing JDBC connection and default dialect.
     *
     * @param connection Active {@link Connection} to the database.
     * @return A configured {@link DSLContext} instance.
     */
    public static DSLContext createDSLContext (Connection connection) throws SQLException {
        return createDSLContext(connection, defaultDialect);
    }

    /**
     * Creates a {@link DSLContext} using an existing JDBC connection and specific dialect.
     *
     * @param connection Active {@link Connection} to the database.
     * @param dialect    Specific SQL dialect.
     * @return A configured {@link DSLContext} instance.
     */
    public static DSLContext createDSLContext (Connection connection, SQLDialect dialect) throws SQLException {
        Validator.notNull(connection, "Database Connection cannot be null");
        assert connection != null;
        LoggerAux.info("Connection established [" + connection.getMetaData().getURL() + "]");
        return DSL.using(connection, dialect);
    }
}