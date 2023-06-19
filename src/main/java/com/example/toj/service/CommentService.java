package com.example.toj.service;

import com.example.toj.mapper.CommentMapper;
import com.example.toj.pojo.Comment;
import com.example.toj.pojo.ParentComment;
import com.example.toj.pojo.SubComment;
import com.example.toj.pojo.User;
import com.example.toj.pojo.request.commentRequest.SubmitCommentRequest;
import com.example.toj.pojo.response.commentResponse.*;
import com.example.toj.pojo.response.object.MessageItem;
import com.example.toj.pojo.response.object.SubmitCommentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    final CommentMapper commentMapper;

    @Autowired
    public CommentService(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    public CommentsResponse getParentComments(Integer problemId, User user){
        CommentsResponse response = new CommentsResponse();
        List<ParentComment> parentCommentList = commentMapper.queryParentComments(problemId, user);

        if(parentCommentList == null){
            response.setSuccess(false);
            response.setMessage("获取评论失败：未知错误");
            return response;
        }

        response.setSuccess(true);
        response.setData(parentCommentList);
        return response;
    }

    public SubCommentResponse getSubComment(Integer parentId){
        SubCommentResponse response = new SubCommentResponse();
        List<SubComment> subComments = commentMapper.querySubComment(parentId);
        if(subComments == null){
            response.setSuccess(false);
            response.setMessage("获取子评论失败: 未知错误");
            return response;
        }

        response.setSuccess(true);
        response.setData(subComments);

        return response;
    }

    public SubmitCommentResponse submitComment(SubmitCommentRequest submitCommentRequest, User user){
        SubmitCommentResponse response = new SubmitCommentResponse();

        Comment comment = new Comment();
        comment.setUserId(user.getId());
        comment.setProblemId(submitCommentRequest.getProblemId());
        comment.setParentId(submitCommentRequest.getParentId());
        comment.setContent(submitCommentRequest.getContent());
        comment.setRead(false);

        Integer result = commentMapper.insertComment(comment);
        if(result == 0){
            response.setSuccess(false);
            response.setMessage("提交评论失败: 未知错误");
            return response;
        }

        SubmitCommentData data = new SubmitCommentData(comment.getParentId(), comment.getId());
        response.setData(data);
        response.setSuccess(true);

        return response;
    }

    public CommentLikeResponse likeComment(Integer commentId, User user, Boolean isLike){
        CommentLikeResponse response = new CommentLikeResponse();

        Integer results = commentMapper.updateCommentLike(commentId, user, isLike);
        if(results == 0){
            results = commentMapper.insertCommentLike(commentId, user, isLike);
            if(results == 0){
                response.setSuccess(false);
                response.setMessage("点赞失败: 未知错误");
                return response;
            }
        }

        Integer count = commentMapper.queryCommentLikeCount(commentId);
        response.setSuccess(true);
        response.setCount(count);

        return response;
    }

    public MessagesResponse getMessages(User user){
        MessagesResponse response = new MessagesResponse();
        List<MessageItem> messageItems = commentMapper.queryNoReadMessages(user.getId());

        if(messageItems == null){
            response.setMessage("获取未读消息错误: 未知错误");
            response.setSuccess(false);
            return response;
        }

        response.setMessages(messageItems);
        response.setSuccess(true);

        return response;
    }
}
