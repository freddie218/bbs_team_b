package com.thoughtworks.bbs.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserValidatorTest {
    private UserValidator validator;
    private User user;

    @Before
    public void setup(){
        validator = new UserValidator();

        user = new User();
        user.setUserName("user");
        user.setPasswordHash("xxxxxxxxxxxxxxxx");
    }

    @Test
    public void shouldHaveUsernameAndPassword(){
        Map<String, String> errors = validator.validate(user);

        assertThat(errors.size(), is(0));
    }

    @Test
    public void shouldHaveUsername(){
        user.setUserName(null);
        Map<String, String> errors = validator.validate(user);

        assertThat(errors.get("username"), is("Username can not be null"));
    }

    @Test
    public void shouldHavePassword(){
        user.setPasswordHash(null);
        Map<String, String> errors = validator.validate(user);

        assertThat(errors.get("password"), is("Password can not be null"));
    }

    @Test
    public void shouldReturnTrueWhenPasswordFormatCorrect(){
        assertThat("password correct", true == validator.passwordValidate("123456"));
    }

    @Test
    public void shouldReturnFalseWhenPasswordTooShort() throws Exception {
        assertThat("password length should not be less than 6",
                !validator.passwordValidate("123"));
    }

    @Test
    public void shouldReturnFalseWhenPasswordTooLong() throws Exception {
        assertThat("password length should not be more than 12",
                !validator.passwordValidate("123456789123456789"));
    }

    @Test
    public void shouldReturnFalseWhenPasswordStartsWithUnderLine() throws Exception {
        assertThat("password should not start with '_'",
                !validator.passwordValidate("_123456"));
    }

    @Test
    public void shouldReturnFalseWhenPasswordContainsIllegalCharacter(){
        assertThat("password can only contains alphabet, numbers and '_'",
                !validator.passwordValidate("!@123456"));
    }
}
