package com.jets.LasserChat.models.remote;

import java.rmi.Remote;

public class ServiceLocator
{
    private static Cache cache;

    static {
        cache = new Cache();
    }

    public static Remote getService(String chosenService)
    {
        Remote service = cache.getService(chosenService);
        //Service is already located in cashe
        if(service != null)
            return service;

        Remote servicerequired = InitialContext.lookup(chosenService);

        if (servicerequired == null)
            System.out.println("new required Services is null");
        else
            cache.addService(chosenService, servicerequired);

        return servicerequired;
    }
}