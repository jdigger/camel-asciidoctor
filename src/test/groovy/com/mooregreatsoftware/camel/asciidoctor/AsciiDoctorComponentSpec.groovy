package com.mooregreatsoftware.camel.asciidoctor

import org.apache.camel.CamelContext
import org.apache.camel.impl.DefaultCamelContext
import org.apache.camel.impl.SimpleRegistry
import org.asciidoctor.Asciidoctor
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

@Subject(AsciiDoctorComponent)
class AsciiDoctorComponentSpec extends Specification {

    static final Asciidoctor asciidoctor = Asciidoctor.Factory.create()

    @Shared CamelContext camelContext


    def setupSpec() {
        final registry = new SimpleRegistry()
        registry.put("asciidoctor", asciidoctor)

        camelContext = new DefaultCamelContext(registry)
        camelContext.disableJMX()
    }


    def cleanupSpec() {
        camelContext?.stop()
    }


    def "Component name is registered"() {
        expect:
        camelContext.getComponent('adoc') instanceof AsciiDoctorComponent
    }


    def "can create an endpoint"() {
        expect:
        new AsciiDoctorComponent(camelContext: camelContext).createEndpoint('adoc:render') instanceof AsciiDoctorEndpoint
    }

}
