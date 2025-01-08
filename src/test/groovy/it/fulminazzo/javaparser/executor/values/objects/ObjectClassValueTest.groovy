package it.fulminazzo.javaparser.executor.values.objects

import it.fulminazzo.fulmicollection.utils.StringUtils
import it.fulminazzo.javaparser.executor.values.ClassValue
import it.fulminazzo.javaparser.executor.values.Values
import it.fulminazzo.javaparser.executor.values.primitivevalue.PrimitiveValue
import spock.lang.Specification

class ObjectClassValueTest extends Specification {
    static final BYTE = PrimitiveValue.of((byte) 1)
    static final SHORT = PrimitiveValue.of((short) 1)
    static final CHAR = PrimitiveValue.of((char) 'c')
    static final INT = PrimitiveValue.of(1)
    static final LONG = PrimitiveValue.of(1L)
    static final FLOAT = PrimitiveValue.of(1.0f)
    static final DOUBLE = PrimitiveValue.of(1.0d)
    static final BOOLEAN = PrimitiveValue.of(true)

    static final BYTE_WRAPPER = ObjectValue.of((byte) 1)
    static final SHORT_WRAPPER = ObjectValue.of((short) 1)
    static final CHARACTER = ObjectValue.of((char) 'c')
    static final INTEGER = ObjectValue.of(1)
    static final LONG_WRAPPER = ObjectValue.of(1L)
    static final FLOAT_WRAPPER = ObjectValue.of(1.0f)
    static final DOUBLE_WRAPPER = ObjectValue.of(1.0d)
    static final BOOLEAN_WRAPPER = ObjectValue.of(true)
    static final STRING = ObjectValue.of("Hello")

    def 'test #value getValue should return #clazz'() {
        given:
        def actual = value.getValue()

        expect:
        actual == clazz

        where:
        value                      | clazz
        ObjectClassValue.BYTE      | Byte.class
        ObjectClassValue.CHARACTER | Character.class
        ObjectClassValue.SHORT     | Short.class
        ObjectClassValue.INTEGER   | Integer.class
        ObjectClassValue.LONG      | Long.class
        ObjectClassValue.FLOAT     | Float.class
        ObjectClassValue.DOUBLE    | Double.class
        ObjectClassValue.BOOLEAN   | Boolean.class
        ObjectClassValue.STRING    | String.class
        ObjectClassValue.OBJECT    | Object.class
    }

    def 'test valueOf #name should return #expected'() {
        given:
        def value = ObjectClassValue.valueOf(name)

        expect:
        value == expected

        where:
        expected << ObjectClassValue.values()
        name << [
                'BYTE', 'SHORT', 'CHARACTER', 'INTEGER',
                'LONG', 'FLOAT', 'DOUBLE', 'BOOLEAN',
                'STRING', 'OBJECT'
        ]
    }

    def 'test toString of #value'() {
        given:
        def string = value.toString()
        def expected = ClassValue.print(StringUtils.capitalize(value.name()))

        expect:
        string == expected

        where:
        value << ObjectClassValue.values()
    }

    def 'test BYTE compatible with #value'() {
        expect:
        ObjectClassValue.BYTE.compatibleWith(value)

        where:
        value << [
                BYTE,
                BYTE_WRAPPER,
                Values.NULL_VALUE
        ]
    }

    def 'test BYTE incompatible with #value'() {
        expect:
        !ObjectClassValue.BYTE.compatibleWith(value)

        where:
        value << [
                LONG,
                DOUBLE, FLOAT,
                BOOLEAN, STRING,
                CHARACTER,
                SHORT_WRAPPER, INTEGER,
                LONG_WRAPPER, FLOAT_WRAPPER,
                DOUBLE_WRAPPER, BOOLEAN_WRAPPER,
                STRING
        ]
    }

    def 'test CHAR compatible with #value'() {
        expect:
        ObjectClassValue.CHARACTER.compatibleWith(value)

        where:
        value << [
                CHAR, INT,
                CHARACTER,
                Values.NULL_VALUE
        ]
    }

    def 'test CHAR incompatible with #value'() {
        expect:
        !ObjectClassValue.CHARACTER.compatibleWith(value)

        where:
        value << [
                LONG,
                DOUBLE, FLOAT,
                BOOLEAN, STRING,
                BYTE_WRAPPER,
                SHORT_WRAPPER, INTEGER,
                LONG_WRAPPER, FLOAT_WRAPPER,
                DOUBLE_WRAPPER, BOOLEAN_WRAPPER,
                STRING
        ]
    }

    def 'test SHORT compatible with #value'() {
        expect:
        ObjectClassValue.SHORT.compatibleWith(value)

        where:
        value << [
                BYTE, SHORT,
                BYTE_WRAPPER, SHORT_WRAPPER,
                Values.NULL_VALUE
        ]
    }

    def 'test SHORT incompatible with #value'() {
        expect:
        !ObjectClassValue.SHORT.compatibleWith(value)

        where:
        value << [
                LONG,
                DOUBLE, FLOAT,
                BOOLEAN, STRING,
                CHARACTER, INTEGER,
                LONG_WRAPPER, FLOAT_WRAPPER,
                DOUBLE_WRAPPER, BOOLEAN_WRAPPER,
                STRING
        ]
    }

