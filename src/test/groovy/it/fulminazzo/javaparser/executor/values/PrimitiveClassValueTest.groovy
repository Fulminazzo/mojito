package it.fulminazzo.javaparser.executor.values

import it.fulminazzo.javaparser.executor.values.objects.ObjectValue
import it.fulminazzo.javaparser.executor.values.primitivevalue.PrimitiveValue
import spock.lang.Specification

class PrimitiveClassValueTest extends Specification {

    def 'test #classValue should be compatible with #value'() {
        expect:
        classValue.compatibleWith(value)

        where:
        classValue                   | value
        // byte
        PrimitiveClassValue.BYTE     | PrimitiveValue.of(1)
        PrimitiveClassValue.BYTE     | ObjectValue.of(1 as Byte)
        // short
        PrimitiveClassValue.SHORT    | PrimitiveValue.of(1)
        PrimitiveClassValue.SHORT    | ObjectValue.of(1 as Byte)
        PrimitiveClassValue.SHORT    | ObjectValue.of(1 as Short)
        // char
        PrimitiveClassValue.CHAR     | PrimitiveValue.of(1)
        PrimitiveClassValue.CHAR     | PrimitiveValue.of('a' as char)
        PrimitiveClassValue.CHAR     | ObjectValue.of('a' as Character)
        // int
        PrimitiveClassValue.INT      | PrimitiveValue.of(1)
        PrimitiveClassValue.INT      | ObjectValue.of(1 as Byte)
        PrimitiveClassValue.INT      | ObjectValue.of(1 as Short)
        PrimitiveClassValue.INT      | PrimitiveValue.of('a' as char)
        PrimitiveClassValue.INT      | ObjectValue.of('a' as Character)
        PrimitiveClassValue.INT      | ObjectValue.of(1 as Integer)
        // long
        PrimitiveClassValue.LONG     | PrimitiveValue.of(1)
        PrimitiveClassValue.LONG     | ObjectValue.of(1 as Byte)
        PrimitiveClassValue.LONG     | ObjectValue.of(1 as Short)
        PrimitiveClassValue.LONG     | PrimitiveValue.of('a' as char)
        PrimitiveClassValue.LONG     | ObjectValue.of('a' as Character)
        PrimitiveClassValue.LONG     | ObjectValue.of(1 as Integer)
        PrimitiveClassValue.LONG     | PrimitiveValue.of(1L)
        PrimitiveClassValue.LONG     | ObjectValue.of(1L)
        // float
        PrimitiveClassValue.FLOAT    | PrimitiveValue.of(1)
        PrimitiveClassValue.FLOAT    | ObjectValue.of(1 as Byte)
        PrimitiveClassValue.FLOAT    | ObjectValue.of(1 as Short)
        PrimitiveClassValue.FLOAT    | PrimitiveValue.of('a' as char)
        PrimitiveClassValue.FLOAT    | ObjectValue.of('a' as Character)
        PrimitiveClassValue.FLOAT    | ObjectValue.of(1 as Integer)
        PrimitiveClassValue.FLOAT    | PrimitiveValue.of(1L)
        PrimitiveClassValue.FLOAT    | ObjectValue.of(1L)
        PrimitiveClassValue.FLOAT    | PrimitiveValue.of(1.0f)
        PrimitiveClassValue.FLOAT    | ObjectValue.of(1.0f)
        // double
        PrimitiveClassValue.DOUBLE   | PrimitiveValue.of(1)
        PrimitiveClassValue.DOUBLE   | ObjectValue.of(1 as Byte)
        PrimitiveClassValue.DOUBLE   | ObjectValue.of(1 as Short)
        PrimitiveClassValue.DOUBLE   | PrimitiveValue.of('a' as char)
        PrimitiveClassValue.DOUBLE   | ObjectValue.of('a' as Character)
        PrimitiveClassValue.DOUBLE   | ObjectValue.of(1 as Integer)
        PrimitiveClassValue.DOUBLE   | PrimitiveValue.of(1L)
        PrimitiveClassValue.DOUBLE   | ObjectValue.of(1L)
        PrimitiveClassValue.DOUBLE   | PrimitiveValue.of(1.0f)
        PrimitiveClassValue.DOUBLE   | ObjectValue.of(1.0f)
        PrimitiveClassValue.DOUBLE   | PrimitiveValue.of(1.0d)
        PrimitiveClassValue.DOUBLE   | ObjectValue.of(1.0d)
        // boolean
        PrimitiveClassValue.BOOLEAN  | PrimitiveValue.of(true)
        PrimitiveClassValue.BOOLEAN  | PrimitiveValue.of(false)
        PrimitiveClassValue.BOOLEAN  | ObjectValue.of(true as Boolean)
        PrimitiveClassValue.BOOLEAN  | ObjectValue.of(false as Boolean)
    }

