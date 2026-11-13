import net.rroyo.jutils.enums.bbdd.OrderBy;
import net.rroyo.jutils.utils.bbdd.ConnectionManager;
import net.rroyo.jutils.utils.bbdd.SQL;

import static net.rroyo.jutils.utils.bbdd.generator.SQLGenerator.*;

public class SandBox {

    public static void main(String[] args) {
        ConnectionManager.setDatabase("homecontroldb");
        ConnectionManager.setUser("root");
        ConnectionManager.setPassword("");

        String name = "Robert";
        int age = 18;

        try (SQL sql = sql()
                .select(
                        field("name"),
                        field("age"),
                        field("email")
                )
                .from(table("users"))
                .innerJoin(table("departments"))
                .on(field("users.department_id").equal().field("departments.id"))
                .where(field("users.id").equal().subQuery(
                        sql().select().from(table("departments")).build()
                ))
                .and(field("users.id").equal().value(10))
                //.and(isNotNull(field("users.email")))
                .orderBy(field("users.name", OrderBy.ASC))
                .build()
        ) {

            String finalSQL = sql.getPreparedStatement().toString();

            System.out.println();
            System.out.println(finalSQL);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }

}
