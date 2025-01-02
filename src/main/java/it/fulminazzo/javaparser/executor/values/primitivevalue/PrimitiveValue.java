package it.fulminazzo.javaparser.executor.values.primitivevalue;

import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import it.fulminazzo.javaparser.executor.values.ClassValue;
import it.fulminazzo.javaparser.executor.values.PrimitiveClassValue;
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
    public boolean isCharacter() {
        return is(CharValue.class);
    }

    @Override
    public boolean isInteger() {
        return is(IntValue.class);
    }

    @Override
    public boolean isLong() {
        return is(LongValue.class);
    }

    @Override
    public boolean isFloat() {
        return is(FloatValue.class);
    }

    @Override
    public boolean isDouble() {
        return is(DoubleValue.class);
    }

    @Override
    public boolean isBoolean() {
        return is(BooleanValue.class);
    }

    @Override
    public @NotNull V getValue() {
        return this.object;
    }

    @SuppressWarnings("unchecked")
    @Override
    public @NotNull ClassValue<V> toClassValue() {
        Class<?> clazz = ReflectionUtils.getPrimitiveClass(getValue().getClass());
        return (ClassValue<V>) PrimitiveClassValue.valueOf(clazz.getSimpleName().toUpperCase());
    }

    /**
     * Gets the most appropriate {@link PrimitiveValue} from the given value.
     *
     * @param <V>   the type of the value
     * @param value the value
     * @return the primitive value
     */
    @SuppressWarnings("unchecked")
    public static <V> @NotNull PrimitiveValue<V> of(@NotNull V value) throws ValueException {
        Value<?> primitiveValue;
        if (value instanceof Double) primitiveValue = new DoubleValue((Double) value);
        else if (value instanceof Float) primitiveValue = new FloatValue((Float) value);
        else if (value instanceof Long) primitiveValue = new LongValue((Long) value);
        else if (value instanceof Boolean)
            if (value == Boolean.TRUE) primitiveValue = BooleanValue.TRUE;
            else primitiveValue = BooleanValue.FALSE;
        else if (value instanceof Character) primitiveValue = new CharValue((Character) value);
        else if (value instanceof Byte || value instanceof Short || value instanceof Integer)
            primitiveValue = new IntValue(Integer.parseInt(value.toString()));
        else throw ValueException.invalidPrimitiveValue(value);
        return (PrimitiveValue<V>) primitiveValue;
    }

}
