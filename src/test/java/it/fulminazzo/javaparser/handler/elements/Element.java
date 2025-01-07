package it.fulminazzo.javaparser.handler.elements;

import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import it.fulminazzo.javaparser.handler.HandlerException;
import it.fulminazzo.javaparser.tokenizer.TokenType;
import it.fulminazzo.javaparser.visitors.visitorobjects.VisitorObject;
import it.fulminazzo.javaparser.visitors.visitorobjects.VisitorObjectException;
import it.fulminazzo.javaparser.visitors.visitorobjects.variables.FieldContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@SuppressWarnings("unchecked")
public class Element implements VisitorObject<ClassElement, Element, ParameterElements> {
    private final @Nullable Object object;

    public Element(final @Nullable Object object) {
        this.object = object;
    }

    @Override
    public boolean isPrimitive() {
        return this.object != null && ReflectionUtils.isPrimitive(this.object.getClass());
    }

    @Override
    public boolean isNull() {
        return this.object == null;
    }

    @Override
    public <T extends VisitorObject<ClassElement, Element, ParameterElements>> @NotNull T check(@NotNull Class<T> clazz) {
        if (clazz.isInstance(this)) return (T) this;
        else throw new HandlerException("%s is not an instance of %s", this, clazz.getCanonicalName());
    }

    @Override
    public @NotNull ClassElement checkClass() {
        if (this instanceof ClassElement) return (ClassElement) this;
        else throw new HandlerException("%s is not a %s", this, ClassElement.class.getSimpleName());
    }

    @Override
    public @NotNull FieldContainer<ClassElement, Element, ParameterElements> getField(@NotNull Field field) throws VisitorObjectException {
        return null;
    }

    @Override
    public @NotNull Element invokeMethod(@NotNull Method method, @NotNull ParameterElements parameters) throws VisitorObjectException {
        return null;
    }

    @Override
    public @NotNull Element toPrimitive() {
        return null;
    }

    @Override
    public @NotNull Element toWrapper() {
        return null;
    }

    @Override
    public @NotNull ClassElement toClass() {
        return null;
    }

    @Override
    public @NotNull VisitorObjectException fieldNotFound(@NotNull ClassElement classVisitorObject, @NotNull String field) {
        return null;
    }

    @Override
    public @NotNull VisitorObjectException methodNotFound(@NotNull ClassElement classObject, @NotNull String method,
                                                          @NotNull ParameterElements parameters) {
        return null;
    }

    @Override
    public @NotNull VisitorObjectException typesMismatch(@NotNull ClassElement classObject, @NotNull Executable method,
                                                         @NotNull ParameterElements parameters) {
        return null;
    }

    @Override
    public @NotNull RuntimeException noClassType(@NotNull Class<?> type) {
        return null;
    }

    @Override
    public @NotNull RuntimeException unsupportedOperation(@NotNull TokenType operator,
                                                          @NotNull VisitorObject<ClassElement, Element, ParameterElements> left,
                                                          @NotNull VisitorObject<ClassElement, Element, ParameterElements> right) {
        return null;
    }

    @Override
    public @NotNull RuntimeException unsupportedOperation(@NotNull TokenType operator,
                                                          @NotNull VisitorObject<ClassElement, Element, ParameterElements> operand) {
        return null;
    }

}
