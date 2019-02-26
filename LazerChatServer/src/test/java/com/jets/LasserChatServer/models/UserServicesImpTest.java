/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.LasserChatServer.models;

import com.jets.LazerChatCommonService.models.dao.UserServices;
import com.jets.LazerChatCommonService.models.entity.User;
import junit.framework.TestCase;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;

/**
 *
 * @author maimo
 */
public class UserServicesImpTest extends TestCase {

    public UserServicesImpTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of logIn method, of class UserServicesImp.
     */
    public void testLogIn() {
        System.out.println("logIn");
        String phone = "01097706982";
        String password = "@Hager2015";
        UserServicesImp instance = null;
        try {
            instance = new UserServicesImp();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        User user=new User();
        user.setId(6);
        user.setName("ahmed");
        user.setBio("java");
        user.setCountry("egypt");
        user.setEmail("m@gmail.com");
        user.setGender("male");
        user.setPassword(password);
        user.setPhone(phone);
        user.setDate("1996-10-10");

        User expResult = user;
        User result = instance.logIn(phone, password);
        assertEquals(expResult, result);
        System.out.println("well tested Login");


    }

    public void testRegister() throws RemoteException {
        System.out.println("register");
        User user = new User();
        user.setId(10);
        user.setName("Maii");
        user.setDate("1996/10/10");
        user.setPhone("01156797891");
        user.setPassword("mai@2018");
        user.setGender("female");
        user.setEmail("mai@gmaill.com");
        user.setCountry("egypt");
        user.setBio("jetsss");
        File defaultImg = new File("E:\\FCIH\\ITI\\JavaSE\\Project\\LazerChattingApp\\LazerChatServer\\src\\main\\resources\\images\\admin.jpg");
        byte[]img= convertImageToBytes(defaultImg);
        user.setPicture(img);
        UserServicesImp instance = new UserServicesImp();
        boolean expResult = true;
        boolean result = instance.register(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
        System.out.println("Well Tested Registration");
    }
    public void testUpdateProfile() throws RemoteException {
        System.out.println("updateProfile");
        User user = new User();

        user.setId(6);
        user.setName("ahmed");
        user.setBio("java");
        user.setCountry("egypt");
        user.setEmail("m@gmail.com");
        user.setGender("male");
        user.setPassword("@Hager2015");
        user.setPhone("01097706982");
        user.setDate("1996-10-10");

        File defaultImg = new File("E:\\FCIH\\ITI\\JavaSE\\Project\\LazerChattingApp\\LazerChatServer\\src\\main\\resources\\images\\admin.jpg");
        byte[]img= convertImageToBytes(defaultImg);
        user.setPicture(img);
        UserServicesImp instance = new UserServicesImp();
        boolean expResult =false;
        boolean result = instance.updateProfile(user);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
        System.out.println("Well Tested Update user dataa");
    }

    private byte[] convertImageToBytes(File choosenImg)
    {
        BufferedImage image = null;
        try {
            image = ImageIO.read(choosenImg);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            return null;
        }
    }


    /**
     * Test of logOut method, of class UserServicesImp.
     */

}