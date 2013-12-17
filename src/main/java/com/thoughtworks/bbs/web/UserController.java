
package com.thoughtworks.bbs.web;

import com.thoughtworks.bbs.model.Post;
import com.thoughtworks.bbs.model.User;
import com.thoughtworks.bbs.model.UserRole;
import com.thoughtworks.bbs.model.UserValidator;
import com.thoughtworks.bbs.service.PostService;
import com.thoughtworks.bbs.service.UserRoleService;
import com.thoughtworks.bbs.service.UserService;
import com.thoughtworks.bbs.service.impl.PostServiceImpl;
import com.thoughtworks.bbs.service.impl.UserRoleServiceImpl;
import com.thoughtworks.bbs.service.impl.UserServiceImpl;
import com.thoughtworks.bbs.util.MyBatisUtil;
import com.thoughtworks.bbs.util.UserBuilder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
    private UserRoleService userRoleService = new UserRoleServiceImpl(MyBatisUtil.getSqlSessionFactory());
    public void rstUserService(UserService userService) {
        this.userService=userService;
    }


    public void rstPostService(PostService postService) {
        this.postService = postService;
    }
    public void rstUserRoleService(UserRoleService userRoleService){
        this.userRoleService = userRoleService;
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
        model.addAttribute("posts", postService.getMainPostByAuthorName(principal.getName()));
        model.addAttribute("isMyself","true");

        return new ModelAndView("user/profile", map);
    }

    @RequestMapping(value = {"/users/{authorName}"}, method = RequestMethod.GET)
    public ModelAndView visitUserProfile(ModelMap model, Principal principal,@PathVariable("authorName") String authorName,@ModelAttribute Post post) {
        User user = userService.getByUsername(authorName);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user", user);
        map.put("posts", postService.getMainPostByAuthorName(authorName));
        if (null != principal&&principal.getName().equals(authorName))
            map.put("isMyself","true");
        else
            map.put("isNotMyself","true");
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

        model.addAttribute("posts", postService.getMainPostByAuthorName(principal.getName()));
        Map<String, User> map = new HashMap<String, User>();
        map.put("user", user);

        return new ModelAndView("user/profile", map);
    }

    @RequestMapping(value = {"/del/{postId}"}, method = RequestMethod.GET)
    public String delPostId(@PathVariable("postId") Long postId, Principal principal) {
        Post mainPost = postService.get(postId);
        String authorName = mainPost.getAuthorName();
        if (null != principal&&principal.getName().equals(authorName)){
            List<Post> postList=postService.findAllPostByMainPost(postId);
            for(Post post:postList){
                postService.delete(post);
            }
            return "redirect:/user/profile";
        }
        return "redirect:/login";
    }



    @RequestMapping(value = {"/updateProfile"}, method = RequestMethod.GET)
    public ModelAndView updateProfile(ModelMap model, Principal principal) {
        User user = userService.getByUsername(principal.getName());
        Map<String, User> map = new HashMap<String, User>();
        map.put("user", user);

        return new ModelAndView("user/updateProfile", map);
    }

    @RequestMapping(value = {"/users"}, method = RequestMethod.GET)
    public ModelAndView listUsers(ModelMap map) {
        List<User> users = userService.getAll();
        List<Long> usersNotAdmin = userRoleService.getAllNotAdmin();
        UserRole userRole = new UserRole();
        users=userService.setUsersIsRegular(users,usersNotAdmin);
        map.put("users",users);

        return new ModelAndView("user/users", map);
    }


    @RequestMapping(value = {"/updateProfile"}, method = RequestMethod.POST)
    public ModelAndView processUpdateProfile(ModelMap model, HttpServletRequest request, Principal principal) throws IOException {

        User user = userService.getByUsername(principal.getName());
        boolean isAltered =false;
        String oldUsername = principal.getName();
        String newUsername = request.getParameter("new username");

        UserValidator userValidator = new UserValidator();
        if(userValidator.usernameValidate(newUsername) && userService.verifyUsername(newUsername)){
            user.setUserName(newUsername);
            userService.update(user);
            postService.updateAllPostsAuthorByUserName(oldUsername, newUsername);
            isAltered =true;
            Authentication newToken = new UsernamePasswordAuthenticationToken(newUsername,user.getPasswordHash());
            SecurityContextHolder.getContext().setAuthentication(newToken);
            model.addAttribute("updateProfileSuccess", "true");
        }
        else{
            model.addAttribute("updateProfileFailed", "true");
        }

        model.addAttribute("posts", postService.getMainPostByAuthorName(user.getUserName()));   //altered

        Map<String, User> map = new HashMap<String, User>();
        map.put("user", user);
        if(isAltered)
            return new ModelAndView("user/profile",map);
        else
            return new ModelAndView("user/updateProfile",map);

    }


    @RequestMapping(value = {"/dis/{id}"}, method = RequestMethod.GET)
    public String disableUser(@PathVariable("id") Long userId, Principal principal) {
        User user = userService.getByUserId(userId);
        user.setEnabled(false);
        userService.update(user);

        return "redirect:/user/users";
    }
    @RequestMapping(value = {"/authorise/{id}"},method = RequestMethod.GET)
    public String authoriseUser(@PathVariable("id") Long userId,Principal principal){
        UserRole userRole = userRoleService.getByUserId(userId);
        userRole.setRoleName("ROLE_ADMIN");
        userRoleService.authoriseRoleName(userRole);

        return "redirect:/user/users";

    }
}
