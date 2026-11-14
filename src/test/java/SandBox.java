import net.rroyo.jutils.utils.bbdd.ConnectionManager;
import net.rroyo.jutils.utils.bbdd.ResultTable;
import net.rroyo.jutils.utils.bbdd.SQL;
import net.rroyo.jutils.utils.bbdd.data.field.Field;
import net.rroyo.jutils.utils.bbdd.data.table.Table;
import net.rroyo.jutils.utils.bbdd.generator.SQLGenerator;

import java.sql.SQLException;

public class SandBox {

    private static final Table markets = new Table("markets");
    private static final Field marketId = markets.field("MARKET_ID");
    private static final Field marketName = markets.field("MARKET_NAME");
    private static final Field abbreviation = markets.field("ABBREVIATION");

    public static void main(String[] args) throws SQLException {
        ConnectionManager.setDatabase("homecontroldb");
        ConnectionManager.setUser("root");
        ConnectionManager.setPassword("");

        SQL select = SQLGenerator.sql()
                .select()
                .from(markets)
                .build()
                ;

        SQL insert = SQLGenerator.sql()
                .insertInto(markets, marketName, abbreviation)
                .values("", "")º
                .build()
                ;

        ResultTable rt = new ResultTable(select.executeQuery());

        System.out.println(rt);

    }

}
