package com.volcanapp.volcanapp.funciones;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidacionCampos {


    public boolean validateFields(String value){
        Boolean validaction =false;
        if (!value.isEmpty()){
            validaction =true;
        }
        return validaction;
    }

    public boolean validateName(String name){
        Boolean validaction =false;
        Pattern pattern = Pattern.compile("[a-zA-Z\\u00C0-\\u017F\\s]{3,}");
        Matcher mat = pattern.matcher(name);
        if (mat.matches()) {
            validaction = true;
        }
        return validaction;
    }

    public boolean validateEmail(String email){
        Boolean validaction =false;
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mat = pattern.matcher(email);
        if (mat.matches()) {
            validaction = true;
        }
        return validaction;
    }
    /*
    public boolean validatePass(String password){
        Boolean validaction =false;
        Pattern pattern = Pattern
                .compile("^(?=.[0-9])(?=.[a-z])(?=.[A-Z])(?=.[.@#$%^&+=])(?=\\S+$).{8,}$");
        Matcher mat = pattern.matcher(password);
        if (mat.matches()) {
            validaction = true;
        }
        return validaction;
    }

     */

    public boolean validatePass(String password){
        Boolean validaction =false;
        Pattern pattern = Pattern.compile("[a-zA-Z0-9.@#$%^&+=]{8,}");
                Matcher mat = pattern.matcher(password);
        if (mat.matches()) {

            char ch;
            boolean capitalFlag = false;
            boolean lowerCaseFlag = false;
            boolean numberFlag = false;
            for(int i=0;i < password.length();i++) {
                ch = password.charAt(i);
                if( Character.isDigit(ch)) {
                    numberFlag = true;
                }
                else if (Character.isUpperCase(ch)) {
                    capitalFlag = true;
                } else if (Character.isLowerCase(ch)) {
                    lowerCaseFlag = true;
                }
                if(numberFlag && capitalFlag && lowerCaseFlag)
                    return true;
            }
            return false;

                    }
        return validaction;
    }





}

/*
            System.out.println(password.contains("[a-zA-Z]+"));
            if (password.contains("[a-zA-Z]+")) {
                System.out.println(password.matches("[0-9]+"));
                if (password.matches("[0-9]+")) {
                    validaction = true;
                }
            }

             */
