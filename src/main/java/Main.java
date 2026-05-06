import rroyo.JUtils.Utils.Console.CSystem;
import rroyo.JUtils.Utils.Logging.Benchmark;
import rroyo.JUtils.Utils.Logging.LoggerAux;

public class Main {

    public static void main(String[] args) {

        LoggerAux.setLogDirectory("C:\\Users\\r.royo\\Downloads");

        LoggerAux.info("Hello World!");
        LoggerAux.debug("Hello World!");
        LoggerAux.warn("Hello World!");
        LoggerAux.error("Hello World!");

    }

}
