package rroyo.JUtils.Utils.CLI;

import rroyo.JUtils.Anotations.CLI.JCommand;
import rroyo.JUtils.Anotations.CLI.JOption;
import rroyo.JUtils.Enums.CLI.DataTypes;
import rroyo.JUtils.Utils.IO.ScannerAux;
import rroyo.JUtils.Utils.Logging.LoggerAux;

import java.lang.reflect.Method;
import java.util.*;

public class ConsoleListener implements Runnable{

    private final Map<String, CommandData> commands = new HashMap<>();

    private boolean running = true;

    private String prompt = ":> ";

    public record CommandData(Object instance, Method method, String description) {}

    public ConsoleListener() {}

    public ConsoleListener(Object provider) {
        registerCommands(provider);
    }

    /**
     * Escanea un objeto buscando métodos con @JCommand y los registra.
     */
    public void registerCommands(Object provider) {
        for (Method method : provider.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(JCommand.class)) {
                JCommand ann = method.getAnnotation(JCommand.class);
                method.setAccessible(true);
                commands.put(ann.name().toLowerCase(), new CommandData(provider, method, ann.description()));
            }
        }
        new Thread(this).start();
    }

    @Override
    public void run() {
        LoggerAux.info("CLI System started");
        LoggerAux.setConsoleOutputEnabled(false);

        while (running) {
            String input = ScannerAux.readString(prompt, false).trim();
            if (input.isEmpty()) continue;

            List<String> matchList = new ArrayList<>();

            java.util.regex.Matcher m = java.util.regex.Pattern.compile("([\"'])(?:(?=(\\\\?))\\2.)*?\\1|\\S+").matcher(input);

            while (m.find()) {
                String arg = m.group();
                if (arg.startsWith("\"") || arg.startsWith("'")) {
                    arg = arg.substring(1, arg.length() - 1);
                }
                matchList.add(arg);
            }

            if (matchList.isEmpty()) continue;

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

    private void showError(String message) {
        LoggerAux.setConsoleOutputEnabled(true);
        LoggerAux.error(message);
        LoggerAux.setConsoleOutputEnabled(false);
    }

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

    public String getPrompt() {
        return prompt;
    }

    public ConsoleListener setPrompt(String prompt) {
        this.prompt = prompt;
        return this;
    }
}
