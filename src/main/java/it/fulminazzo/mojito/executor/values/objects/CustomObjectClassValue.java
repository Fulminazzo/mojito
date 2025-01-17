package it.fulminazzo.mojito.executor.values.objects;

import it.fulminazzo.mojito.executor.values.ClassValue;
import it.fulminazzo.mojito.executor.values.Value;
import it.fulminazzo.mojito.executor.values.Values;
import it.fulminazzo.mojito.wrappers.ObjectWrapper;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link ObjectClassValue} with a class different from the default types.
 */
class CustomObjectClassValue<V> extends ObjectWrapper<Class<V>> implements ClassValue<V> {

    /**
     * Instantiates a new Custom class object value.
     *
     * @param internalClass the internal class
     */
    public CustomObjectClassValue(@NotNull Class<V> internalClass) {
        super(internalClass);
    }

    @Override
    public @NotNull Class<V> getValue() {
        return this.object;
    }

    @Override
    public boolean compatibleWith(@NotNull Value<?> value) {
        if (value.is(ObjectValue.class))
            return getValue().isAssignableFrom(value.getValue().getClass());
        else return value.equals(Values.NULL_VALUE);
    }

    @Override
    public @NotNull String toString() {
        return ClassValue.print(ObjectValue.getClassName(this.object));
    }

}
