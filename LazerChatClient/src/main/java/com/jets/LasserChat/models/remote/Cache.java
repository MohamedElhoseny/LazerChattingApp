package com.jets.LasserChat.models.remote;
import java.rmi.Remote;
import java.util.HashMap;

public class Cache
{
    private HashMap<String, Remote> services;

    public Cache(){
        services = new HashMap<>();
    }

    public Remote getService(String serviceName)
    {
        for (String service: services.keySet()) {
            if (service.equalsIgnoreCase(serviceName))
                return services.get(service);
        }
        return null;
    }

    public void addService(String name, Remote newService){
        boolean exists = false;

        if(services.containsKey(name))
            exists = true;

        if(!exists){
            services.put(name, newService);
        }
    }
}