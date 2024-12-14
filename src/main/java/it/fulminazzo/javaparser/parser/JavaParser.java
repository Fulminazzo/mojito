package it.fulminazzo.javaparser.parser;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import it.fulminazzo.fulmicollection.utils.StringUtils;
import it.fulminazzo.javaparser.parser.node.MethodCall;
import it.fulminazzo.javaparser.parser.node.MethodInvocation;
import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.operators.binary.*;
import it.fulminazzo.javaparser.parser.node.operators.unary.Decrement;
import it.fulminazzo.javaparser.parser.node.operators.unary.Increment;
import it.fulminazzo.javaparser.parser.node.operators.unary.Minus;
import it.fulminazzo.javaparser.parser.node.operators.unary.Not;
import it.fulminazzo.javaparser.parser.node.types.*;
import it.fulminazzo.javaparser.tokenizer.TokenType;
import it.fulminazzo.javaparser.tokenizer.Tokenizer;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import static it.fulminazzo.javaparser.tokenizer.TokenType.*;

/**
 * A parser to read Java code using {@link Tokenizer} and {@link TokenType}.
 */
@NoArgsConstructor
public class JavaParser extends Parser {

    /**
     * Instantiates a new Java parser.
     *
     * @param input the input
     */
    public JavaParser(@NotNull InputStream input) {
        super(input);
    }

    protected void startReading() {
        //TODO: temporary method
        //TODO: for testing purposes only
        getTokenizer().nextSpaceless();
    }

    /**
     * EXPR := METHOD_CALL | RE_ASSIGN | INCREMENT | DECREMENT | EQUAL
     *
     * @return the node
     */
    protected @NotNull Node parseExpression() {
        switch (lastToken()) {
            case LITERAL: {
                final Literal literal = parseLiteral();
                switch (lastToken()) {
                    case OPEN_PAR: return parseMethodCall(literal);
                    default: return parseReAssign(literal);
                }
            }
            case ADD: return parseIncrement();
            case SUBTRACT: return parseDecrement();
            default: return parseBinaryOperation(EQUAL);
        }
    }

    /**
     * METHOD_CALL := LITERAL . METHOD_INVOCATION
     *
     * @param literal the literal to start from
     * @return the node
     */
    protected @NotNull MethodCall parseMethodCall(final @NotNull Literal literal) {
        return new MethodCall(literal, parseMethodInvocation());
    }

    /**
     * METHOD_INVOCATION := \( (EXPR)? (, EXPR)* \)
     *
     * @return the node
     */
    protected @NotNull MethodInvocation parseMethodInvocation() {
        List<Node> parameters = new LinkedList<>();
        consume(OPEN_PAR);
        while (lastToken() != CLOSE_PAR) {
            parameters.add(parseExpression());
            if (lastToken() == COMMA) consume(COMMA);
        }
        consume(CLOSE_PAR);
        return new MethodInvocation(parameters);
    }

    /**
     * INCREMENT := ++LITERAL
     *
     * @return the node
     */
    protected @NotNull Increment parseIncrement() {
        consume(ADD);
        consume(ADD);
        return new Increment(parseLiteral(), true);
    }

    /**
     * DECREMENT := --LITERAL
     *
     * @return the node
     */
    protected @NotNull Decrement parseDecrement() {
        consume(SUBTRACT);
        consume(SUBTRACT);
        return new Decrement(parseLiteral(), true);
    }

    /**
     * RE_ASSIGN := LITERAL (+|-|*|/|%|&|\||^|<<|>>|>>>)?= EXPR | LITERAL(++|--)
     *
     * @param literal the literal to start from
     * @return the node
     */
    protected @NotNull Node parseReAssign(final @NotNull Literal literal) {
        final Node expr;
        final TokenType token = lastToken();
        switch (token) {
            case ADD:
            case SUBTRACT: {
                consume(token);
                if (lastToken() == token) {
                    consume(token);
                    if (token == ADD) return new Increment(literal, false);
                    else return new Decrement(literal, false);
                } else expr = parseOperationReAssign(token, literal);
                break;
            }
            case MULTIPLY:
            case DIVIDE:
            case MODULO:
            case BIT_AND:
            case BIT_OR:
            case BIT_XOR:
            case LSHIFT:
            case RSHIFT:
            case URSHIFT: {
                consume(token);
                expr = parseOperationReAssign(token, literal);
                break;
            }
            case ASSIGN: {
                consume(ASSIGN);
                expr = parseExpression();
                break;
            }
            default:
                // Probably something else, return the literal itself
                return literal;
        }
        return new ReAssign(literal, expr);
    }

    private @NotNull Node parseOperationReAssign(final @NotNull TokenType token,
                                                 final @NotNull Node literal) {
        consume(ASSIGN);
        Node tmp = parseExpression();
        return generateBinaryOperationFromToken(token, literal, tmp);
    }

