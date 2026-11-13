import net.rroyo.jutils.enums.bbdd.OrderBy;
import net.rroyo.jutils.utils.bbdd.SQL;
import net.rroyo.jutils.utils.bbdd.data.Condition;

import java.sql.SQLException;

import static net.rroyo.jutils.utils.bbdd.generator.SQLGenerator.*;

public class SandBox {

    public static void main(String[] args) throws SQLException {

        SQL sql = sql()
                .select(
                        field("a"),
                        field("b"),
                        field("c"),
                        field("d")
                )
                .from(table("TestTable"))
                .innerJoin(table("Test2"))
                .on(Condition.equals(field("f1"), field("f2")))
                .where(Condition.not(Condition.equals(field("f1"))))
                .groupBy(
                        field("a"),
                        field("b")
                )
                .orderBy(
                        field("a"),
                        field("b", OrderBy.ASC),
                        field("c", OrderBy.DESC)
                )
                .build();

        System.out.println(sql);

    }

}
