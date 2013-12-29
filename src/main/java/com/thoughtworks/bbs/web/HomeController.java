package com.thoughtworks.bbs.web;

import com.thoughtworks.bbs.model.Like;
import com.thoughtworks.bbs.model.Post;
import com.thoughtworks.bbs.service.LikeService;
import com.thoughtworks.bbs.service.PostService;
import com.thoughtworks.bbs.service.UserRoleService;
import com.thoughtworks.bbs.service.UserService;
import com.thoughtworks.bbs.service.impl.LikeServiceImpl;
import com.thoughtworks.bbs.service.impl.PostServiceImpl;
import com.thoughtworks.bbs.service.impl.UserRoleServiceImpl;
import com.thoughtworks.bbs.service.impl.UserServiceImpl;
import com.thoughtworks.bbs.util.MyBatisUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class HomeController {
    PostService postService = new PostServiceImpl(MyBatisUtil.getSqlSessionFactory());
    UserService userService = new UserServiceImpl(MyBatisUtil.getSqlSessionFactory());
    LikeService likeService = new LikeServiceImpl(MyBatisUtil.getSqlSessionFactory());
    private UserRoleService userRoleService = new UserRoleServiceImpl(MyBatisUtil.getSqlSessionFactory());

    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model, Principal principal) {
        if (null == principal) {
            return "login";
        }
        List<Post> posts=postService.findAllPost();
        showHomepage(model, principal, posts);
        return "home";
    }

    private long showHomepage(Model model, Principal principal, List<Post> posts) {
        postService.sortByTopped(posts);
        Long uid = userService.getByUsername(principal.getName()).getId();
        Map<Post,Boolean> postsWithLiked= new LinkedHashMap<Post, Boolean>();
        List<Like> likes = likeService.findLikeByUserId(uid);

        long postsCount = 0;
        for (Post post : posts) {
            postsCount++;
            long pid=post.getPostId();
            Boolean liked=false;
            for (Like like : likes) {
                long like_pid=like.getPostId();
                if(pid==like_pid){
                    liked=true;
                }
            }
            postsWithLiked.put(post, liked);
        }
        model.addAttribute("postsWithLiked",postsWithLiked);
        model.addAttribute("isAdmin", userRoleService.isAdmin(userService.getByUsername(principal.getName()).getId()));
        return postsCount;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String searchPost(HttpServletRequest request, Model model, Principal principal) {
        if (null == principal) {
            return "login";
        }
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String author = request.getParameter("author");
        String timeLeft = request.getParameter("dp1");
        String timeRight = request.getParameter("dp2");

        List<Post> posts=postService.findRestrictedPost(title, content, author, timeLeft, timeRight);
        long postsCount=showHomepage(model, principal, posts);

        model.addAttribute("postsFountCount", postsCount);
        model.addAttribute("title", title);
        model.addAttribute("content", content);
        model.addAttribute("author", author);
        model.addAttribute("timeLeft", timeLeft);
        model.addAttribute("timeRight", timeRight);
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
