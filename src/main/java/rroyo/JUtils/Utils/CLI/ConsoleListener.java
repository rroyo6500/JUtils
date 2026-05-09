package rroyo.JUtils.Utils.CLI;

import rroyo.JUtils.Anotations.CLI.JCommand;
import rroyo.JUtils.Anotations.CLI.JOption;
import rroyo.JUtils.Enums.CLI.DataTypes;
import rroyo.JUtils.Utils.IO.ScannerAux;
import rroyo.JUtils.Utils.Logging.LoggerAux;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Class that listens and handles commands in the console, allowing user interaction
 * through a registered command system.
 */
public final class ConsoleListener implements Runnable{

    /** Map that stores registered commands, key: command name in lowercase. */
    private final Map<String, CommandData> commands = new HashMap<>();

    /** Indicates if the listener is running. */
    private volatile boolean running = false;

    /** The prompt displayed in the console. */
    private String prompt = ":> ";

    /**
     * Represents the data of a registered command in the CLI system.
     * @param instance The instance of the object containing the method.
     * @param method The method annotated with @JCommand.
     * @param description The description of the command.
     */
    public record CommandData(Object instance, Method method, String description) {}

    /**
     * Constructor that registers commands from the given providers.
     * @param provider The objects providing commands.
     */
    public ConsoleListener(Object... provider) {
        registerCommands(provider);
    }

    /**
     * Starts the listener in a new thread and returns the instance for chaining.
     * @return The listener instance.
     */
    public synchronized ConsoleListener start() {
        if (!running) {
            running = true;
            Thread t = new Thread(this, "ConsoleListenerThread");
            t.start();
        }
        return this;
    }
    
