package com.thoughtworks.bbs.web;

import com.thoughtworks.bbs.model.Like;
import com.thoughtworks.bbs.model.Post;
import com.thoughtworks.bbs.service.LikeService;
import com.thoughtworks.bbs.service.PostService;
import com.thoughtworks.bbs.service.UserService;
import com.thoughtworks.bbs.service.impl.LikeServiceImpl;
import com.thoughtworks.bbs.service.impl.PostServiceImpl;
import com.thoughtworks.bbs.service.impl.UserServiceImpl;
import com.thoughtworks.bbs.util.MyBatisUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class HomeController {
    PostService postService = new PostServiceImpl(MyBatisUtil.getSqlSessionFactory());
    UserService userService = new UserServiceImpl(MyBatisUtil.getSqlSessionFactory());
    LikeService likeService = new LikeServiceImpl(MyBatisUtil.getSqlSessionFactory());
    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model, Principal principal) {
        if (null == principal) {
            return "login";
        }

        List<Post> posts=postService.findAllPost();

        Long uid = userService.getByUsername(principal.getName()).getId();

        Map<Post,Boolean> postsWithLiked= new LinkedHashMap<Post, Boolean>();

        for (Post post : posts) {
            postsWithLiked.put(post, likeService.isFavorite(uid,post.getPostId()));
        }
        model.addAttribute("postsWithLiked",postsWithLiked);
        return "home";
    }

    public void rstPostService(PostService service){
        postService = service;
    }
    public void rstUserService(UserService service){
        userService = service;
    }
    public void rstLikeService(LikeService service){
        likeService = service;
    }
}
