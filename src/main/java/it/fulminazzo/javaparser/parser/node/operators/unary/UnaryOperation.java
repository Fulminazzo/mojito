package it.fulminazzo.javaparser.parser.node.operators.unary;

import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.operators.Operation;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an {@link Operation} with one operand.
 */
public abstract class UnaryOperation extends Operation {
    protected final @NotNull Node operand;

    /**
     * Instantiates a new Unary operation.
     *
     * @param operand the operand
     */
    public UnaryOperation(final @NotNull Node operand) {
        this.operand = operand;
    }

}
