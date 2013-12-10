package com.thoughtworks.bbs.model;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class PostValidator {
    public Map<String, String> validate(Post post) {
        Map<String, String> errors = new HashMap<String, String>();

        if (StringUtils.isBlank(post.getAuthorName())) {
            errors.put("authorName", "Please enter author name");
        }

        if (StringUtils.isBlank(post.getTitle())) {
            errors.put("title", "Please enter post title");
        }

        if (StringUtils.isBlank(post.getContent())) {
            errors.put("content","Please enter post content");
        }
        return errors;
    }
}
