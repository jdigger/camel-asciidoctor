package com.mooregreatsoftware.camel.asciidoctor

import org.apache.camel.ExchangePattern
import org.apache.camel.Processor
import org.apache.camel.impl.DefaultCamelContext
import spock.lang.Specification
import spock.lang.Subject

@Subject(AsciiDoctorEndpoint)
class AsciiDoctorEndpointSpec extends Specification {

    final camelContext = new DefaultCamelContext()
    final component = new AsciiDoctorComponent(camelContext: camelContext)
    AsciiDoctorEndpoint endpoint


    def setup() {
        endpoint = new AsciiDoctorEndpoint('adoc:test', component, AsciiDoctorComponentSpec.asciidoctor)
    }


    def cleanup() {
        endpoint?.stop()
        component?.stop()
        camelContext?.stop()
    }


    def "producer"() {
        expect:
        endpoint.createProducer() instanceof AsciiDoctorProducer
    }


    def "consumer"() {
        expect:
        endpoint.createConsumer({} as Processor) == null
    }


    def "exchange pattern"() {
        expect:
        endpoint.exchangePattern == ExchangePattern.InOnly
    }

}
