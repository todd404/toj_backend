package com.example.toj;

import com.example.toj.mapper.UserMapper;
import com.example.toj.pojo.User;
import com.example.toj.service.storage.TempFileStorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLIntegrityConstraintViolationException;

@SpringBootTest
class UserTest {
    @Autowired
    UserMapper userMapper;

    @Autowired
    TempFileStorageService tempFileStorageService;

    @Test
    void test(){
        var result = userMapper.queryAllUser();
        return;
    }

    @Test
    void insertUser(){
        User user = new User();
        user.setUsername("toddwu");
        user.setPassword("12345");
        user.setAdmin(false);

        var result = userMapper.insertUser(user);
        return;
    }

    @Test
    void accountTest(){
        User user = new User();
        user.setUsername("toddwu");
        user.setPassword("16995b98616d81230753801fba34fa6a");
        var result = userMapper.login(user);

        user.setUsername("todd2");
        user.setPassword("16995b98616d81230753801fba34fa6a");
        result = userMapper.login(user);
        return;
    }

    @Test
    void avatarCopyTest(){
        String file_uuid = "75d7e38c-b4f1-4289-b94e-4cec89df5a7a.jpg";
        String id = "2";
    }
}
