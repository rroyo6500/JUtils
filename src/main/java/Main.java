import rroyo.JUtils.Utils.Logging.LoggerAux;

public class Main {

    static void main(String[] args) {

        LoggerAux.setLogDirectory("C:\\Users\\rober\\Downloads");

        try {
            LoggerAux.info("Test INFO 1");
            LoggerAux.warn("Test WARN 2");
            Thread.sleep(1000);
            LoggerAux.info("Test INFO 3");
            Thread.sleep(1000);
            LoggerAux.error("Test ERROR 4");
            Thread.sleep(1000);
            LoggerAux.error("Test ERROR 5");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}
