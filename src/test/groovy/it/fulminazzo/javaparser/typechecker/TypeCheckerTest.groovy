package it.fulminazzo.javaparser.typechecker

import it.fulminazzo.javaparser.parser.node.container.CodeBlock
import it.fulminazzo.javaparser.parser.node.container.JavaProgram
import it.fulminazzo.javaparser.parser.node.literals.Literal
import it.fulminazzo.javaparser.parser.node.statements.Break
import it.fulminazzo.javaparser.parser.node.statements.IfStatement
import it.fulminazzo.javaparser.parser.node.statements.Return
import it.fulminazzo.javaparser.parser.node.statements.Statement
import it.fulminazzo.javaparser.parser.node.values.*
import it.fulminazzo.javaparser.typechecker.types.PrimitiveType
import it.fulminazzo.javaparser.typechecker.types.ValueType
import it.fulminazzo.javaparser.typechecker.types.arrays.ArrayClassType
import it.fulminazzo.javaparser.typechecker.types.arrays.ArrayType
import spock.lang.Specification

class TypeCheckerTest extends Specification {
    private static final BOOL_LIT = new BooleanValueLiteral('true')
    private static final CHAR_LIT = new CharValueLiteral('\'a\'')
    private static final NUMBER_LIT = new NumberValueLiteral('1')
    private static final LONG_LIT = new LongValueLiteral('1L')
    private static final FLOAT_LIT = new FloatValueLiteral('1.0f')
    private static final DOUBLE_LIT = new DoubleValueLiteral('1.0d')
    private static final STRING_LIT = new StringValueLiteral('\"Hello, world!\"')

    private TypeChecker typeChecker

    void setup() {
        this.typeChecker = new TypeChecker()
    }

    def 'test visit program #program should return #type'() {
        given:
        def actual = this.typeChecker.visitProgram(program)

        expect:
        actual == type

        where:
        type                          | program
        Optional.of(ValueType.NUMBER) | new JavaProgram(new LinkedList<>([new Return(NUMBER_LIT)]))
        Optional.empty()              | new JavaProgram(new LinkedList<>([new Break()]))
    }

    def 'test visit dynamic array'() {
        given:
        def type = this.typeChecker.visitDynamicArray(
                Arrays.asList(BOOL_LIT, BOOL_LIT),
                Literal.of('boolean')
        )

        and:
        def expected = new ArrayType(PrimitiveType.BOOLEAN)

        expect:
        type == expected
    }

    def 'test visit static array'() {
        given:
        def type = this.typeChecker.visitStaticArray(
                1,
                Literal.of('boolean')
        )

        and:
        def expected = new ArrayType(PrimitiveType.BOOLEAN)

        expect:
        type == expected
    }

    def 'test visit static array invalid size'() {
        when:
        this.typeChecker.visitStaticArray(
                -1,
                Literal.of('boolean')
        )

        then:
        def e = thrown(TypeCheckerException)
        e.message == TypeCheckerException.invalidArraySize(-1).message
    }

    def 'test visit java program'() {
        given:
        def statements = new LinkedList<>([
                new IfStatement(
                        BOOL_LIT,
                        new CodeBlock(new Return(CHAR_LIT)),
                        new Statement()
                ),
                new Return(NUMBER_LIT)
        ])

        when:
        def type = this.typeChecker.visitJavaProgram(statements)

        then:
        type == ValueType.NUMBER
    }

    def 'test visit code block'() {
        given:
        def statements = new LinkedList<>([
                new IfStatement(
                        BOOL_LIT,
                        new CodeBlock(new Return(CHAR_LIT)),
                        new Statement()
                ),
                new Return(NUMBER_LIT)
        ])

        when:
        def type = this.typeChecker.visitCodeBlock(statements)

        then:
        type == ValueType.NUMBER
    }

    def 'test visit array literal'() {
        when:
        def type = this.typeChecker.visitArrayLiteral(Literal.of('int'))

        then:
        type == new ArrayClassType(PrimitiveType.INT)
    }

    def 'test decrement for #literal should return #expected'() {
        given:
        def type = this.typeChecker.visitDecrement(true, literal)

        expect:
        type == expected

        where:
        literal     | expected
        NUMBER_LIT  | ValueType.NUMBER
        CHAR_LIT    | ValueType.CHAR
        LONG_LIT    | ValueType.LONG
        FLOAT_LIT   | ValueType.FLOAT
        DOUBLE_LIT  | ValueType.DOUBLE
    }

