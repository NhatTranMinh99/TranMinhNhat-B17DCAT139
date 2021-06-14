package com.ptit.tmn.Controller;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ReGex {

    public static boolean isGmailAddress(String emailAddress) {
        String expression = "^[\\w.+\\-]+@gmail\\.com$";
        CharSequence inputStr = emailAddress;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }
}
