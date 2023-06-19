package com.example.toj.mapper;

import com.example.toj.pojo.Comment;
import com.example.toj.pojo.ParentComment;
import com.example.toj.pojo.SubComment;
import com.example.toj.pojo.User;
import com.example.toj.pojo.response.object.MessageItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Select("""
            SELECT
                c.id,
                c.user_id,
                u.username,
                COALESCE(l.is_like, 0) AS is_like,
                c.content,
                COALESCE(l_count.like_count, 0) AS like_count,
                COALESCE(s_count.sub_comment_count, 0) AS sub_comment_count
            FROM
                oj.comment AS c
            JOIN
                oj.user AS u ON c.user_id = u.id
            LEFT JOIN
                (SELECT comment_id, is_like FROM comment_like WHERE user_id = #{user.id}) AS l ON l.comment_id = c.id
            LEFT JOIN
                (SELECT comment_id, sum(is_like) AS like_count FROM comment_like GROUP BY comment_id) AS l_count ON l_count.comment_id = c.id
            LEFT JOIN
                (SELECT parent_id, COUNT(parent_id) AS sub_comment_count FROM comment GROUP BY parent_id) AS s_count ON s_count.parent_id = c.id
            WHERE
                c.problem_id = #{problemId} AND c.parent_id = 0;""")
    List<ParentComment> queryParentComments(@Param("problemId") Integer problemId, @Param("user") User user);

    @Select("""
            select c.*, u.username from comment as c
            left join user as u on c.user_id = u.id
            where parent_id = #{parentId}
            """)
    List<SubComment> querySubComment(@Param("parentId") Integer parentId);

    @Insert("insert into comment (user_id, problem_id, parent_id, content, is_read) " +
            "VALUES (#{userId}, #{problemId}, #{parentId}, #{content}, false)")
    @Options(keyProperty = "id", useGeneratedKeys = true)
    Integer insertComment(Comment comment);

    @Update("update comment_like set is_like = #{isLike} where comment_id = #{commentId} and user_id = #{user.id}")
    Integer updateCommentLike(@Param("commentId") Integer commentId,
                              @Param("user") User user,
                              @Param("isLike") Boolean isLike);

    @Insert("insert into comment_like (comment_id, user_id, is_like) values (#{commentId}, #{user.id}, #{isLike})")
    Integer insertCommentLike(@Param("commentId") Integer commentId,
                              @Param("user") User user,
                              @Param("isLike") Boolean isLike);

    @Select("select sum(is_like) from comment_like where comment_id = #{commentId}")
    Integer queryCommentLikeCount(@Param("commentId") Integer commentId);

    @Select("""
            select c.*, p.title from comment as c
            left join problem as p on p.id = c.problem_id
            where parent_id in (select id from comment where user_id = #{userId} and parent_id = 0)
            and is_read = false;
            """)
    List<MessageItem> queryNoReadMessages(@Param("userId") Integer userId);

    @Update("update comment set is_read = true where id=#{commentId}")
    Integer updateCommentRead(@Param("commentId") Integer commentId);
}
