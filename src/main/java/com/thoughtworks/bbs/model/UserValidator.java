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
        invalid(user.getUserName(), "username", "Username");
        invalid(user.getPasswordHash(), "password", "Password");
        return errors;
    }

    private void invalid(String item, String key, String value) {
        if(StringUtils.isBlank(item)) {
            errors.put(key, value + " can not be null");
        }
    }

    public boolean passwordValidate(String password){
        return !(StringUtils.isBlank(password) || isLengthValid(password) || isFirstCharValid(password) || passwordCharValid(password));

    }

    private boolean passwordCharValid(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (!isCharValid(password.charAt(i))) return true;
        }
        return false;
    }

    private boolean isCharValid(char charAt) {
        return (charAt >= 'a' && charAt <= 'z')
                || (charAt >= 'A' && charAt <= 'Z')
                || (charAt >= '0' && charAt <= '9')
                || (charAt == '_');
    }

    private boolean isFirstCharValid(String password) {
        return password.charAt(0) == '_';
    }

    private boolean isLengthValid(String password) {
        return password.length() < 6 || password.length() > 12;
    }

    public boolean usernameValidate(String username) {
        return !StringUtils.isBlank(username);
    }
}
