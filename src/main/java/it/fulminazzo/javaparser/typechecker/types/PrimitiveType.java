package it.fulminazzo.javaparser.typechecker.types;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a primitive {@link Type} name.
 */
public enum PrimitiveType implements ClassType {
    /**
     * <code>byte</code>
     */
    BYTE(ValueType.CHAR, ValueType.NUMBER),
    /**
     * <code>char</code>
     */
    CHAR(ValueType.CHAR, ValueType.NUMBER),
    /**
     * <code>short</code>
     */
    SHORT(ValueType.CHAR, ValueType.NUMBER),
    /**
     * <code>int</code>
     */
    INT(ValueType.CHAR, ValueType.NUMBER),
    /**
     * <code>long</code>
     */
    LONG(ValueType.CHAR, ValueType.NUMBER, ValueType.LONG),
    /**
     * <code>float</code>
     */
    FLOAT(ValueType.CHAR, ValueType.NUMBER, ValueType.LONG, ValueType.FLOAT),
    /**
     * <code>double</code>
     */
    DOUBLE(ValueType.CHAR, ValueType.NUMBER, ValueType.LONG, ValueType.FLOAT, ValueType.DOUBLE),
    /**
     * <code>boolean</code>
     */
    BOOLEAN(ValueType.BOOLEAN),
    ;

    private final Type @NotNull [] compatibleTypes;

    PrimitiveType(final Type @NotNull ... compatibleTypes) {
        this.compatibleTypes = compatibleTypes;
    }

    @Override
    public boolean compatibleWith(@NotNull Type type) {
        for (Type compatibleType : this.compatibleTypes)
            if (compatibleType.is(type)) return true;
        return false;
    }

}
