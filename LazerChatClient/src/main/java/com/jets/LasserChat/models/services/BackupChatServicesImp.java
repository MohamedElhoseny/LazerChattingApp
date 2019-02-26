package com.jets.LasserChat.models.services;


import com.jets.LasserChat.models.entity.BodyType;
import com.jets.LasserChat.models.entity.MessageType;
import com.jets.LasserChat.models.entity.MessagesType;
import com.jets.LasserChat.models.entity.ObjectFactory;
import com.jets.LazerChatCommonService.models.entity.Message;
import com.jets.LazerChatCommonService.models.entity.User;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.rmi.RemoteException;
import java.util.List;

public class BackupChatServicesImp implements BackupChatServices
{
    @Override
    public void saveSession(List<Message> messages, File sessionXmlFile) throws RemoteException
    {
        try {

            File xmlFile = new File("XML.xml");
            if (!xmlFile.exists()) {
                xmlFile.createNewFile();
            }
            JAXBContext context = JAXBContext.newInstance("messagewriting");
            ObjectFactory factory = new ObjectFactory();
            MessagesType messagesType = factory.createMessagesType();

            messages.forEach((t) -> {
                MessageType messageType = factory.createMessageType();
                System.out.println("messagetype" + messageType);
                BodyType bodyType = factory.createBodyType();
                bodyType.setValue(t.getMessageString());
                bodyType.setColor(t.getMessageStyle().getColor());
                bodyType.setFontFamily(t.getMessageStyle().getFontFamily());
                bodyType.setSize(t.getMessageStyle().getSize());
                bodyType.setIsBold(t.getMessageStyle().isBold());
                bodyType.setIsItalic(t.getMessageStyle().isItalic());
                bodyType.setIsUnderline(t.getMessageStyle().isUnderline());
                messageType.setBody(bodyType);
                messageType.setData(t.getDate_time());
                messageType.setFrom(t.getUser().getName());
                messageType.setState(t.getState().name());
                messagesType.getMessage().add(messageType);

            });
            JAXBElement messagesJAXB = factory.createMessages(messagesType);

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(messagesJAXB, new FileOutputStream("XML.xml"));

            File xslt = new File("stylesheet.xsl");
            int dotPos = sessionXmlFile.getAbsolutePath().lastIndexOf(".");

            File html;
            if (dotPos == -1) {
                html = new File(sessionXmlFile.getAbsolutePath().concat(".html"));

            } else {
                String strFilename = sessionXmlFile.getAbsolutePath().substring(0, dotPos-1);
                html = new File(strFilename.concat(".html"));

            }
            if(!html.exists())
                html.createNewFile();

            sessionXmlFile.deleteOnExit();



            TransformerFactory transformerFactory = TransformerFactory.newInstance();

            // Use the factory to create a template containing the xsl file
            Templates template = transformerFactory.newTemplates(new StreamSource(
                    new FileInputStream("stylesheet.xsl")));

            // Use the template to create a transformer
            Transformer xformer = template.newTransformer();

            // Prepare the input and output files
            Source source = new StreamSource(new FileInputStream("XML.xml"));
            Result result = new StreamResult(new FileOutputStream(html));

            // Apply the xsl file to the source file and write the result
            // to the output file
            xformer.transform(source, result);
            xmlFile.deleteOnExit();
            System.out.println("Done!");

        } catch (JAXBException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (TransformerConfigurationException ex) {
            ex.printStackTrace();
        } catch (TransformerException ex) {
            // An error occurred while applying the XSL file
            // Get location of error in input file
            SourceLocator locator = ex.getLocator();
            int col = locator.getColumnNumber();
            int line = locator.getLineNumber();
            String publicId = locator.getPublicId();
            String systemId = locator.getSystemId();
        }
    }

    public static void xsl(String inFilename, String outFilename, String xslFilename) {
        try {
            // Create transformer factory
            TransformerFactory factory = TransformerFactory.newInstance();

            // Use the factory to create a template containing the xsl file
            Templates template = factory.newTemplates(new StreamSource(
                    new FileInputStream(xslFilename)));

            // Use the template to create a transformer
            Transformer xformer = template.newTransformer();

            // Prepare the input and output files
            Source source = new StreamSource(new FileInputStream(inFilename));
            Result result = new StreamResult(new FileOutputStream(outFilename));

            // Apply the xsl file to the source file and write the result
            // to the output file
            xformer.transform(source, result);
        } catch (FileNotFoundException e) {
        } catch (TransformerConfigurationException e) {
            // An error occurred in the XSL file
        } catch (TransformerException e) {

        }
    }
}
