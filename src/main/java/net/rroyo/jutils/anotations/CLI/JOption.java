package net.rroyo.jutils.anotations.CLI;

import net.rroyo.jutils.enums.CLI.DataTypes;

import java.lang.annotation.*;

/**
 * Defines a command-line option associated with a method parameter.
 * Used to map input arguments to specific variables during command execution.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface JOption {

    /**
     * @return The name of the option (e.g., "port" or "user").
     */
    String name();

    /**
     * @return A brief explanation of what the option does for help menus.
     */
    String description() default "";

    /**
     * @return The expected data type for validation and parsing. Defaults to STRING.
     */
    DataTypes type() default DataTypes.STRING;

    /**
     * @return The value to use if the option is not provided.
     * Defaults to "__NONE__" to indicate no value is present.
     */
    String defaultValue() default "__NONE__";
}
