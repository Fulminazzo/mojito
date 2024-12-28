package it.fulminazzo.javaparser.parser.node;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a method call invocation.
 */
public class MethodCall extends Node {
    private final @NotNull Node executor;
    private final @NotNull MethodInvocation invocation;

    /**
     * Instantiates a new Method call.
     *
     * @param executor   the executor
     * @param invocation the invocation
     */
    public MethodCall(final @NotNull Node executor,
                      final @NotNull MethodInvocation invocation) {
        this.executor = executor;
        this.invocation = invocation;
    }

}
