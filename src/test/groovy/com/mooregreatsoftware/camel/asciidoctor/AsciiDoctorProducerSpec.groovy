package com.mooregreatsoftware.camel.asciidoctor

import org.apache.camel.impl.DefaultCamelContext
import spock.lang.Specification
import spock.lang.Subject

@Subject(AsciiDoctorProducer)
class AsciiDoctorProducerSpec extends Specification {

    final camelContext = new DefaultCamelContext()
    final component = new AsciiDoctorComponent(camelContext: camelContext)
    final endpoint = new AsciiDoctorEndpoint('adoc:test', component, AsciiDoctorComponentSpec.asciidoctor)
    AsciiDoctorProducer producer


    def setup() {
        producer = new AsciiDoctorProducer(endpoint)
    }


    def cleanup() {
        producer?.stop()
        endpoint?.stop()
        component?.stop()
        camelContext?.stop()
    }


    final String inputString = "*This* is it."
    final String outputString = "<div class=\"paragraph\">\n" +
        "<p><strong>This</strong> is it.</p>\n" +
        "</div>"


    private process(input) {
        final exchange = producer.createExchange()
        exchange.in.body = input
        producer.process(exchange)
        exchange.out.body
    }


    def "render string"() {
        expect:
        process(inputString) == outputString
    }


    def "render Reader"() {
        expect:
        process(new StringReader(inputString)) == outputString
    }


    def "render File"() {
        File inputFile = File.createTempFile("asciidoc-camel", "adoc")
        inputFile.withWriter { Writer writer ->
            writer.write(inputString)
        }

        expect:
        process(inputFile) == outputString

        cleanup:
        inputFile?.delete()
    }

}