    def 'test #classValue should not be compatible with #value'() {
        expect:
        !classValue.compatibleWith(value)

        where:
        //TODO: wrapper objects
        classValue                   | value
        PrimitiveClassValue.BYTE     | PrimitiveValue.of('a')
        PrimitiveClassValue.BYTE     | PrimitiveValue.of(1L)
        PrimitiveClassValue.BYTE     | PrimitiveValue.of(1.0f)
        PrimitiveClassValue.BYTE     | PrimitiveValue.of(1.0d)
        PrimitiveClassValue.BYTE     | PrimitiveValue.of(true)
        PrimitiveClassValue.SHORT    | PrimitiveValue.of('a')
        PrimitiveClassValue.SHORT    | PrimitiveValue.of(1L)
        PrimitiveClassValue.SHORT    | PrimitiveValue.of(1.0f)
        PrimitiveClassValue.SHORT    | PrimitiveValue.of(1.0d)
        PrimitiveClassValue.SHORT    | PrimitiveValue.of(true)
        PrimitiveClassValue.CHAR     | PrimitiveValue.of('a')
        PrimitiveClassValue.CHAR     | PrimitiveValue.of(1L)
        PrimitiveClassValue.CHAR     | PrimitiveValue.of(1.0f)
        PrimitiveClassValue.CHAR     | PrimitiveValue.of(1.0d)
        PrimitiveClassValue.CHAR     | PrimitiveValue.of(true)
        PrimitiveClassValue.INT      | PrimitiveValue.of(1.0f)
        PrimitiveClassValue.INT      | PrimitiveValue.of(1.0d)
        PrimitiveClassValue.INT      | PrimitiveValue.of(true)
        PrimitiveClassValue.LONG     | PrimitiveValue.of(1.0f)
        PrimitiveClassValue.LONG     | PrimitiveValue.of(1.0d)
        PrimitiveClassValue.LONG     | PrimitiveValue.of(true)
        PrimitiveClassValue.FLOAT    | PrimitiveValue.of(1.0d)
        PrimitiveClassValue.FLOAT    | PrimitiveValue.of(true)
        PrimitiveClassValue.DOUBLE   | PrimitiveValue.of(true)
        PrimitiveClassValue.BOOLEAN  | PrimitiveValue.of(1)
        PrimitiveClassValue.BOOLEAN  | PrimitiveValue.of('a')
        PrimitiveClassValue.BOOLEAN  | PrimitiveValue.of(1L)
        PrimitiveClassValue.BOOLEAN  | PrimitiveValue.of(1.0f)
        PrimitiveClassValue.BOOLEAN  | PrimitiveValue.of(1.0d)
    }

    def 'test toString of #value should return #expected'() {
        given:
        def string = value.toString()

        expect:
        string == expected

        where:
        value                       | expected
        PrimitiveClassValue.BYTE    | 'byte'
        PrimitiveClassValue.SHORT   | 'short'
        PrimitiveClassValue.CHAR    | 'char'
        PrimitiveClassValue.INT     | 'int'
        PrimitiveClassValue.LONG    | 'long'
        PrimitiveClassValue.FLOAT   | 'float'
        PrimitiveClassValue.DOUBLE  | 'double'
        PrimitiveClassValue.BOOLEAN | 'boolean'
    }

    def 'test values'() {
        expect:
        expected == value

        where:
        value << PrimitiveClassValue.values()
        expected << [
                PrimitiveClassValue.BYTE, PrimitiveClassValue.SHORT, PrimitiveClassValue.CHAR,
                PrimitiveClassValue.INT, PrimitiveClassValue.LONG, PrimitiveClassValue.FLOAT,
                PrimitiveClassValue.DOUBLE, PrimitiveClassValue.BOOLEAN
        ]
    }

    def 'test valueOf #name should return #expected'() {
        given:
        def actual = PrimitiveClassValue.valueOf(name)

        expect:
        actual == expected

        where:
        expected                    | name
        PrimitiveClassValue.BYTE    | 'BYTE'
        PrimitiveClassValue.SHORT   | 'SHORT'
        PrimitiveClassValue.CHAR    | 'CHAR'
        PrimitiveClassValue.INT     | 'INT'
        PrimitiveClassValue.LONG    | 'LONG'
        PrimitiveClassValue.FLOAT   | 'FLOAT'
        PrimitiveClassValue.DOUBLE  | 'DOUBLE'
        PrimitiveClassValue.BOOLEAN | 'BOOLEAN'
    }

    def 'test valueOf invalid should throw'() {
        when:
        PrimitiveClassValue.valueOf('none')

        then:
        thrown(IllegalArgumentException)
    }

}