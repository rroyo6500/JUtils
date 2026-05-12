package rroyo.JUtils.Anotations.WEB.JSP;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Validate {
    boolean required() default false;
    String regex() default "";
    int minLength() default 0;
}
