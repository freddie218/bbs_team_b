package com.thoughtworks.bbs.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class PostValidatorTest {
    private PostValidator validator;
    private Post post;

    @Before
    public void setup(){
        validator = new PostValidator();

        post = new Post();
        post.setAuthorName("Juntao");
        post.setTitle("Introduce to TDD");
    }

    @Test
    public void postShouldHaveAuthorAndTitle(){
        Map<String, String> errors = validator.validate(post);
        assertThat(errors.size(), is(0));
    }

    @Test
    public void shouldHasAnAuthor(){
        post.setAuthorName(null);
        Map<String, String> errors = validator.validate(post);

        assertThat(errors.get("authorName"), is("Please enter author name"));
    }

    @Test
    public void shouldShouldHasATitle(){
        post.setTitle(null);
        Map<String, String> errors = validator.validate(post);

        assertThat(errors.get("title"), is("Please enter post title"));
    }
}
