package rroyo.JUtils.Utils.WEB.JSP;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to simplify data extraction from {@link HttpServletRequest}.
 * It acts as a wrapper that captures all request parameters and provides
 * type-safe getter methods with default value support.
 *
 * @author rroyo
 */
public class RequestHolder {

    /**
     * Internal storage for request parameters.
     * Uses String[] to handle multiple values per key (e.g., checkboxes).
     */
    private final Map<String, String[]> parameters = new HashMap<>();

    /**
     * Constructs a new RequestHolder by capturing all parameters from the given request.
     *
     * @param request The HttpServletRequest to extract parameters from.
     */
    public RequestHolder(HttpServletRequest request) {
        if (request != null) {
            this.parameters.putAll(request.getParameterMap());
        }
    }

    /**
     * Retrieves a parameter as a String.
     *
     * @param key The name of the parameter.
     * @return The first value associated with the key, or null if it doesn't exist.
     */
    public String getString(String key) {
        return getString(key, null);
    }

    /**
     * Retrieves a parameter as a String with a fallback default value.
     *
     * @param key The name of the parameter.
     * @param defaultValue The value to return if the parameter is missing.
     * @return The parameter value or defaultValue.
     */
    public String getString(String key, String defaultValue) {
        String[] values = parameters.get(key);
        return (values != null && values.length > 0) ? values[0] : defaultValue;
    }

    /**
     * Retrieves a parameter as an integer. Defaults to 0 if missing or invalid.
     *
     * @param key The name of the parameter.
     * @return The parsed integer or 0.
     */
    public int getInt(String key) {
        return getInt(key, 0);
    }

    /**
     * Retrieves a parameter as an integer with a fallback default value.
     *
     * @param key The name of the parameter.
     * @param defaultValue The value to return if parsing fails or key is missing.
     * @return The parsed integer or defaultValue.
     */
    public int getInt(String key, int defaultValue) {
        try {
            String val = getString(key);
            return (val != null) ? Integer.parseInt(val) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Retrieves a parameter as a double. Defaults to 0.0 if missing or invalid.
     *
     * @param key The name of the parameter.
     * @return The parsed double or '0.0'.
     */
    public double getDouble(String key) {
        return getDouble(key, 0.0);
    }

    /**
     * Retrieves a parameter as a double with a fallback default value.
     *
     * @param key The name of the parameter.
     * @param defaultValue The value to return if parsing fails or key is missing.
     * @return The parsed double or defaultValue.
     */
    public double getDouble(String key, double defaultValue) {
        try {
            String val = getString(key);
            return (val != null) ? Double.parseDouble(val) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Retrieves a parameter as a boolean.
     * Interprets "true", "on", "1", and "yes" (case-insensitive) as true.
     *
     * @param key The name of the parameter.
     * @return true if the parameter matches any truthy value, false otherwise.
     */
    public boolean getBoolean(String key) {
        String value = getString(key);
        if (value == null) return false;
        return value.equalsIgnoreCase("true") ||
                value.equalsIgnoreCase("on") ||
                value.equals("1") ||
                value.equalsIgnoreCase("yes");
    }

    /**
     * Retrieves all values associated with a key (useful for multi-selects or checkboxes).
     *
     * @param key The name of the parameter.
     * @return An array of strings containing all values, or an empty array if the key is missing.
     */
    public String[] getList(String key) {
        String[] values = parameters.get(key);
        return (values != null) ? values : new String[0];
    }

    /**
     * Checks if a parameter exists in the request.
     *
     * @param key The name of the parameter.
     * @return true if the key is present in the parameter map.
     */
    public boolean has(String key) {
        return parameters.containsKey(key);
    }

    /**
     * Returns an unmodifiable view of all parameters.
     *
     * @return A Map containing all parameter keys and their corresponding String arrays.
     */
    public Map<String, String[]> getAll() {
        return Collections.unmodifiableMap(parameters);
    }
}