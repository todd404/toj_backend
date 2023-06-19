package com.example.toj.controller;

import com.example.toj.pojo.User;
import com.example.toj.pojo.request.commentRequest.CommentLikeRequest;
import com.example.toj.pojo.request.commentRequest.SubmitCommentRequest;
import com.example.toj.pojo.response.commentResponse.CommentLikeResponse;
import com.example.toj.pojo.response.commentResponse.CommentsResponse;
import com.example.toj.pojo.response.commentResponse.SubCommentResponse;
import com.example.toj.pojo.response.commentResponse.SubmitCommentResponse;
import com.example.toj.service.CommentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {
    final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comments")
    public CommentsResponse comments(@RequestParam("problem_id") Integer problemId,
                                     HttpSession session){
        User user = (User) session.getAttribute("user");
        user = (user == null) ? new User() : user;

        return commentService.getParentComments(problemId, user);
    }

    @GetMapping("/sub_comment")
    public SubCommentResponse subComment(@RequestParam("parent_id") Integer parentId){
        return commentService.getSubComment(parentId);
    }

    @PostMapping("submit_comment")
    public SubmitCommentResponse submitComment(@RequestBody SubmitCommentRequest submitCommentRequest,
                                               HttpSession session)
    {
        User user =  (User) session.getAttribute("user");
        if(user == null){
            SubmitCommentResponse response = new SubmitCommentResponse();
            response.setSuccess(false);
            response.setMessage("提交评论失败: 未登录");
            return response;
        }

        return commentService.submitComment(submitCommentRequest, user);
    }

    @PostMapping("like_comment")
    public CommentLikeResponse likeComment(@RequestBody CommentLikeRequest commentLikeRequest,
                                           HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        if(user == null){
            return new CommentLikeResponse(false, "点赞失败: 未登录");
        }

        return commentService.likeComment(commentLikeRequest.getCommentId(), user, commentLikeRequest.getLike());
    }
}
