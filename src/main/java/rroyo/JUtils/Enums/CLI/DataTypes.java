package rroyo.JUtils.Enums.CLI;

/**
 * Represents the supported primitive and standard data types for command options.
 * This is used to guide the type-casting logic during argument injection.
 */
public enum DataTypes {
    /** Represents standard text sequences. */
    STRING,

    /** Represents a single Unicode character. */
    CHAR,

    /** Represents a 32-bit signed integer. */
    INT,

    /** Represents a 64-bit signed integer. */
    LONG,

    /** Represents a double-precision 64-bit IEEE 754 floating point. */
    DOUBLE,

    /** Represents a single-precision 32-bit IEEE 754 floating point. */
    FLOAT,

    /** Represents a logical value: true or false. */
    BOOLEAN
}
