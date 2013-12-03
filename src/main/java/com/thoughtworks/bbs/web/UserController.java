package com.thoughtworks.bbs.web;

import com.thoughtworks.bbs.model.Post;
import com.thoughtworks.bbs.model.User;
import com.thoughtworks.bbs.model.UserValidator;
import com.thoughtworks.bbs.service.PostService;
import com.thoughtworks.bbs.service.UserService;
import com.thoughtworks.bbs.service.impl.PostServiceImpl;
import com.thoughtworks.bbs.service.impl.UserServiceImpl;
import com.thoughtworks.bbs.util.MyBatisUtil;
import com.thoughtworks.bbs.util.UserBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService = new UserServiceImpl(MyBatisUtil.getSqlSessionFactory());
    private PostService postService = new PostServiceImpl(MyBatisUtil.getSqlSessionFactory());

    public void rstUserService(UserService userService) {
        this.userService=userService;
    }


    public void rstPostService(PostService postService) {
        this.postService = postService;
    }





    @RequestMapping(value = {"/create"}, method = RequestMethod.GET)
    public ModelAndView registerUser(ModelMap model) {
        return new ModelAndView("user/register");
    }

    @RequestMapping(value = {"/profile"}, method = RequestMethod.GET)
    public ModelAndView userProfile(ModelMap model, Principal principal) {
        User user = userService.getByUsername(principal.getName());
        Map<String, User> map = new HashMap<String, User>();
        map.put("user", user);
        model.addAttribute("posts", postService.findMainPostByAuthorName(principal.getName()));

        return new ModelAndView("user/profile", map);
    }

    @RequestMapping(value = {"/create"}, method = RequestMethod.POST)
    public ModelAndView processCreate(HttpServletRequest request) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserBuilder builder = new UserBuilder();
        builder.userName(username).password(password).enable(true);

        userService.save(builder.build());
        User user = userService.getByUsername(username);

        Map<String, User> map = new HashMap<String, User>();
        map.put("user", user);

        return new ModelAndView("user/createSuccess", map);
    }

    @RequestMapping(value = {"/changePassword"}, method = RequestMethod.GET)
    public String changePassword() {
        return "user/changePassword";
    }

    @RequestMapping(value = {"/changePassword"}, method = RequestMethod.POST)
    public ModelAndView processChangePassword(ModelMap model, HttpServletRequest request, Principal principal){
        User user =  userService.getByUsername(principal.getName());
        UserValidator userValidator = new UserValidator();

        String oldPassword = request.getParameter("old password");
        String newPassword = request.getParameter("new password");
        String confirmPassword = request.getParameter("confirm password");

        if(userService.verifyPassword(user.getUserName(),oldPassword)
           && userValidator.passwordValidate(newPassword)
           && newPassword.equals(confirmPassword))
        {
            user.setPasswordHash(newPassword);
            model.addAttribute(userService.update(user).getErrors().isEmpty()?
            "success" : "failed", "true");
        }
        else
            model.addAttribute("failed", "true");

        model.addAttribute("posts", postService.findMainPostByAuthorName(principal.getName()));
        Map<String, User> map = new HashMap<String, User>();
        map.put("user", user);

        return new ModelAndView("user/profile", map);
    }

    @RequestMapping(value = {"/del/{postId}"}, method = RequestMethod.GET)
    public String delPostId(@PathVariable("postId") Long postId, Principal principal) {
        if (null == principal) {
            return "login";
        }
        List<Post> postList=postService.findAllPostByMainPost(postId);
        for(Post post:postList){
            postService.delete(post);
        }
        return "redirect:/user/profile";
    }



    @RequestMapping(value = {"/updateProfile"}, method = RequestMethod.GET)
    public ModelAndView updateProfile(ModelMap model, Principal principal) {
        User user = userService.getByUsername(principal.getName());
        Map<String, User> map = new HashMap<String, User>();
        map.put("user", user);

        return new ModelAndView("user/updateProfile", map);
    }
}
