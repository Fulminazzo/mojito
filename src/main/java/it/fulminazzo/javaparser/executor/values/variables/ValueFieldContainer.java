package it.fulminazzo.javaparser.executor.values.variables;

import it.fulminazzo.javaparser.executor.values.ClassValue;
import it.fulminazzo.javaparser.executor.values.ParameterValues;
import it.fulminazzo.javaparser.executor.values.Value;
import it.fulminazzo.javaparser.executor.values.ValueException;
import it.fulminazzo.javaparser.visitors.visitorobjects.variables.FieldContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * An implementation of {@link FieldContainer} for {@link Value}.
 */
@SuppressWarnings("unchecked")
public final class ValueFieldContainer<V> extends FieldContainer<ClassValue<?>, Value<?>, ParameterValues> implements Value<V> {

    /**
     * Instantiates a new Field container.
     *
     * @param type  the type
     * @param value the value
     */
    public ValueFieldContainer(@NotNull Value<?> parent, @NotNull ClassValue<?> type,
                               @NotNull String name, @NotNull Value<?> value) {
        super(parent, type, name, value);
    }

    @Override
    public @NotNull Value<?> set(@NotNull Value<?> newValue) {
        return newValue;
    }

    @Override
    public @Nullable V getValue() {
        return (V) this.variable.getValue();
    }

    @Override
    public boolean isCharacter() {
        return this.variable.isCharacter();
    }

    @Override
    public boolean isInteger() {
        return this.variable.isInteger();
    }

    @Override
    public boolean isLong() {
        return this.variable.isLong();
    }

    @Override
    public boolean isFloat() {
        return this.variable.isFloat();
    }

    @Override
    public boolean isDouble() {
        return this.variable.isDouble();
    }

    @Override
    public boolean isBoolean() {
        return this.variable.isBoolean();
    }

    @Override
    public boolean isString() {
        return this.variable.isString();
    }

    @Override
    public @NotNull ClassValue<V> checkClass() {
        return (ClassValue<V>) this.variable.checkClass();
    }

    @Override
    public @NotNull ValueFieldContainer<V> getField(@NotNull Field field) {
        return (ValueFieldContainer<V>) this.variable.getField(field);
    }

    @Override
    public @NotNull Value<?> invokeMethod(@NotNull Method method, @NotNull ParameterValues parameters) throws ValueException {
        return this.variable.invokeMethod(method, parameters);
    }

}
