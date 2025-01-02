package it.fulminazzo.javaparser.executor.values.primitive;

import it.fulminazzo.javaparser.executor.values.Value;
import it.fulminazzo.javaparser.executor.values.ValueException;
import it.fulminazzo.javaparser.wrappers.ObjectWrapper;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a primitive {@link Value}.
 *
 * @param <V> the type of the value
 */
public abstract class PrimitiveValue<V> extends ObjectWrapper<V> implements Value<V> {

    /**
     * Instantiates a new Primitive value.
     *
     * @param value the value
     */
    PrimitiveValue(@NotNull V value) {
        super(value);
    }

    @Override
    public @NotNull V getValue() {
        return this.object;
    }

    @Override
    public boolean isCharacter() {
        return this instanceof CharacterValue;
    }

    @Override
    public boolean isInteger() {
        return this instanceof IntegerValue;
    }

    @Override
    public boolean isLong() {
        return this instanceof LongValue;
    }

    @Override
    public boolean isFloat() {
        return this instanceof FloatValue;
    }

    @Override
    public boolean isDouble() {
        return this instanceof DoubleValue;
    }

    @Override
    public boolean isBoolean() {
        return this instanceof BooleanValue;
    }

    @Override
    public boolean isString() {
        return this instanceof StringValue;
    }

    /**
     * Gets the most appropriate {@link PrimitiveValue} from the given value.
     *
     * @param <V>   the type of the value
     * @param value the value
     * @return the primitive value
     */
    @SuppressWarnings("unchecked")
    public static <V> @NotNull PrimitiveValue<V> of(@NotNull V value) {
        Value<?> primitiveValue;
        if (value instanceof Double) primitiveValue = new DoubleValue((Double) value);
        else if (value instanceof Float) primitiveValue = new FloatValue((Float) value);
        else if (value instanceof Long) primitiveValue = new LongValue((Long) value);
        else if (value instanceof Boolean)
            if (value == Boolean.TRUE) primitiveValue = BooleanValue.TRUE;
            else primitiveValue = BooleanValue.FALSE;
        else if (value instanceof String) primitiveValue = new StringValue((String) value);
        else if (value instanceof Character) primitiveValue = new CharacterValue((Character) value);
        else if (value instanceof Byte || value instanceof Short || value instanceof Integer)
            primitiveValue = new IntegerValue(Integer.parseInt(value.toString()));
        else throw ValueException.invalidPrimitiveValue(value);
        return (PrimitiveValue<V>) primitiveValue;
    }

}
