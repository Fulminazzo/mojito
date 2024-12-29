package it.fulminazzo.javaparser.typechecker;

import it.fulminazzo.javaparser.environment.Environment;
import it.fulminazzo.javaparser.parser.node.MethodInvocation;
import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.container.CodeBlock;
import it.fulminazzo.javaparser.parser.node.literals.Literal;
import it.fulminazzo.javaparser.parser.node.statements.Statement;
import it.fulminazzo.javaparser.typechecker.types.Type;
import it.fulminazzo.javaparser.visitors.Visitor;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

import static it.fulminazzo.javaparser.typechecker.OperationUtils.*;
import static it.fulminazzo.javaparser.typechecker.types.ValueType.*;

/**
 * A {@link Visitor} that checks and verifies all the types of the parsed code.
 */
public final class TypeChecker implements Visitor<Type> {
    private final Environment<Type> environment;

    /**
     * Instantiates a new Type checker.
     */
    public TypeChecker() {
        this.environment = new Environment<>();
    }

    @Override
    public Type visitAssignment(@NotNull Node type, @NotNull Literal name, @NotNull Node value) {
        // TODO:
        return null;
    }

    @Override
    public Type visitMethodCall(@NotNull Node executor, @NotNull MethodInvocation invocation) {
        // TODO:
        return null;
    }

    @Override
    public Type visitMethodInvocation(@NotNull List<Node> parameters) {
        // TODO:
        return null;
    }

    @Override
    public Type visitDynamicArray(@NotNull List<Node> parameters, @NotNull Node type) {
        // TODO:
        return null;
    }

    @Override
    public Type visitStaticArray(int size, @NotNull Node type) {
        // TODO:
        return null;
    }

    @Override
    public Type visitCodeBlock(@NotNull LinkedList<Statement> statements) {
        // TODO:
        return null;
    }

    @Override
    public Type visitJavaProgram(@NotNull LinkedList<Statement> statements) {
        // TODO:
        return null;
    }

    @Override
    public Type visitArrayLiteral(@NotNull Node type) {
        // TODO:
        return null;
    }

    @Override
    public Type visitEmptyLiteral() {
        // TODO:
        return null;
    }

    @Override
    public Type visitLiteralImpl(@NotNull String value) {
        // TODO:
        return null;
    }

    @Override
    public Type visitAdd(@NotNull Node left, @NotNull Node right) {
        Type leftType = left.accept(this);
        if (leftType.is(STRING)) return right.accept(this).check(STRING);
        else return executeBinaryOperationDecimal(leftType, right.accept(this));
    }

    @Override
    public Type visitAnd(@NotNull Node left, @NotNull Node right) {
        left.accept(this).check(BOOLEAN);
        right.accept(this).check(BOOLEAN);
        return BOOLEAN;
    }

    @Override
    public Type visitBitAnd(@NotNull Node left, @NotNull Node right) {
        return executeBinaryBitOperation(left.accept(this), right.accept(this));
    }

    @Override
    public Type visitBitOr(@NotNull Node left, @NotNull Node right) {
        return executeBinaryBitOperation(left.accept(this), right.accept(this));
    }

    @Override
    public Type visitBitXor(@NotNull Node left, @NotNull Node right) {
        return executeBinaryBitOperation(left.accept(this), right.accept(this));
    }

    @Override
    public Type visitCast(@NotNull Node left, @NotNull Node right) {
        // TODO:
        return null;
    }

    @Override
    public Type visitDivide(@NotNull Node left, @NotNull Node right) {
        return executeBinaryOperationDecimal(left.accept(this), right.accept(this));
    }

    @Override
    public Type visitEqual(@NotNull Node left, @NotNull Node right) {
        return executeObjectComparison(left.accept(this), right.accept(this));
    }

    @Override
    public Type visitGreaterThan(@NotNull Node left, @NotNull Node right) {
        return executeBinaryComparison(left.accept(this), right.accept(this));
    }

    @Override
    public Type visitGreaterThanEqual(@NotNull Node left, @NotNull Node right) {
        return executeBinaryComparison(left.accept(this), right.accept(this));
    }

    @Override
    public Type visitLShift(@NotNull Node left, @NotNull Node right) {
        return executeBinaryOperation(left.accept(this), right.accept(this));
    }

    @Override
    public Type visitLessThan(@NotNull Node left, @NotNull Node right) {
        return executeBinaryComparison(left.accept(this), right.accept(this));
    }

