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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.rmi.RemoteException;
import java.util.List;

public class BackupChatServicesImp implements BackupChatServices
{
    @Override
    public void saveSession(List<Message> messages, File sessionXmlFile) throws RemoteException
    {
        try
        {
            JAXBContext context = JAXBContext.newInstance("com.jets.LasserChat.models.entity");
            ObjectFactory factory = new ObjectFactory();
            MessagesType messagesType = factory.createMessagesType();
            MessageType messageType = factory.createMessageType();
            for (Message t : messages)
            {

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


            }
            JAXBElement messagesJAXB = factory.createMessages(messagesType);

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(messagesJAXB, new FileOutputStream(sessionXmlFile));

        }
        catch (JAXBException | FileNotFoundException ex)
        {
            ex.printStackTrace();
        }
    }
}
