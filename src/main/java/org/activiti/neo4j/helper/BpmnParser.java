package org.activiti.neo4j.helper;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Helper-class, provides utility to parse a BPMN process class
 *
 */
public class BpmnParser {

    /**
     * Parses the given BPM file in XML format and returns it as Model
     *
     * @param inputStream
     * @return
     */
    public static BpmnModel parse(final InputStream inputStream) {
        BpmnModel bpmnModel = null;

        try (InputStreamReader in = new InputStreamReader(inputStream, "UTF-8"))
        {
            XMLInputFactory xif = XMLInputFactory.newInstance();
            XMLStreamReader xtr = xif.createXMLStreamReader(in);
            BpmnXMLConverter converter = new BpmnXMLConverter();
            bpmnModel = converter.convertToBpmnModel(xtr);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bpmnModel;
    }
}