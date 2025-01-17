package it.fulminazzo.mojito.handler.elements;

import it.fulminazzo.mojito.visitors.visitorobjects.VisitorObjectException;
import org.jetbrains.annotations.NotNull;

public class ElementException extends VisitorObjectException {

    /**
     * Instantiates a new Element exception.
     *
     * @param message the message
     * @param args    the arguments to add in the message format
     */
    ElementException(@NotNull String message, Object @NotNull ... args) {
        super(message, args);
    }

}
