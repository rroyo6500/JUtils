import net.rroyo.jutils.utils.bbdd.ConnectionManager;
import net.rroyo.jutils.utils.bbdd.ResultTable;
import net.rroyo.jutils.utils.bbdd.SQL;
import net.rroyo.jutils.utils.bbdd.data.field.Field;
import net.rroyo.jutils.utils.bbdd.data.table.Table;
import net.rroyo.jutils.utils.bbdd.generator.*;
import net.rroyo.jutils.utils.bbdd.generator.From;

import java.sql.SQLException;

import static net.rroyo.jutils.utils.bbdd.generator.SQLGenerator.*;

public class SandBox {

    public static void main(String[] args) {
        ConnectionManager.setDatabase("homecontroldb");
        ConnectionManager.setUser("root");
        ConnectionManager.setPassword("");

        try (SQL sql = ScriptsSQL.updateMarkets
                .field(HomeContolDB.M_MARKET_NAME, "Amazon")
                .where(HomeContolDB.M_MARKET_ID.eq(12))
                .build()
        ) {

            System.out.println();
            System.out.println(sql);

            System.out.println(sql.executeUpdate());

            System.out.println(new ResultTable(ScriptsSQL.selectFromMarkets.build().executeQuery()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}

class ScriptsSQL {

    public static From selectFromMarkets;
    public static Values insertIntoMarkets;
    public static Set updateMarkets;

    static {
        try {

            selectFromMarkets = sql()
                    .select().from(HomeContolDB.MARKETS);

            insertIntoMarkets = sql()
                    .insertInto(HomeContolDB.MARKETS,
                            HomeContolDB.M_MARKET_NAME, HomeContolDB.M_ABBREVIATION)
                    .values("Merc1", "m1")
                    .values("Merc2", "m2")
                    .values("Merc3", "m3")
                    .values("Merc4", "m4")
            ;

            updateMarkets = sql()
                    .update(HomeContolDB.MARKETS)
                    .set();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

class HomeContolDB {

    public static final Table PRODUCTS = table("products");

    public static final Field P_PRODUCT_ID = PRODUCTS.field("PRODUCT_ID");
    public static final Field P_DISPLAY_NAME = PRODUCTS.field("DISPLAY_NAME");
    public static final Field P_UNIT_PRICE = PRODUCTS.field("UNIT_PRICE");
    public static final Field P_THUMBNAIL = PRODUCTS.field("THUMBNAIL");
    public static final Field P_MARKET_ID = PRODUCTS.field("MARKET_ID");

    public static final Table MARKETS = table("markets");

    public static final Field M_MARKET_ID = MARKETS.field("MARKET_ID");
    public static final Field M_MARKET_NAME = MARKETS.field("MARKET_NAME");
    public static final Field M_ABBREVIATION = MARKETS.field("ABBREVIATION");

}
