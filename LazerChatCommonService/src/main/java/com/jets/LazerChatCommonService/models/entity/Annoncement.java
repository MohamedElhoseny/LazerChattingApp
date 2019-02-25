package com.jets.LazerChatCommonService.models.entity;

import java.io.Serializable;

public class Annoncement implements Serializable
{
    String annoncementText;
    byte[] image;

    public String getAnnoncementText() {
        return annoncementText;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setAnnoncementText(String annoncementText) {
        this.annoncementText = annoncementText;
    }
}