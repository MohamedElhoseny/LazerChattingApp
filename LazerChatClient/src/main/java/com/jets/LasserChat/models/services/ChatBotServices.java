package com.jets.LasserChat.models.services;


import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;

import java.io.File;


public class ChatBotServices
{

    String botname = "super";
    String path = getResourcesPath();
    private Bot bot = new Bot(botname, path);
    Chat chatSession = new Chat(bot);

    public String responseBot(String  message){
        bot.brain.nodeStats();

        // user message
        String request = message;
        //user response
        String response = chatSession.multisentenceRespond(request);
        return response;
    }
    private static String getResourcesPath()
    {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        path = path.substring(0, path.length() - 2);
        System.out.println(path);
        String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
        return resourcesPath;
    }
}
