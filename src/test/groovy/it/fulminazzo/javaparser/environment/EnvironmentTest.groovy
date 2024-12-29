package it.fulminazzo.javaparser.environment

import it.fulminazzo.fulmicollection.objects.Refl
import it.fulminazzo.javaparser.utils.TestUtils
import spock.lang.Specification

class EnvironmentTest extends Specification {
    private Environment<Integer> environment

    void setup() {
        this.environment = new Environment()
    }

    void cleanup() {
        // Exit all scopes
        while (!this.environment.isMainScope()) this.environment.exitScope()
    }

    def 'test main scope should not be anything else'() {
        when:
        this.environment.checkScopeType(scopeType)

        then:
        def e = thrown(ScopeException)
        e.message == this.environment.scopeTypeMismatch(scopeType).message

        where:
        scopeType << ScopeType.values().findAll { it != ScopeType.MAIN }
    }

    def 'test exit of main scope should throw exception'() {
        when:
        this.environment.exitScope()

        then:
        thrown(IllegalStateException)
    }

    def 'test inner scope type should be able to see #scopeType scope type'() {
        given:
        this.environment.enterScope(ScopeType.CODE_BLOCK)
        ScopeType.values().each { this.environment.enterScope(it) }
        this.environment.enterScope(scopeType)
        this.environment.enterScope(ScopeType.CODE_BLOCK)
        this.environment.enterScope(ScopeType.CODE_BLOCK)
        this.environment.enterScope(ScopeType.CODE_BLOCK)

        when:
        this.environment.checkScopeType(scopeType)
        if (scopeType != ScopeType.MAIN)
            new Refl<>(this.environment).invokeMethod("check" + TestUtils.convertEnumName(scopeType))

        then:
        notThrown(ScopeException)

        where:
        scopeType << ScopeType.values().findAll { it != ScopeType.MAIN }
    }

    def 'test scope type'() {
        expect:
        this.environment.scopeType() == ScopeType.MAIN
    }

    def 'test declare, isDeclared, lookup and update'() {
        given:
        def varName = 'var'

        when:
        this.environment.declare(new WrapperInfo<>(Integer), varName, 1)
        def declared = this.environment.isDeclared(varName)
        def first = this.environment.lookup(varName)
        this.environment.update(varName, 2)
        def second = this.environment.lookup(varName)

        then:
        declared
        first == 1
        second == 2
    }

    def 'test declare twice'() {
        given:
        def varName = 'var'

        when:
        this.environment.declare(new WrapperInfo<>(Integer), varName, 1)
        this.environment.declare(new WrapperInfo<>(Integer), varName, 2)

        then:
        def e = thrown(ScopeException)
        e.message == this.environment.alreadyDeclaredVariable(varName).message
    }

    def 'test lookup without declare'() {
        given:
        def varName = 'var'

        when:
        this.environment.lookup(varName)

        then:
        def e = thrown(ScopeException)
        e.message == this.environment.noSuchVariable(varName).message
    }

    def 'test update without declare'() {
        given:
        def varName = 'var'

        when:
        this.environment.update(varName, 1)

        then:
        def e = thrown(ScopeException)
        e.message == this.environment.noSuchVariable(varName).message
    }

}