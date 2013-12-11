package com.thoughtworks.bbs.web;

import com.thoughtworks.bbs.model.Like;
import com.thoughtworks.bbs.model.Post;
import com.thoughtworks.bbs.model.User;
import com.thoughtworks.bbs.service.LikeService;
import com.thoughtworks.bbs.service.PostService;
import com.thoughtworks.bbs.service.UserService;
import com.thoughtworks.bbs.service.impl.LikeServiceImpl;
import com.thoughtworks.bbs.service.impl.PostServiceImpl;
import com.thoughtworks.bbs.service.impl.UserServiceImpl;
import com.thoughtworks.bbs.util.LikeBuilder;
import com.thoughtworks.bbs.util.MyBatisUtil;
import com.thoughtworks.bbs.util.PostBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    private PostService postService = new PostServiceImpl(MyBatisUtil.getSqlSessionFactory());
    private UserService userService = new UserServiceImpl(MyBatisUtil.getSqlSessionFactory());
    private LikeService likeService = new LikeServiceImpl(MyBatisUtil.getSqlSessionFactory());

    PostController(){

    }
    public PostController(PostService postService,UserService userService,LikeService likeService){
        this.postService = postService;
        this.userService = userService;
        this.likeService = likeService;
    }
    @RequestMapping(value = {"/{postId}"}, method = RequestMethod.GET)
    public String get(@PathVariable("postId") Long postId, Model model, @ModelAttribute Post post, Principal principal) {
        model.addAttribute("mainPost", postService.get(postId));
        model.addAttribute("posts", postService.findAllPostByMainPost(postId));

        Long uid = userService.getByUsername(principal.getName()).getId();
        boolean liked=likeService.isUserLikesPost(uid,postId);
        model.addAttribute("liked",liked);
        return "posts/show";
    }


    @RequestMapping(value = {"/{postId}"}, method = RequestMethod.POST)
    public String postShow(HttpServletRequest request, Principal principal,Model model,@PathVariable("postId") Long postId) {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String parentId = request.getParameter("parentId");

        Long parentIdLong = 0L;

        if(StringUtils.isEmpty(content)){
         model.addAttribute("failed", "content is empty");
//            JOptionPane.showMessageDialog(null,"in if");

        }
        if (!StringUtils.isEmpty(parentId)) {
            parentIdLong = Long.parseLong(parentId);
        }

        User currentUser = userService.getByUsername(principal.getName());

        PostBuilder builder = new PostBuilder();
        builder.title(title).content(content).author(currentUser.getUserName()).parentId(parentIdLong).creatorId(currentUser.getId())
                .modifierId(currentUser.getId()).createTime(new Date()).modifyTime(new Date()).likedTimes(0L);

        postService.save(builder.build());
        model.addAttribute("mainPost", postService.get(postId));
        model.addAttribute("posts", postService.findAllPostByMainPost(postId));
        // return new ModelAndView("posts/createSuccess");
        //return new ModelAndView("home", "posts", postService.findMainPostByAuthorName(principal.getName()));
        return "posts/show";
    }

    @RequestMapping(value = {"/create"}, method = RequestMethod.GET)
    public ModelAndView createPostForm(ModelMap model, Principal principal) {
        return new ModelAndView("posts/create");
    }

    @RequestMapping(value = {"/create"}, method = RequestMethod.POST)
    public RedirectView processCreate(HttpServletRequest request, Principal principal) throws IOException {
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String parentId = request.getParameter("parentId");

        Long parentIdLong = 0L;
        if (!StringUtils.isEmpty(parentId)) {
            parentIdLong = Long.parseLong(parentId);
        }
        User currentUser = userService.getByUsername(principal.getName());

        PostBuilder builder = new PostBuilder();
        builder.title(title).content(content).author(currentUser.getUserName()).parentId(parentIdLong).creatorId(currentUser.getId())
                .modifierId(currentUser.getId()).createTime(new Date()).modifyTime(new Date()).likedTimes(0L);

        postService.save(builder.build());
            // return new ModelAndView("posts/createSuccess");
            //return new ModelAndView("home", "posts", postService.findMainPostByAuthorName(principal.getName()));
        return new RedirectView("../");
        }
    @RequestMapping(value = {"/like/{postId}"}, method = RequestMethod.GET)
    public String add1LikedTime(@PathVariable("postId") Long id,@RequestHeader("Referer") String referer, Principal principal) {
        if(principal == null){
            return "login";
        }

        User user = userService.getByUsername(principal.getName());
        Long uid = user.getId();
        Post post = postService.get(id);
        Long pid = post.getParentId();

        if(likeService.isUserLikesPost(uid,pid)) {
            return "login";
        }

        LikeBuilder likeBuilder=new LikeBuilder().postId(id).parentId(pid).userId(uid);

        Like like=likeBuilder.build();

        likeService.save(like);
        postService.add1LikedTime(id);

        return "redirect:"+ referer;
    }
}
