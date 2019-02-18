package com.jets.LasserChat.models.dao.impl;

import com.jets.LasserChat.models.dao.CallServices;
import com.jets.LazerChatCommonService.models.entity.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CallClientService extends UnicastRemoteObject implements CallServices
{
    public CallClientService() throws RemoteException
    { }

    @Override
    public void startVoiceCall(User toUser) {

    }

    @Override
    public void endVoiceCall(User toUser) {

    }

    @Override
    public void startVedioCall(User toUser) {

    }

    @Override
    public void endVedioCall(User toUser) {

    }
}
