package com.example.toj.mapper;

import com.example.toj.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * from user where username=#{username} and password=#{password}")
    User login(User user);

    @Select("select * from user")
    List<User> queryAllUser();

    @Select("select username from user where id = #{userId}")
    String queryUsernameById(@Param("userId") Integer userId);

    @Insert("insert into user (username, password, is_admin) VALUES (#{username}, #{password}, #{isAdmin})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insertUser(User user) throws DataAccessException;

    @Update("UPDATE user set password=#{newPassword} where id=#{userid} and password=#{oldPassword}")
    Integer updatePassword(@Param("userid") Integer id,
                           @Param("oldPassword") String oldPassword,
                           @Param("newPassword") String newPassword);

    @Update("update user set username = #{username}, password = #{password} where id = #{id}")
    Integer updateUser(User user);

    @Delete("delete from user where id = #{id}")
    Integer deleteUser(@Param("id") Integer id);
}
