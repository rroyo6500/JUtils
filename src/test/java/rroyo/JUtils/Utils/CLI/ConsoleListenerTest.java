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

/**
 * Test class for ConsoleListener utility.
 * Verifies command parsing, execution, state management, and robustness of the console input listener.
 */
class ConsoleListenerTest {

    /** The console listener instance being tested. */
    private ConsoleListener listener;
    /** Mock command provider for capturing command executions. */
    private MockCommandProvider provider;

    /**
     * Mock command provider for capturing command executions and test data.
     * Used to verify that commands are parsed and executed with correct arguments.
     */
    static class MockCommandProvider {
        /** Stores the last string parameter received. */
        String lastString = "";
        /** Stores the last integer result from command execution. */
        int lastInt = 0;
        /** Stores the last boolean flag received. */
        boolean lastBool = false;
        /** Stores the last array of extra arguments received. */
        String[] lastArgs = null;
        /** Indicates whether a command was successfully executed. */
        boolean executed = false;

        /**
         * Test command: greets a user by name.
         * @param user the user name to greet.
         */
        @JCommand(name = "greet", description = "Test string")
        public void greet(@JOption(name = "user", type = DataTypes.STRING) String user) {
            this.executed = true;
            this.lastString = user;
        }

        /**
         * Test command: sums two integer values.
         * @param v the first integer value.
         * @param m the second integer value (defaults to 1).
         */
        @JCommand(name = "sum", description = "Test math")
        public void sum(
                @JOption(name = "v", type = DataTypes.INT) int v,
                @JOption(name = "m", type = DataTypes.INT, defaultValue = "1") int m) {
            this.executed = true;
            this.lastInt = v + m;
        }

        /**
         * Test command: toggles a boolean flag.
         * @param active the boolean state to set.
         */
        @JCommand(name = "toggle", description = "Test boolean")
        public void toggle(@JOption(name = "active", type = DataTypes.BOOLEAN) boolean active) {
            this.executed = true;
            this.lastBool = active;
        }

        /**
         * Test command: captures raw arguments without parsing.
         * @param args the raw command line arguments.
         */
        @JCommand(name = "extra", description = "Test raw args")
        public void extra(String[] args) {
            this.executed = true;
            this.lastArgs = args;
        }
    }

    /**
     * Initializes the test environment by disabling console output and creating test instances.
     */
    @BeforeEach
    void setUp() {
        LoggerAux.setConsoleOutputEnabled(false);
        provider = new MockCommandProvider();
        listener = new ConsoleListener(provider);
    }

    @Nested
    @DisplayName("Tests de Parsing y Ejecución")
    class ExecutionTests {

        /**
         * Verifies that a simple command with a string argument is executed correctly.
         */
        @Test
        @DisplayName("Debe ejecutar un comando simple con un argumento String")
        void shouldExecuteStringCommand() {
            listener.processInput("greet -user Gemini");
            assertTrue(provider.executed);
            assertEquals("Gemini", provider.lastString);
        }

        /**
         * Verifies that default values are applied when an option is not provided.
         */
        @Test
        @DisplayName("Debe manejar valores por defecto cuando no se pasa la opción")
        void shouldHandleDefaultValues() {
            listener.processInput("sum -v 10"); // m por defecto es 1
            assertTrue(provider.executed);
            assertEquals(11, provider.lastInt);
        }

        /**
         * Verifies that provided option values override default values.
         */
        @Test
        @DisplayName("Debe sobrescribir el valor por defecto si se provee la opción")
        void shouldOverrideDefaultValues() {
            listener.processInput("sum -v 10 -m 5");
            assertEquals(15, provider.lastInt);
        }

        /**
         * Verifies that boolean flags are correctly parsed (presence = true).
         */
        @Test
        @DisplayName("Debe manejar flags booleanos (presente = true)")
        void shouldHandleBooleanFlags() {
            listener.processInput("toggle -active");
            assertTrue(provider.lastBool);
        }

        /**
         * Verifies that raw arguments without special parsing are correctly captured.
         */
        @Test
        @DisplayName("Debe capturar argumentos sin procesar (String[])")
        void shouldCaptureRawArguments() {
            listener.processInput("extra arg1 arg2 \"argumento con espacios\"");
            assertNotNull(provider.lastArgs);
            assertEquals(3, provider.lastArgs.length);
            assertEquals("argumento con espacios", provider.lastArgs[2]);
        }
    }

    /**
     * Tests for ConsoleListener state management and lifecycle.
     */
    @Nested
    @DisplayName("Tests de Estado y Ciclo de Vida")
    class LifecycleTests {

        /**
         * Verifies that the listener is initially in a stopped state.
         */
        @Test
        @DisplayName("El estado inicial debe ser running = false")
        void initialStateShouldBeStopped() throws Exception {
            assertFalse(getRunningField());
        }

        /**
         * Verifies that the start() method activates the running flag.
         */
        @Test
        @DisplayName("El método start() debe activar el flag running")
        void startShouldSetRunningToTrue() throws Exception {
            listener.start();
            assertTrue(getRunningField());
        }

        /**
         * Verifies that the 'exit' command stops the listener and sets running to false.
         */
        @Test
        @DisplayName("El comando 'exit' debe detener el listener")
        void exitCommandShouldStopListener() throws Exception {
            listener.start();
            listener.processInput("exit");
            assertFalse(getRunningField());
        }

        /**
         * Verifies that the prompt can be updated and retrieved correctly.
         */
        @Test
        @DisplayName("El prompt debe actualizarse correctamente")
        void shouldUpdatePrompt() {
            listener.setPrompt("admin# ");
            assertEquals("admin# ", listener.getPrompt());
        }
    }

    /**
     * Tests for ConsoleListener robustness and edge case handling.
     */
    @Nested
    @DisplayName("Tests de Robustez")
    class RobustnessTests {

        /**
         * Verifies that the listener gracefully handles empty or whitespace-only input.
         */
        @Test
        @DisplayName("No debe fallar con entradas vacías o espacios")
        void shouldIgnoreEmptyInput() {
            assertDoesNotThrow(() -> {
                listener.processInput("");
                listener.processInput("   ");
            });
            assertFalse(provider.executed);
        }

        /**
         * Verifies that command names are parsed case-insensitively.
         */
        @Test
        @DisplayName("Debe ser insensible a mayúsculas en el nombre del comando")
        void commandNamesShouldBeCaseInsensitive() {
            listener.processInput("GREET -user Test");
            assertTrue(provider.executed);
            assertEquals("Test", provider.lastString);
        }
    }

    /**
     * Utility method to access the private 'running' field using reflection.
     * @return the current state of the running flag.
     * @throws Exception if reflection access fails.
     */
    private boolean getRunningField() throws Exception {
        Field field = ConsoleListener.class.getDeclaredField("running");
        field.setAccessible(true);
        return (boolean) field.get(listener);
    }
}