    def 'test INT compatible with #value'() {
        expect:
        ObjectClassValue.INTEGER.compatibleWith(value)

        where:
        value << [
                CHAR, INT,
                BYTE_WRAPPER, CHARACTER,
                SHORT_WRAPPER, INTEGER,
                Values.NULL_VALUE
        ]
    }

    def 'test INT incompatible with #value'() {
        expect:
        !ObjectClassValue.INTEGER.compatibleWith(value)

        where:
        value << [
                LONG,
                DOUBLE, FLOAT,
                BOOLEAN, STRING,
                LONG_WRAPPER, FLOAT_WRAPPER,
                DOUBLE_WRAPPER, BOOLEAN_WRAPPER,
                STRING
        ]
    }

    def 'test LONG compatible with #value'() {
        expect:
        ObjectClassValue.LONG.compatibleWith(value)

        where:
        value << [
                CHAR, INT, LONG,
                BYTE_WRAPPER, CHARACTER,
                SHORT_WRAPPER, INTEGER,
                LONG_WRAPPER,
                Values.NULL_VALUE
        ]
    }

    def 'test LONG incompatible with #value'() {
        expect:
        !ObjectClassValue.LONG.compatibleWith(value)

        where:
        value << [
                DOUBLE, FLOAT,
                BOOLEAN, STRING,
                FLOAT_WRAPPER,
                DOUBLE_WRAPPER, BOOLEAN_WRAPPER,
                STRING
        ]
    }

    def 'test FLOAT compatible with #value'() {
        expect:
        ObjectClassValue.FLOAT.compatibleWith(value)

        where:
        value << [
                CHAR, INT, LONG,
                FLOAT,
                BYTE_WRAPPER, CHARACTER,
                SHORT_WRAPPER, INTEGER,
                LONG_WRAPPER, FLOAT_WRAPPER,
                Values.NULL_VALUE
        ]
    }

    def 'test FLOAT incompatible with #value'() {
        expect:
        !ObjectClassValue.FLOAT.compatibleWith(value)

        where:
        value << [
                DOUBLE,
                BOOLEAN, STRING,
                DOUBLE_WRAPPER, BOOLEAN_WRAPPER,
                STRING
        ]
    }

    def 'test DOUBLE compatible with #value'() {
        expect:
        ObjectClassValue.DOUBLE.compatibleWith(value)

        where:
        value << [
                CHAR, INT, LONG,
                FLOAT, DOUBLE,
                BYTE_WRAPPER, CHARACTER,
                SHORT_WRAPPER, INTEGER,
                LONG_WRAPPER, FLOAT_WRAPPER,
                DOUBLE_WRAPPER,
                Values.NULL_VALUE
        ]
    }

    def 'test DOUBLE incompatible with #value'() {
        expect:
        !ObjectClassValue.DOUBLE.compatibleWith(value)

        where:
        value << [
                BOOLEAN, STRING,
                BOOLEAN_WRAPPER, STRING
        ]
    }

    def 'test BOOLEAN compatible with #value'() {
        expect:
        ObjectClassValue.BOOLEAN.compatibleWith(value)

        where:
        value << [
                BOOLEAN, BOOLEAN_WRAPPER,
                Values.NULL_VALUE
        ]
    }

    def 'test BOOLEAN incompatible with #value'() {
        expect:
        !ObjectClassValue.BOOLEAN.compatibleWith(value)

        where:
        value << [
                CHAR, INT, LONG,
                DOUBLE, FLOAT,
                STRING,
                BYTE_WRAPPER, CHARACTER,
                SHORT_WRAPPER, INTEGER,
                LONG_WRAPPER, FLOAT_WRAPPER,
                DOUBLE_WRAPPER, STRING
        ]
    }

    def 'test STRING compatible with #value'() {
        expect:
        ObjectClassValue.STRING.compatibleWith(value)

        where:
        value << [
                STRING, STRING,
                Values.NULL_VALUE
        ]
    }

    def 'test STRING incompatible with #value'() {
        expect:
        !ObjectClassValue.STRING.compatibleWith(value)

        where:
        value << [
                CHAR, INT, LONG,
                DOUBLE, FLOAT,
                BOOLEAN,
                BYTE_WRAPPER, CHARACTER,
                SHORT_WRAPPER, INTEGER,
                LONG_WRAPPER, FLOAT_WRAPPER,
                DOUBLE_WRAPPER, BOOLEAN_WRAPPER
        ]
    }

    def 'test OBJECT compatible with #value'() {
        expect:
        ObjectClassValue.OBJECT.compatibleWith(value)

        where:
        value << [
                CHAR, INT, LONG,
                DOUBLE, FLOAT,
                BOOLEAN, STRING,
                BYTE_WRAPPER, CHARACTER,
                SHORT_WRAPPER, INTEGER,
                LONG_WRAPPER, FLOAT_WRAPPER,
                DOUBLE_WRAPPER, BOOLEAN_WRAPPER,
                STRING, ObjectValue.of(new Object()),
                Values.NULL_VALUE
        ]
    }

}
