import net.rroyo.jutils.enums.bbdd.SQL;

public class SandBox {

    public static void main(String[] args) {

        String test = SQL.SELECT.toString();
        String test2 = SQL.ORDER_BY.toString();

        System.out.println(test);
        System.out.println(test2);

    }

}
