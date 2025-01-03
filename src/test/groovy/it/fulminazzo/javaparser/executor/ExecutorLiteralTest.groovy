package it.fulminazzo.javaparser.executor

import it.fulminazzo.fulmicollection.structures.tuples.Tuple
import it.fulminazzo.javaparser.executor.values.ClassValue
import it.fulminazzo.javaparser.executor.values.LiteralValue
import it.fulminazzo.javaparser.executor.values.PrimitiveClassValue
import it.fulminazzo.javaparser.executor.values.Value
import it.fulminazzo.javaparser.executor.values.objects.ObjectClassValue
import it.fulminazzo.javaparser.executor.values.objects.ObjectValue
import it.fulminazzo.javaparser.executor.values.primitivevalue.PrimitiveValue
import spock.lang.Specification

class ExecutorLiteralTest extends Specification {
    private Executor executor

    void setup() {
        this.executor = new Executor(getClass())
    }

    def 'test visit literal from code #code should return #expected'() {
        given:
        this.executor.environment.declare(PrimitiveClassValue.INT, 'var', PrimitiveValue.of(1))

        when:
        def read = this.executor.visitLiteralImpl(code)

        then:
        read == expected

        where:
        code                                                        | expected
        'val'                                                       | new LiteralValue('val')
        'int'                                                       | PrimitiveClassValue.INT
        'String'                                                    | ObjectClassValue.STRING
        'System'                                                    | ClassValue.of(System)
        'System.class'                                              | ClassValue.of(Class)
        'System.out'                                                | ObjectValue.of(System.out)
        'var'                                                       | PrimitiveValue.of(1)
        'var.TYPE'                                                  | ObjectValue.of(int)
        "${FirstInnerClass.canonicalName}.second"                   | ObjectValue.of(FirstInnerClass.second)
        "${FirstInnerClass.canonicalName}.second.version"           | PrimitiveValue.of(2)
        "${FirstInnerClass.SecondInnerClass.canonicalName}"         | ClassValue.of(FirstInnerClass.SecondInnerClass)
        "${FirstInnerClass.SecondInnerClass.canonicalName}.version" | PrimitiveValue.of(2)
    }

    def 'test getTypeFromLiteral #literal'() {
        given:
        this.executor.environment.declare(PrimitiveClassValue.INT, 'i', PrimitiveValue.of(1))

        when:
        def tuple = this.executor.getValueFromLiteral(literal)

        then:
        tuple == expected

        where:
        literal   | expected
        'int'     | new Tuple<>(PrimitiveClassValue.INT, PrimitiveClassValue.INT)
        'i'       | new Tuple<>(PrimitiveClassValue.INT, PrimitiveValue.of(1))
        'invalid' | new Tuple<>()
    }

    static class FirstInnerClass {
        public static SecondInnerClass second = new SecondInnerClass()

        static class SecondInnerClass {
            public static int version = 2
        }

    }

}