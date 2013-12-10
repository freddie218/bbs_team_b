package com.thoughtworks.bbs.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jesse on 12/11/13.
 */
public class LikeValidator {
    public Map<String, String> validate(Like like) {
        Map<String, String> errors = new HashMap<String, String>();

        if (like.getPostId()<=like.getParentId()) {
            errors.put("id", "U cannot reply to the future");
        }

        return errors;
    }
}
