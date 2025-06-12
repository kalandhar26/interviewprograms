package com.ds.Arrays;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class XMLContentRead {

    public static void main(String[] args) throws Exception {
        String input = "<ReferenceDataFileNotification xmlns='urn:swift:xxxx:primitives:v1'>\r\n  <FileName>AUS_NNP_ReferenceData.xml</FileName>\r\n  <FileSize>1104051</FileSize>\r\n</ReferenceDataFileNotification>";

        System.out.println(getFileNameFromString(input));
    }

    public static String getFileNameFromString(String xmlContent) throws Exception {
        // Convert string to InputStream
        InputStream inputStream = new ByteArrayInputStream(xmlContent.getBytes("UTF-8"));

        // Parse XML
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(true); // Enable namespace awareness
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputStream);
        doc.getDocumentElement().normalize();

        // Extract FileName
        Element fileNameElement = (Element) doc.getElementsByTagNameNS("urn:swift:xxxx:primitives:v1", "FileName").item(0);
        if (fileNameElement != null) {
            return fileNameElement.getTextContent();
        } else {
            throw new Exception("FileName element not found in the XML.");
        }
    }
}
