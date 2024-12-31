package it.fulminazzo.javaparser.typechecker.types

import it.fulminazzo.javaparser.typechecker.TypeCheckerException
import it.fulminazzo.javaparser.typechecker.types.objects.ClassObjectType
import spock.lang.Specification

import java.util.regex.Pattern

class ParameterTypesTest extends Specification {

    def 'test toJavaClassArray of #parameter should return #expected'() {
        given:
        def types = new ParameterTypes([parameter])

        when:
        def clazz = types.toJavaClassArray()

        then:
        clazz.length == 1
        clazz[0] == expected

        where:
        parameter << [
                PrimitiveType.values(),
                ClassObjectType.values(),
                ClassObjectType.of(getClass())
        ].flatten()
        expected << [
                [byte, short, char, int, long, float, double, boolean],
                [Byte, Short, Character, Integer, Long, Float, Double, Boolean, String, Object],
                getClass()
        ].flatten()
    }

    def 'test toClassType should throw exception'() {
        given:
        def types = new ParameterTypes([])

        when:
        types.toClassType()

        then:
        def e = thrown(TypeCheckerException)
        e.message == TypeCheckerException.noClassType(ParameterTypes).message
    }

}