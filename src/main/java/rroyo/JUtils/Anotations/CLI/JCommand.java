package rroyo.JUtils.Anotations.CLI;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a method as an executable command within the application.
 * Methods annotated with this are typically discovered via reflection at runtime.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JCommand {

    /**
     * @return The unique identifier or keyword used to trigger this command.
     */
    String name();

    /**
     * @return A description of the command's functionality.
     */
    String description() default "";

    /**
     * @return Whether this command should be listed in the automatically generated help menu.
     */
    boolean showInHelp() default true;
}
