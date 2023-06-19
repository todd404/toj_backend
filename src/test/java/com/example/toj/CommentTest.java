package com.example.toj;

import com.example.toj.mapper.CommentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommentTest {
    @Autowired
    CommentMapper commentMapper;

    @Test
    void queryCommentLikeCountTest(){
        var result = commentMapper.queryCommentLikeCount(4);
        return;
    }
}