    def 'test increment for #literal should return #expected'() {
        given:
        def type = this.typeChecker.visitIncrement(false, literal)

        expect:
        type == expected

        where:
        literal     | expected
        NUMBER_LIT  | ValueType.NUMBER
        CHAR_LIT    | ValueType.CHAR
        LONG_LIT    | ValueType.LONG
        FLOAT_LIT   | ValueType.FLOAT
        DOUBLE_LIT  | ValueType.DOUBLE
    }

    def 'test equal'() {
        given:
        def type = this.typeChecker.visitEqual(NUMBER_LIT, NUMBER_LIT)

        expect:
        type == ValueType.BOOLEAN
    }

    def 'test not equal'() {
        given:
        def type = this.typeChecker.visitNotEqual(NUMBER_LIT, NUMBER_LIT)

        expect:
        type == ValueType.BOOLEAN
    }

    def 'test less than'() {
        given:
        def type = this.typeChecker.visitLessThan(NUMBER_LIT, NUMBER_LIT)

        expect:
        type == ValueType.BOOLEAN
    }

    def 'test less than equal'() {
        given:
        def type = this.typeChecker.visitLessThanEqual(NUMBER_LIT, NUMBER_LIT)

        expect:
        type == ValueType.BOOLEAN
    }

    def 'test greater than'() {
        given:
        def type = this.typeChecker.visitGreaterThan(NUMBER_LIT, NUMBER_LIT)

        expect:
        type == ValueType.BOOLEAN
    }

    def 'test greater than equal'() {
        given:
        def type = this.typeChecker.visitGreaterThanEqual(NUMBER_LIT, NUMBER_LIT)

        expect:
        type == ValueType.BOOLEAN
    }

    def 'test valid and'() {
        given:
        def type = this.typeChecker.visitAnd(BOOL_LIT, BOOL_LIT)

        expect:
        type == ValueType.BOOLEAN
    }

    def 'test invalid and between #first and #second'() {
        when:
        this.typeChecker.visitAnd(first, second)

        then:
        thrown(TypeCheckerException)

        where:
        first      | second
        BOOL_LIT   | NUMBER_LIT
        NUMBER_LIT | BOOL_LIT
        NUMBER_LIT | NUMBER_LIT
    }

    def 'test valid or'() {
        given:
        def type = this.typeChecker.visitOr(BOOL_LIT, BOOL_LIT)

        expect:
        type == ValueType.BOOLEAN
    }

    def 'test invalid or between #first and #second'() {
        when:
        this.typeChecker.visitOr(first, second)

        then:
        thrown(TypeCheckerException)

        where:
        first      | second
        BOOL_LIT   | NUMBER_LIT
        NUMBER_LIT | BOOL_LIT
        NUMBER_LIT | NUMBER_LIT
    }

    def 'test visit bit and of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitBitAnd(first, second)

        then:
        type == expected

