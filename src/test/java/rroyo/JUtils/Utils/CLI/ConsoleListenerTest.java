package rroyo.JUtils.Utils.CLI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import rroyo.JUtils.Anotations.CLI.JCommand;
import rroyo.JUtils.Anotations.CLI.JOption;
import rroyo.JUtils.Enums.CLI.DataTypes;
import rroyo.JUtils.Utils.Logging.LoggerAux;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleListenerTest {

    private ConsoleListener listener;
    private MockCommandProvider provider;

    /**
     * Provider de comandos falso para capturar ejecuciones.
     */
    static class MockCommandProvider {
        String lastString = "";
        int lastInt = 0;
        boolean lastBool = false;
        String[] lastArgs = null;
        boolean executed = false;

        @JCommand(name = "greet", description = "Test string")
        public void greet(@JOption(name = "user", type = DataTypes.STRING) String user) {
            this.executed = true;
            this.lastString = user;
        }

        @JCommand(name = "sum", description = "Test math")
        public void sum(
                @JOption(name = "v", type = DataTypes.INT) int v,
                @JOption(name = "m", type = DataTypes.INT, defaultValue = "1") int m) {
            this.executed = true;
            this.lastInt = v + m;
        }

        @JCommand(name = "toggle", description = "Test boolean")
        public void toggle(@JOption(name = "active", type = DataTypes.BOOLEAN) boolean active) {
            this.executed = true;
            this.lastBool = active;
        }

        @JCommand(name = "extra", description = "Test raw args")
        public void extra(String[] args) {
            this.executed = true;
            this.lastArgs = args;
        }
    }

    @BeforeEach
    void setUp() {
        LoggerAux.setConsoleOutputEnabled(false);
        provider = new MockCommandProvider();
        listener = new ConsoleListener(provider);
    }

    @Nested
    @DisplayName("Tests de Parsing y Ejecución")
    class ExecutionTests {

        @Test
        @DisplayName("Debe ejecutar un comando simple con un argumento String")
        void shouldExecuteStringCommand() {
            listener.processInput("greet -user Gemini");
            assertTrue(provider.executed);
            assertEquals("Gemini", provider.lastString);
        }

        @Test
        @DisplayName("Debe manejar valores por defecto cuando no se pasa la opción")
        void shouldHandleDefaultValues() {
            listener.processInput("sum -v 10"); // m por defecto es 1
            assertTrue(provider.executed);
            assertEquals(11, provider.lastInt);
        }

        @Test
        @DisplayName("Debe sobrescribir el valor por defecto si se provee la opción")
        void shouldOverrideDefaultValues() {
            listener.processInput("sum -v 10 -m 5");
            assertEquals(15, provider.lastInt);
        }

        @Test
        @DisplayName("Debe manejar flags booleanos (presente = true)")
        void shouldHandleBooleanFlags() {
            listener.processInput("toggle -active");
            assertTrue(provider.lastBool);
        }

        @Test
        @DisplayName("Debe capturar argumentos sin procesar (String[])")
        void shouldCaptureRawArguments() {
            listener.processInput("extra arg1 arg2 \"argumento con espacios\"");
            assertNotNull(provider.lastArgs);
            assertEquals(3, provider.lastArgs.length);
            assertEquals("argumento con espacios", provider.lastArgs[2]);
        }
    }

    @Nested
    @DisplayName("Tests de Estado y Ciclo de Vida")
    class LifecycleTests {

        @Test
        @DisplayName("El estado inicial debe ser running = false")
        void initialStateShouldBeStopped() throws Exception {
            assertFalse(getRunningField());
        }

        @Test
        @DisplayName("El método start() debe activar el flag running")
        void startShouldSetRunningToTrue() throws Exception {
            listener.start();
            assertTrue(getRunningField());
        }

        @Test
        @DisplayName("El comando 'exit' debe detener el listener")
        void exitCommandShouldStopListener() throws Exception {
            listener.start();
            listener.processInput("exit");
            assertFalse(getRunningField());
        }

        @Test
        @DisplayName("El prompt debe actualizarse correctamente")
        void shouldUpdatePrompt() {
            listener.setPrompt("admin# ");
            assertEquals("admin# ", listener.getPrompt());
        }
    }

    @Nested
    @DisplayName("Tests de Robustez")
    class RobustnessTests {

        @Test
        @DisplayName("No debe fallar con entradas vacías o espacios")
        void shouldIgnoreEmptyInput() {
            assertDoesNotThrow(() -> {
                listener.processInput("");
                listener.processInput("   ");
            });
            assertFalse(provider.executed);
        }

        @Test
        @DisplayName("Debe ser insensible a mayúsculas en el nombre del comando")
        void commandNamesShouldBeCaseInsensitive() {
            listener.processInput("GREET -user Test");
            assertTrue(provider.executed);
            assertEquals("Test", provider.lastString);
        }
    }

    /**
     * Utilidad para leer el campo privado 'running' mediante reflexión.
     */
    private boolean getRunningField() throws Exception {
        Field field = ConsoleListener.class.getDeclaredField("running");
        field.setAccessible(true);
        return (boolean) field.get(listener);
    }
}