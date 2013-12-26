package com.mooregreatsoftware.camel.asciidoctor

import groovy.transform.CompileStatic
import org.apache.camel.Endpoint
import org.apache.camel.impl.DefaultComponent

/**
 *
 */
@CompileStatic
class AsciiDoctorComponent extends DefaultComponent {

    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        return new AsciiDoctorEndpoint(uri, this)
    }

}
