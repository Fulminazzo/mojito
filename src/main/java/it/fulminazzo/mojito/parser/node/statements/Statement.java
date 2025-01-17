package it.fulminazzo.mojito.parser.node.statements;

import it.fulminazzo.mojito.parser.node.Node;
import it.fulminazzo.mojito.parser.node.NodeImpl;
import it.fulminazzo.mojito.parser.node.literals.EmptyLiteral;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a general statement.
 */
@Getter
public class Statement extends NodeImpl {
    private final @NotNull Node expression;

    /**
     * Instantiates a new Statement with expression {@link EmptyLiteral}.
     */
    public Statement() {
        this(new EmptyLiteral());
    }

    /**
     * Instantiates a new Statement.
     *
     * @param expression the expression
     */
    public Statement(final @NotNull Node expression) {
        this.expression = expression;
    }

}
