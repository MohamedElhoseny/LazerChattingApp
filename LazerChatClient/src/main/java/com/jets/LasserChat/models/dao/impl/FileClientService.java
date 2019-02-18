package com.jets.LasserChat.models.dao.impl;

import com.healthmarketscience.rmiio.RemoteInputStream;
import com.healthmarketscience.rmiio.RemoteInputStreamClient;
import com.jets.LasserChat.models.dao.FileServices;
import com.jets.LazerChatCommonService.models.entity.User;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class FileClientService extends UnicastRemoteObject implements FileServices
{
    public FileClientService() throws RemoteException
    { }

    /**
     * Describe what should do when a user receive a file from another user
     * @param fromUser the user send this file
     * @param senderFile the file is being uploaded
     * @param ristream remoteInputStream used to read file
     * @param name  name of file
     * @param extension extension of file
     */
    @Override
    public void receiveFile(User fromUser, File senderFile,
                            RemoteInputStream ristream, String name, String extension)
    {
        InputStream istream = null;
        try
        {
            istream = RemoteInputStreamClient.wrap(ristream);
            FileOutputStream ostream = null;
            try
            {
                File file = new File("C:\\Users\\omdae\\Downloads");
                File tempFile = File.createTempFile(name, ".".concat(extension), file);
                ostream = new FileOutputStream(tempFile);
                System.out.println("Writing file " + tempFile);

                byte[] buf = new byte[1024];

                int bytesRead = 0;
                while ((bytesRead = istream.read(buf)) >= 0)
                    ostream.write(buf, 0, bytesRead);

                ostream.flush();

                System.out.println("Finished writing file " + tempFile);

            } finally {
                try {
                    istream.close();
                } finally {
                    if (ostream != null) {
                        ostream.close();
                    }
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (istream != null)
                    istream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
