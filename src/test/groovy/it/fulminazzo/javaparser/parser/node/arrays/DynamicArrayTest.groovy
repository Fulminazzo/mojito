package it.fulminazzo.javaparser.parser.node.arrays

import it.fulminazzo.javaparser.parser.node.literals.Literal
import it.fulminazzo.javaparser.parser.node.values.NumberValueLiteral
import spock.lang.Specification

class DynamicArrayTest extends Specification {
    private DynamicArray array

    void setup() {
        this.array = new DynamicArray(Literal.of('int'), [
                new NumberValueLiteral('1'),
                new NumberValueLiteral('2'),
                new NumberValueLiteral('3'),
        ])
    }

    def 'test size method'() {
        when:
        def size = this.array.size()

        then:
        size == 3
    }

    def 'test toString'() {
        when:
        def output = this.array.toString()

        then:
        output == 'DynamicArray(Literal(int), {NumberValueLiteral(1), NumberValueLiteral(2), NumberValueLiteral(3)})'
    }

}