    @Override
    public Type visitLessThanEqual(@NotNull Node left, @NotNull Node right) {
        return executeBinaryComparison(left.accept(this), right.accept(this));
    }

    @Override
    public Type visitModulo(@NotNull Node left, @NotNull Node right) {
        return executeBinaryOperationDecimal(left.accept(this), right.accept(this));
    }

    @Override
    public Type visitMultiply(@NotNull Node left, @NotNull Node right) {
        return executeBinaryOperationDecimal(left.accept(this), right.accept(this));
    }

    @Override
    public Type visitNewObject(@NotNull Node left, @NotNull Node right) {
        // TODO:
        return null;
    }

    @Override
    public Type visitNotEqual(@NotNull Node left, @NotNull Node right) {
        return executeObjectComparison(left.accept(this), right.accept(this));
    }

    @Override
    public Type visitOr(@NotNull Node left, @NotNull Node right) {
        left.accept(this).check(BOOLEAN);
        right.accept(this).check(BOOLEAN);
        return BOOLEAN;
    }

    @Override
    public Type visitRShift(@NotNull Node left, @NotNull Node right) {
        return executeBinaryOperation(left.accept(this), right.accept(this));
    }

    @Override
    public Type visitReAssign(@NotNull Node left, @NotNull Node right) {
        // TODO:
        return null;
    }

    @Override
    public Type visitSubtract(@NotNull Node left, @NotNull Node right) {
        return executeBinaryOperationDecimal(left.accept(this), right.accept(this));
    }

    @Override
    public Type visitURShift(@NotNull Node left, @NotNull Node right) {
        return executeBinaryOperation(left.accept(this), right.accept(this));
    }

    @Override
    public Type visitDecrement(boolean before, @NotNull Node operand) {
        // TODO:
        return null;
    }

    @Override
    public Type visitIncrement(boolean before, @NotNull Node operand) {
        // TODO:
        return null;
    }

    @Override
    public Type visitMinus(@NotNull Node operand) {
        return operand.accept(this).check(OperationUtils.getDecimalTypes());
    }

    @Override
    public Type visitNot(@NotNull Node operand) {
        return operand.accept(this).check(BOOLEAN);
    }

    @Override
    public Type visitBreak(@NotNull Node expr) {
        // TODO:
        return null;
    }

    @Override
    public Type visitContinue(@NotNull Node expr) {
        // TODO:
        return null;
    }

    @Override
    public Type visitDoStatement(@NotNull CodeBlock code, @NotNull Node expr) {
        // TODO:
        return null;
    }

    @Override
    public Type visitEnhancedForStatement(@NotNull Node type, @NotNull Node variable, @NotNull CodeBlock code, @NotNull Node expr) {
        // TODO:
        return null;
    }

    @Override
    public Type visitForStatement(@NotNull Node assignment, @NotNull Node increment, @NotNull CodeBlock code, @NotNull Node expr) {
        // TODO:
        return null;
    }

    @Override
    public Type visitIfStatement(@NotNull CodeBlock code, @NotNull Node thenBranch, @NotNull Node expr) {
        // TODO:
        return null;
    }

    @Override
    public Type visitReturn(@NotNull Node expr) {
        return expr.accept(this);
    }

    @Override
    public Type visitStatement(@NotNull Node expr) {
        // TODO:
        return null;
    }

    @Override
    public Type visitWhileStatement(@NotNull CodeBlock code, @NotNull Node expr) {
        // TODO:
        return null;
    }

    @Override
    public Type visitNullLiteral() {
        // TODO:
        return null;
    }

    @Override
    public Type visitBooleanValueLiteral(@NotNull String rawValue) {
        return BOOLEAN;
    }

    @Override
    public Type visitCharValueLiteral(@NotNull String rawValue) {
        return CHAR;
    }

    @Override
    public Type visitDoubleValueLiteral(@NotNull String rawValue) {
        return DOUBLE;
    }

    @Override
    public Type visitFloatValueLiteral(@NotNull String rawValue) {
        return FLOAT;
    }

    @Override
    public Type visitLongValueLiteral(@NotNull String rawValue) {
        return LONG;
    }

    @Override
    public Type visitNumberValueLiteral(@NotNull String rawValue) {
        return NUMBER;
    }

    @Override
    public Type visitStringValueLiteral(@NotNull String rawValue) {
        return STRING;
    }

}
