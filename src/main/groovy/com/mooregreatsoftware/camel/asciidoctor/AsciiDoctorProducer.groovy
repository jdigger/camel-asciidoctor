package com.mooregreatsoftware.camel.asciidoctor

import groovy.transform.CompileStatic
import org.apache.camel.Exchange
import org.apache.camel.impl.DefaultProducer
import org.asciidoctor.Asciidoctor

/**
 *
 */
@CompileStatic
class AsciiDoctorProducer extends DefaultProducer {
    Asciidoctor asciidoctor


    AsciiDoctorProducer(AsciiDoctorEndpoint endpoint) {
        super(endpoint)
        asciidoctor = endpoint.asciidoctor
    }


    @Override
    void process(Exchange exchange) throws Exception {
        final body = exchange.in.mandatoryBody
        final reader = exchange.context.typeConverter.mandatoryConvertTo(Reader, exchange, body)

        StringWriter writer = new StringWriter()

        asciidoctor.render(reader, writer, [:])

        exchange.out.body = writer.toString()
    }

}
