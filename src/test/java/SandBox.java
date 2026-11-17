import net.rroyo.jutils.utils.bbdd.ConnectionManager;
import net.rroyo.jutils.utils.bbdd.ResultTable;
import net.rroyo.jutils.utils.bbdd.SQL;
import net.rroyo.jutils.utils.bbdd.data.field.Field;
import net.rroyo.jutils.utils.bbdd.data.table.Table;
import net.rroyo.jutils.utils.bbdd.generator.SQLGenerator;

import java.sql.ResultSet;
import java.sql.SQLException;

import static net.rroyo.jutils.utils.bbdd.generator.SQLGenerator.table;

public class SandBox {

    private static final Table COCHES = table("coches");

    private static final Field ID = COCHES.field("id");
    private static final Field MATRICULA = COCHES.field("matricula");
    private static final Field MARCA = COCHES.field("marca");
    private static final Field MODELO = COCHES.field("modelo");
    private static final Field FECHA_MATRICULACION = COCHES.field("fecha_matriculacion");

    public static void main(String[] args) throws SQLException {
        ConnectionManager.setDatabase("vehiculos");
        ConnectionManager.setUser("root");
        ConnectionManager.setPassword("");

        SQL sql = SQLGenerator.sql()
                .select().from(COCHES)
                .build()
                ;

        System.out.println(COCHES.getColNum());
        ResultTable rt = new ResultTable(sql.executeQuery());
        System.out.println(rt);

    }

}
