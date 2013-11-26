package com.thoughtworks.bbs.web;

import com.thoughtworks.bbs.model.User;
import com.thoughtworks.bbs.service.PostService;
import com.thoughtworks.bbs.service.UserService;
import com.thoughtworks.bbs.service.impl.PostServiceImpl;
import com.thoughtworks.bbs.service.impl.UserServiceImpl;
import com.thoughtworks.bbs.util.MyBatisUtil;
import com.thoughtworks.bbs.util.UserBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService = new UserServiceImpl(MyBatisUtil.getSqlSessionFactory());
    private PostService postService = new PostServiceImpl(MyBatisUtil.getSqlSessionFactory());

    public void srtUserService(UserService userService) {

        this.userService=new UserServiceImpl(MyBatisUtil.getSqlSessionFactory());

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
    public ModelAndView changePassword(ModelMap model) {
        return new ModelAndView("user/changePassword");
    }

    @RequestMapping(value = {"/changePassword"}, method = RequestMethod.POST)
    public String processChangePassword(ModelMap model, HttpServletRequest request, Principal principal) throws IOException {
        User user = userService.getByUsername(principal.getName());

        String oldPassword = request.getParameter("old password");
        String newPassword = request.getParameter("new password");
        String confirmPassword = request.getParameter("confirm password");

        model.addAttribute(userService.changePassword(user, oldPassword, newPassword, confirmPassword), "true");

        return "user/profile";
    }

    @RequestMapping(value = {"/ChangePassword"}, method = RequestMethod.POST)
    public ModelAndView ChangePassword(HttpServletRequest request,Principal principal) throws IOException {
        User user = userService.getByUsername(principal.getName());
        String password = request.getParameter("password");
        String newPassword = request.getParameter("confirmPassword");

        if(user.getPasswordHash().equals(password))
        {
            if (!user.getPasswordHash().equals(newPassword))
            {
                user.setPasswordHash(newPassword);
                userService.update(user);
                Map<String,User>map=new HashMap<String, User>();
                map.put("user",user);
                return new ModelAndView("user/profile",null).addObject("user",user).addObject("message","Password has been changed successfully!");
            }else {
                return new ModelAndView("user/ChangePassword",null).addObject("user",user).addObject("message","New password is the same with the old one! ");
            }

        } else {
              return  new ModelAndView("user/ChangePassword",null).addObject("user",user).addObject("message","Wrong Password!<a href='#'><strong>Can't change!</strong></a>");
        }
    }
}
