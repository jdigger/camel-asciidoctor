package com.mooregreatsoftware.camel.asciidoctor

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.apache.camel.Consumer
import org.apache.camel.ExchangePattern
import org.apache.camel.Processor
import org.apache.camel.Producer
import org.apache.camel.impl.DefaultEndpoint
import org.asciidoctor.Asciidoctor

/**
 * An Endpoint for handling AsciiDoc, providing a {@link AsciiDoctorProducer}.
 *
 * Currently the URL has no meaning other than selecting this endpoint to create its Producer.
 *
 * This will check the Registry for an instance of {@link Asciidoctor} and use that if available.
 * Otherwise it will create its own instance.
 *
 * @see AsciiDoctorProducer
 */
@Slf4j
@CompileStatic
class AsciiDoctorEndpoint extends DefaultEndpoint {
    Asciidoctor asciidoctor


    AsciiDoctorEndpoint(String endpointUri, AsciiDoctorComponent component) {
        super(endpointUri, component)

        exchangePattern = ExchangePattern.InOnly

        initAsciiDoctor()
    }


    AsciiDoctorEndpoint(String endpointUri, AsciiDoctorComponent component, Asciidoctor asciidoctor) {
        super(endpointUri, component)

        exchangePattern = ExchangePattern.InOnly

        this.asciidoctor = asciidoctor
    }


    private void initAsciiDoctor() {
        final asciidoctors = camelContext.registry.findByType(Asciidoctor)
        if (asciidoctors.isEmpty()) {
            log.debug("Creating a new instance of Asciidoctor")
            asciidoctor = Asciidoctor.Factory.create()
        }
        else {
            if (asciidoctors.size() > 1) {
                throw new IllegalStateException("There is more than one instance of Asciidoctor in the registry: ${asciidoctors}")
            }
            asciidoctor = asciidoctors.first()
            log.debug("Using Asciidoctor instance from the Registry: ${asciidoctor}")
        }
    }


    @Override
    Producer createProducer() throws Exception {
        new AsciiDoctorProducer(this)
    }


    @Override
    Consumer createConsumer(Processor processor) throws Exception {
        null // does not provide a Consumer
    }


    @Override
    boolean isSingleton() {
        true
    }

}
