package rroyo.JUtils.Anotations.CLI;

import rroyo.JUtils.Enums.CLI.DataTypes;

import java.lang.annotation.*;

/**
 * Annotation to define CLI options for command parameters.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface JOption {
    String name();
    String description() default "";
    DataTypes type() default DataTypes.STRING;
    String defaultValue() default "__NONE__";
}
