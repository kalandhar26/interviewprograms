package com.ds.Arrays;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;

public class SelfServiceStepDef {

    // Existing methods...

   // @Given("Extract FileName from \"([^\"]*)\" and generate Primitive Request XML")
    public void extractFileNameAndGenerateRequestXML(String primitiveNotificationFile) throws Exception {
        // Step 1: Extract FileName from the Primitive Notification XML
        String fileName = extractFileNameFromXML(primitiveNotificationFile);

        // Step 2: Create the new Primitive Request XML
        createPrimitiveRequestXML(fileName);
    }

    private String extractFileNameFromXML(String filePath) throws Exception {
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        // Extract FileName
        Element fileNameElement = (Element) doc.getElementsByTagName("FileName").item(0);
        return fileNameElement.getTextContent();
    }

    private void createPrimitiveRequestXML(String fileName) throws Exception {
        // Create the new XML Document
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();

        // Root element
        Element rootElement = doc.createElement("ReferenceDataFileRequest");
        rootElement.setAttribute("xmlns", "urn:swift:xxxx:primitives:v1");
        doc.appendChild(rootElement);

        // Generate a unique RequestId
        String requestId = generateUniqueRequestId();

        // RequestId element
        Element requestIdElement = doc.createElement("RequestId");
        requestIdElement.appendChild(doc.createTextNode(requestId));
        rootElement.appendChild(requestIdElement);

        // FileName element
        Element fileNameElement = doc.createElement("FileName");
        fileNameElement.appendChild(doc.createTextNode(fileName));
        rootElement.appendChild(fileNameElement);

        // Write the content into an XML file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("output/Primitive_Request.xml")); // Specify the output path
        transformer.transform(source, result);
    }

    private String generateUniqueRequestId() {
        // Define the segments of the RequestId
        String segment1 = generateRandomString(5); // 5 characters
        String segment2 = generateRandomString(5); // 5 characters
        String segment3 = String.valueOf(generateRandomDigit()); // 1 numeric
        String segment4 = generateRandomString(8); // 8 characters
        String segment5 = String.valueOf(generateRandomDigit()); // 1 numeric

        // Combine segments to form the RequestId
        return segment1 + "-" + segment2 + "-" + segment3 + "-" + segment4 + "-" + segment5;
    }

    private String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder result = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            result.append(characters.charAt(random.nextInt(characters.length())));
        }
        return result.toString();
    }

    private int generateRandomDigit() {
        SecureRandom random = new SecureRandom();
        return random.nextInt(10); // Generates a random digit between 0 and 9
    }
}