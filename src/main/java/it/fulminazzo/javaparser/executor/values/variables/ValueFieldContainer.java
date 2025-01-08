package it.fulminazzo.javaparser.executor.values.variables;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.javaparser.executor.values.ClassValue;
import it.fulminazzo.javaparser.executor.values.ParameterValues;
import it.fulminazzo.javaparser.executor.values.Value;
import it.fulminazzo.javaparser.visitors.visitorobjects.variables.FieldContainer;
import org.jetbrains.annotations.NotNull;

/**
 * An implementation of {@link FieldContainer} for {@link Value}.
 */
public final class ValueFieldContainer<V>
        extends FieldContainer<ClassValue<?>, Value<?>, ParameterValues>
        implements ValueVariableContainer<V> {

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
        Refl<?> refl = new Refl<>(this.container.getValue());
        refl.setFieldObject(this.name, newValue.getValue());
        return newValue;
    }

    @Override
    public @NotNull ClassValue<V> checkClass() {
        return ValueVariableContainer.super.checkClass();
    }

}
