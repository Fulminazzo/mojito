package it.fulminazzo.javaparser.executor.values.primitive

import spock.lang.Specification

import static it.fulminazzo.javaparser.executor.values.primitive.BooleanValue.*

class BooleanValueTest extends Specification {

    def 'test #first ^ #second = #third'() {
        when:
        def eval = first.bitAnd(second)

        then:
        eval == third

        where:
        first | second | third
        TRUE  | TRUE   | of(true & true)
        TRUE  | FALSE  | of(true & false)
        FALSE | TRUE   | of(false & true)
        FALSE | FALSE  | of(false & false)
    }

    def 'test #first | #second = #third'() {
        when:
        def eval = first.bitOr(second)

        then:
        eval == third

        where:
        first | second | third
        TRUE  | TRUE   | of(true | true)
        TRUE  | FALSE  | of(true | false)
        FALSE | TRUE   | of(false | true)
        FALSE | FALSE  | of(false | false)
    }

    def 'test #first ^ #second = #third'() {
        when:
        def eval = first.bitXor(second)

        then:
        eval == third

        where:
        first | second | third
        TRUE  | TRUE   | of(true ^ true)
        TRUE  | FALSE  | of(true ^ false)
        FALSE | TRUE   | of(false ^ true)
        FALSE | FALSE  | of(false ^ false)
    }

}