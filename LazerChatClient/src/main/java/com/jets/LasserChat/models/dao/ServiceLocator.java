package com.jets.LasserChat.models.dao;

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
        if(service !=null)
            return service;

        Remote servicerequired = InitialContext.lookup(chosenService);

        cache.addService(chosenService, servicerequired);

        return servicerequired;
    }
}