        where:
        first      | second     | expected
        BOOL_LIT   | BOOL_LIT   | ValueType.BOOLEAN
        CHAR_LIT   | CHAR_LIT   | ValueType.NUMBER
        NUMBER_LIT | NUMBER_LIT | ValueType.NUMBER
        LONG_LIT   | LONG_LIT   | ValueType.LONG
    }

    def 'test visit bit or of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitBitOr(first, second)

        then:
        type == expected

        where:
        first      | second     | expected
        BOOL_LIT   | BOOL_LIT   | ValueType.BOOLEAN
        CHAR_LIT   | CHAR_LIT   | ValueType.NUMBER
        NUMBER_LIT | NUMBER_LIT | ValueType.NUMBER
        LONG_LIT   | LONG_LIT   | ValueType.LONG
    }

    def 'test visit bit xor of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitBitXor(first, second)

        then:
        type == expected

        where:
        first      | second     | expected
        BOOL_LIT   | BOOL_LIT   | ValueType.BOOLEAN
        CHAR_LIT   | CHAR_LIT   | ValueType.NUMBER
        NUMBER_LIT | NUMBER_LIT | ValueType.NUMBER
        LONG_LIT   | LONG_LIT   | ValueType.LONG
    }

    def 'test visit lshift of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitLShift(first, second)

        then:
        type == expected

        where:
        first      | second     | expected
        CHAR_LIT   | CHAR_LIT   | ValueType.NUMBER
        NUMBER_LIT | NUMBER_LIT | ValueType.NUMBER
        LONG_LIT   | LONG_LIT   | ValueType.LONG
    }

    def 'test visit rshift of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitRShift(first, second)

        then:
        type == expected

        where:
        first      | second     | expected
        CHAR_LIT   | CHAR_LIT   | ValueType.NUMBER
        NUMBER_LIT | NUMBER_LIT | ValueType.NUMBER
        LONG_LIT   | LONG_LIT   | ValueType.LONG
    }

    def 'test visit urshift of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitURShift(first, second)

        then:
        type == expected

        where:
        first      | second     | expected
        CHAR_LIT   | CHAR_LIT   | ValueType.NUMBER
        NUMBER_LIT | NUMBER_LIT | ValueType.NUMBER
        LONG_LIT   | LONG_LIT   | ValueType.LONG
    }

    def 'test visit add of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitAdd(first, second)

        then:
        type == expected

        where:
        first       | second      | expected
        CHAR_LIT    | CHAR_LIT    | ValueType.NUMBER
        NUMBER_LIT  | NUMBER_LIT  | ValueType.NUMBER
        LONG_LIT    | LONG_LIT    | ValueType.LONG
        FLOAT_LIT   | FLOAT_LIT   | ValueType.FLOAT
        DOUBLE_LIT  | DOUBLE_LIT  | ValueType.DOUBLE
        STRING_LIT  | STRING_LIT  | ValueType.STRING
    }

    def 'test visit subtract of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitSubtract(first, second)

        then:
        type == expected

        where:
        first       | second      | expected
        CHAR_LIT    | CHAR_LIT    | ValueType.NUMBER
        NUMBER_LIT  | NUMBER_LIT  | ValueType.NUMBER
        LONG_LIT    | LONG_LIT    | ValueType.LONG
        FLOAT_LIT   | FLOAT_LIT   | ValueType.FLOAT
        DOUBLE_LIT  | DOUBLE_LIT  | ValueType.DOUBLE
    }

    def 'test visit multiply of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitMultiply(first, second)

        then:
        type == expected

        where:
        first       | second      | expected
        CHAR_LIT    | CHAR_LIT    | ValueType.NUMBER
        NUMBER_LIT  | NUMBER_LIT  | ValueType.NUMBER
        LONG_LIT    | LONG_LIT    | ValueType.LONG
        FLOAT_LIT   | FLOAT_LIT   | ValueType.FLOAT
        DOUBLE_LIT  | DOUBLE_LIT  | ValueType.DOUBLE
    }

    def 'test visit divide of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitDivide(first, second)

        then:
        type == expected

        where:
        first       | second      | expected
        CHAR_LIT    | CHAR_LIT    | ValueType.NUMBER
        NUMBER_LIT  | NUMBER_LIT  | ValueType.NUMBER
        LONG_LIT    | LONG_LIT    | ValueType.LONG
        FLOAT_LIT   | FLOAT_LIT   | ValueType.FLOAT
        DOUBLE_LIT  | DOUBLE_LIT  | ValueType.DOUBLE
    }

    def 'test visit modulo of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitModulo(first, second)

        then:
        type == expected

        where:
        first       | second      | expected
        CHAR_LIT    | CHAR_LIT    | ValueType.NUMBER
        NUMBER_LIT  | NUMBER_LIT  | ValueType.NUMBER
        LONG_LIT    | LONG_LIT    | ValueType.LONG
        FLOAT_LIT   | FLOAT_LIT   | ValueType.FLOAT
        DOUBLE_LIT  | DOUBLE_LIT  | ValueType.DOUBLE
    }

    def 'test visit minus for #literal should return #expected'() {
        given:
        def type = this.typeChecker.visitMinus(literal)

        expect:
        type == expected

        where:
        literal     | expected
        NUMBER_LIT  | ValueType.NUMBER
        CHAR_LIT    | ValueType.NUMBER
        LONG_LIT    | ValueType.LONG
        FLOAT_LIT   | ValueType.FLOAT
        DOUBLE_LIT  | ValueType.DOUBLE
    }

    def 'test visit not'() {
        given:
        def type = this.typeChecker.visitNot(BOOL_LIT)

        expect:
        type == ValueType.BOOLEAN
    }

}
