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
}
