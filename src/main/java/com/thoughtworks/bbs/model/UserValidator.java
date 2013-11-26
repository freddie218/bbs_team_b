package com.thoughtworks.bbs.model;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class UserValidator {
    private Map<String, String> errors;

    public UserValidator() {
        errors = new HashMap<String, String>();
    }

    public Map<String, String> validate(User user) {
        if(StringUtils.isBlank(user.getUserName())) {
            errors.put("username", "Username can not be null");
        }

        if(StringUtils.isBlank(user.getPasswordHash())) {
            errors.put("password", "Password can not be null");
        }
        return errors;
    }

    public String passwordValidate(String password){
        if(StringUtils.isBlank(password)){
            return  "Password can not be null";
        }
        else if(password.length() < 6 || password.length() > 12){
            return "Password length must be 6~12";
        }
        else if((password.charAt(0) == '_')){
            return "Invalid password character!";
        }
        else{
            for(int i = 0; i < password.length(); i++)
            {
                if((password.charAt(i) >= 'a' && password.charAt(i) <= 'z')
                   || (password.charAt(i) >= 'A' && password.charAt(i) <= 'Z')
                   || (password.charAt(i) >= '0' && password.charAt(i) <= '9')
                   || (password.charAt(i) == '_')){
                    continue;
                }
                else{
                    return "Invalid password character!";
                }
            }
        }

        return null;
    }
}
