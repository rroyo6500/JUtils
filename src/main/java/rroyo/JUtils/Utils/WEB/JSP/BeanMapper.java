package rroyo.JUtils.Utils.WEB.JSP;

import rroyo.JUtils.Anotations.WEB.JSP.*;
import rroyo.JUtils.Exceptions.ValidationException;
import rroyo.JUtils.Utils.Logging.LoggerAux;

import java.lang.reflect.Field;

public class BeanMapper {

    /**
     * Fills an object's fields using data from a RequestHolder.
     *
     * @param holder The source of the data.
     * @param clazz The class to instantiate and fill.
     * @return A filled instance of T.
     */
    public static <T> T map(RequestHolder holder, Class<T> clazz) {
        try {
            T bean = clazz.getDeclaredConstructor().newInstance();
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                if (field.isAnnotationPresent(IgnoreMapping.class)) {
                    LoggerAux.debug("Field '" + field.getName() + "' ignored by @IgnoreMapping");
                    continue;
                }

                String paramName = field.isAnnotationPresent(WebParam.class)
                        ? field.getAnnotation(WebParam.class).value()
                        : field.getName();

                if (field.isAnnotationPresent(Validate.class)) {
                    try {
                        validateField(field.getAnnotation(Validate.class), holder, paramName);
                    } catch (ValidationException ve) {
                        LoggerAux.warn("Validation failed for field '" + paramName + "': " + ve.getMessage());
                        throw ve;
                    }
                }

                if (holder.has(paramName)) {
                    field.setAccessible(true);
                    Object value;

                    if (field.getType().isArray()) {
                        value = holder.getList(paramName);
                        LoggerAux.debug("Mapping array to field '" + field.getName() + "' from parameter '" + paramName + "'");
                    } else {
                        value = convert(holder, paramName, field.getType());
                        LoggerAux.debug("Mapping value [" + value + "] to field '" + field.getName() + "'");
                    }

                    field.set(bean, value);
                }
            }

            LoggerAux.info("Successful mapping of " + clazz.getSimpleName());
            return bean;
        } catch (ValidationException ve) {
            throw ve;
        } catch (Exception e) {
            LoggerAux.error("Critical error during mapping of " + clazz.getSimpleName(), e);
            throw new RuntimeException("Mapping failed: " + e.getMessage(), e);
        }
    }

    private static void validateField(Validate config, RequestHolder holder, String name) {
        String[] values = holder.getList(name); // Obtenemos el array (nunca es null por tu RequestHolder)
        boolean hasContent = values.length > 0 && values[0] != null && !values[0].trim().isEmpty();

        // 1. Validación de Presencia / Requerido
        if (config.required() && !hasContent) {
            throw new ValidationException("Field '" + name + "' is required.");
        }

        // 2. Validación de cantidad mínima (para listas)
        if (values.length < config.minLength()) {
            throw new ValidationException("Field '" + name + "' requires at least " + config.minLength() + " selections.");
        }

        // 3. Validación de Regex (se aplica a CADA elemento de la lista)
        if (hasContent && !config.regex().isEmpty()) {
            for (String val : values) {
                if (!val.matches(config.regex())) {
                    throw new ValidationException("Value '" + val + "' in field '" + name + "' violates format.");
                }
            }
        }
    }

    private static Object convert(RequestHolder holder, String key, Class<?> type) {
        if (type == String.class) return holder.getString(key);
        if (type == int.class || type == Integer.class) return holder.getInt(key);
        if (type == double.class || type == Double.class) return holder.getDouble(key);
        if (type == boolean.class || type == Boolean.class) return holder.getBoolean(key);
        if (type == String[].class) return holder.getList(key);
        return null;
    }

}
