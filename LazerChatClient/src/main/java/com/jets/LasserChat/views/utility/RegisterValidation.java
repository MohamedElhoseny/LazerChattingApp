/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jets.LasserChat.views.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author mai
 */
public class RegisterValidation {

    Matcher matcher;
    public static final Pattern VALID_EMAIL_REGEX = Pattern.compile("[A-Z0-9a-z._%+-]+@[A-Z0-9a-z.-]+.[A-Za-z]{2,64}");
    public static final Pattern VALID_NAME_REGEX = Pattern.compile("^[a-zA-Z]+[a-zA-Z ]*$");
    private static final Pattern VALID_PASSWORD_REGEX = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_PHONE_REGEX = Pattern.compile("\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}");

    public boolean validateEmail(String email) {
        matcher = VALID_EMAIL_REGEX.matcher(email);
        return matcher.find();
    }

    public boolean validateName(String name) {
        matcher = VALID_NAME_REGEX.matcher(name);
        return matcher.find();
    
    }
    public boolean validatePassword(String password){
        matcher = VALID_PASSWORD_REGEX.matcher(password);
        return matcher.find();
    }

    public  boolean validatePhone(String phone){
        if (phone.length() > 11)
            return false;
        else
            matcher = VALID_PHONE_REGEX.matcher(phone);
        return matcher.find();
    }
       
    public boolean matchPassword(String password1,String password2){
            return (password1.equals(password2));
    }
}