    /**
     * Scans an object for methods annotated with @JCommand and registers them.
     * @param provider The objects providing commands.
     * @return The listener instance for chaining.
     */
    public ConsoleListener registerCommands(Object... provider) {
        for (Object o : provider) {
            for (Method method : o.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(JCommand.class)) {
                    JCommand ann = method.getAnnotation(JCommand.class);
                    method.setAccessible(true);
                    commands.put(ann.name().toLowerCase(), new CommandData(o, method, ann.description()));
                }
            }
        }
        return this;
    }

    /**
     * Main method of the thread that handles user input.
     */
    @Override
    public void run() {
        LoggerAux.info("CLI System started");
        LoggerAux.setConsoleOutputEnabled(false);

        while (running) {
            processInput(ScannerAux.readString(prompt, false));
        }
    }

    /**
     * Executes the specified command with the given arguments.
     * @param name The name of the command.
     * @param args The arguments for the command.
     */
    private void executeCommand(String name, String[] args) {
        CommandData data = commands.get(name);
        if (data == null) {
            showError("Command not found: " + name);
            return;
        }

        try {
            Method method = data.method();
            java.lang.reflect.Parameter[] parameters = method.getParameters();
            Object[] values = new Object[parameters.length];
            List<String> argList = Arrays.asList(args);

            for (int i = 0; i < parameters.length; i++) {
                java.lang.reflect.Parameter p = parameters[i];

                if (p.isAnnotationPresent(JOption.class)) {
                    JOption opt = p.getAnnotation(JOption.class);
                    String rawName = opt.name();
                    String flag = rawName.startsWith("-") ? rawName : "-" + rawName;
                    int index = argList.indexOf(flag);

                    if (index == -1) {
                        if (opt.defaultValue().equals("__NONE__")) {
                            values[i] = (opt.type() == DataTypes.BOOLEAN) ? false : null;
                        } else {
                            try {
                                values[i] = switch (opt.type()) {
                                    case INT -> Integer.parseInt(opt.defaultValue());
                                    case DOUBLE -> Double.parseDouble(opt.defaultValue());
                                    case FLOAT -> Float.parseFloat(opt.defaultValue());
                                    case STRING -> opt.defaultValue();
                                    case BOOLEAN -> Boolean.parseBoolean(opt.defaultValue());
                                };
                            } catch (Exception e) {
                                throw new IllegalArgumentException("Invalid default value for '" + flag + "'");
                            }
                        }
                        continue;
                    }

                    if (opt.type() == DataTypes.BOOLEAN) {
                        values[i] = true;
                    } else {
                        if (index + 1 >= argList.size()) {
                            throw new IllegalArgumentException("The flag '" + flag + "' requires a value of type " + opt.type());
                        }

                        String rawValue = argList.get(index + 1);
                        try {
                            values[i] = switch (opt.type()) {
                                case INT -> Integer.parseInt(rawValue);
                                case DOUBLE -> Double.parseDouble(rawValue);
                                case FLOAT -> Float.parseFloat(rawValue);
                                case STRING -> rawValue;
                                default -> null;
                            };
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("Invalid value for '" + flag + "'. Expected " + opt.type());
                        }
                    }

                } else if (p.getType() == String[].class) {
                    values[i] = args;
                }
            }

            method.invoke(data.instance(), values);

        } catch (Exception e) {
            String errorMsg = (e instanceof java.lang.reflect.InvocationTargetException)
                    ? e.getCause().getMessage()
                    : e.getMessage();
            showError("Command error [" + name + "]: " + errorMsg);
        }
    }

    /**
     * Displays an error message in the console.
     * @param message The error message.
     */
    private void showError(String message) {
        LoggerAux.setConsoleOutputEnabled(true);
        LoggerAux.error(message);
        LoggerAux.setConsoleOutputEnabled(false);
    }

    /**
     * Displays the help menu with available commands.
     */
    private void showHelp() {
        System.out.println("\n--- Comandos Disponibles ---");
        commands.forEach((name, data) -> {
            System.out.print(String.format("- %-10s : %s", name, data.description()));

            // Listar las opciones del comando si existen
            for (java.lang.reflect.Parameter p : data.method().getParameters()) {
                if (p.isAnnotationPresent(JOption.class)) {
                    JOption opt = p.getAnnotation(JOption.class);
                    String flag = opt.name().startsWith("-") ? opt.name() : "-" + opt.name();
                    System.out.print("\n    [" + flag + " (" + opt.type() + ")] " + opt.description());
                }
            }
            System.out.println();
        });
        System.out.println("\n- help       : Muestra este menú");
        System.out.println("- exit       : Cierra la consola");
        System.out.println("---------------------------\n");
    }

    /**
     * Gets the current prompt.
     * @return The current prompt.
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * Sets the prompt and returns the instance for chaining.
     * @param prompt The new prompt.
     * @return The listener instance.
     */
    public ConsoleListener setPrompt(String prompt) {
        this.prompt = prompt;
        return this;
    }

    /**
     * Processes the input string by parsing it into command name and arguments.
     * <p>
     * Supports quoted arguments (both single and double quotes) and handles special
     * escape sequences within quoted strings. The method then executes the appropriate
     * command or performs built-in actions (exit, help).
     * </p>
     * <p>
     * Built-in commands:
     * <ul>
     *   <li>"exit": Stops the CLI system and closes the listener.</li>
     *   <li>"help": Displays the help menu with all available commands.</li>
     * </ul>
     * </p>
     *
     * @param input The raw input string from the user. Can contain quoted arguments
     *              and multiple space-separated arguments.
     * @see #executeCommand(String, String[])
     * @see #showHelp()
     */
    public void processInput(String input) {
        input = input.trim();
        if (input.isEmpty()) return;

        List<String> matchList = new ArrayList<>();
        java.util.regex.Matcher m = java.util.regex.Pattern.compile("([\"'])(?:(?=(\\\\?))\\2.)*?\\1|\\S+").matcher(input);

        while (m.find()) {
            String arg = m.group();
            if (arg.startsWith("\"") || arg.startsWith("'")) {
                arg = arg.substring(1, arg.length() - 1);
            }
            matchList.add(arg);
        }

        if (matchList.isEmpty()) return;

        String commandName = matchList.get(0).toLowerCase();
        String[] args = matchList.subList(1, matchList.size()).toArray(new String[0]);

        if (commandName.equals("exit")) {
            running = false;
            LoggerAux.setConsoleOutputEnabled(true);
            LoggerAux.info("CLI System stopped");
        } else if (commandName.equals("help")) {
            showHelp();
        } else {
            executeCommand(commandName, args);
        }
    }
}