    /**
     * EQUAL := NOT_EQUAL (== NOT_EQUAL)* <br/>
     * NOT_EQUAL := LESS_THAN (!= LESS_THAN)* <br/>
     * LESS_THAN := LESS_THAN_OR_EQUAL (< LESS_THAN_OR_EQUAL)* <br/>
     * LESS_THAN_OR_EQUAL := GREATER_THAN (<= GREATER_THAN)* <br/>
     * GREATER_THAN := GREATER_THAN_OR_EQUAL (> GREATER_THAN_OR_EQUAL)* <br/>
     * GREATER_THAN_OR_EQUAL := AND (>= AND)* <br/>
     * AND := OR (&& OR)* <br/>
     * OR := BIT_AND (|| BIT_AND)* <br/>
     * <br/>
     * BIT_AND := BIT_OR (& BIT_OR)* <br/>
     * BIT_OR := BIT_XOR (| BIT_XOR)* <br/>
     * BIT_XOR := LSHIFT (^ LSHIFT)* <br/>
     * LSHIFT := RSHIFT (<< RSHIFT)* <br/>
     * RSHIFT := URSHIFT (>> URSHIFT)* <br/>
     * URSHIFT := ADD (>>> ADD)* <br/>
     * <br/>
     * ADD := SUB (+ SUB)* <br/>
     * SUB := MUL (- MUL)* <br/>
     * MUL := DIV (* DIV)* <br/>
     * DIV := MOD (/ MOD)* <br/>
     * MOD := MINUS (% MINUS)* <br/>
     *
     * @param operation the {@link TokenType} that corresponds to the operation
     * @return the node
     */
    protected @NotNull Node parseBinaryOperation(final @NotNull TokenType operation) {
        if (operation.ordinal() > MODULO.ordinal()) return parseAtom();
        else {
            final TokenType nextOperation = TokenType.values()[operation.ordinal() + 1];
            Node node = parseBinaryOperation(nextOperation);
            while (lastToken() == operation) {
                consume(operation);
                Node tmp = parseBinaryOperation(nextOperation);
                node = generateBinaryOperationFromToken(operation, node, tmp);
            }
            return node;
        }
    }

    private @NotNull Node generateBinaryOperationFromToken(final @NotNull TokenType operation,
                                                           final @NotNull Node left,
                                                           final @NotNull Node right) {
        Class<? extends BinaryOperation> clazz = findOperationClass(operation.name());
        return new Refl<>(clazz, left, right).getObject();
    }

    private Class<? extends BinaryOperation> findOperationClass(@NotNull String className) {
        if (className.equals(URSHIFT.name())) return URShift.class;
        else if (className.equals(RSHIFT.name())) return RShift.class;
        else if (className.equals(LSHIFT.name())) return LShift.class;
        else {
            className = StringUtils.capitalize(className).replace("_", "");
            return ReflectionUtils.getClass(BinaryOperation.class.getPackage().getName() + "." + className);
        }
    }

    /**
     * ATOM := MINUS | NOT | LITERAL | TYPE_VALUE
     *
     * @return the node
     */
    protected @NotNull Node parseAtom() {
        switch (lastToken()) {
            case MINUS: return parseMinus();
            case NOT: return parseNot();
            case LITERAL: return parseLiteral();
            default: return parseTypeValue();
        }
    }

    /**
     * MINUS := - EXPR
     *
     * @return the node
     */
    protected @NotNull Node parseMinus() {
        consume(MINUS);
        return new Minus(parseExpression());
    }

    /**
     * NOT := ! EXPR
     *
     * @return the node
     */
    protected @NotNull Node parseNot() {
        consume(NOT);
        return new Not(parseExpression());
    }

    /**
     * TYPE_VALUE := {@link TokenType#NUMBER_VALUE} | {@link TokenType#LONG_VALUE} |
     *               {@link TokenType#DOUBLE_VALUE} | {@link TokenType#FLOAT_VALUE} |
     *               {@link TokenType#BOOLEAN_VALUE} | {@link TokenType#CHAR_VALUE} |
     *               {@link TokenType#STRING_VALUE}
     *
     * @return the node
     */
    protected @NotNull BaseTypeLiteral parseTypeValue() {
        final String read = getTokenizer().lastRead();
        final BaseTypeLiteral literal;
        switch (lastToken()) {
            case NUMBER_VALUE: {
                literal = new NumberLiteral(read);
                break;
            }
            case LONG_VALUE: {
                literal = new LongLiteral(read);
                break;
            }
            case DOUBLE_VALUE: {
                literal = new DoubleLiteral(read);
                break;
            }
            case FLOAT_VALUE: {
                literal = new FloatLiteral(read);
                break;
            }
            case BOOLEAN_VALUE: {
                literal = new BooleanLiteral(read);
                break;
            }
            case CHAR_VALUE: {
                literal = new CharLiteral(read);
                break;
            }
            case STRING_VALUE: {
                literal = new StringLiteral(read);
                break;
            }
            default:
                throw new ParserException("Unexpected token: " + lastToken());
        }
        nextSpaceless();
        return literal;
    }

    /**
     * LITERAL := {@link TokenType#LITERAL}
     *
     * @return the node
     */
    protected @NotNull Literal parseLiteral() {
        final String literal = getTokenizer().lastRead();
        consume(LITERAL);
        return new Literal(literal);
    }

}
