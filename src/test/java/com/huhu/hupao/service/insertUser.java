package com.huhu.hupao.service;

import com.huhu.hupao.mapper.UserMapper;
import com.huhu.hupao.model.domain.User;
import io.swagger.models.auth.In;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;

/**
 * @author xxx
 * @date 2024/3/15 18:44
 */

@SpringBootTest
public class insertUser {
    @Resource
    private UserMapper userMapper;
    @Test
    public void doInsertUsers(){
        StopWatch stopWatch=new StopWatch();
        stopWatch.start();
        final int INSERT_NUM=1000;

        for (int i = 0; i < INSERT_NUM; i++) {
            User user=new User();

            user.setUsername("假虎皮");
            user.setUserAccount("fakehupi");
            user.setAvatarUrl("");
            user.setGender(0);
            user.setUserPassword("12345678");
            user.setPhone("123");
            user.setEmail("123@qq.com");
            user.setUserStatus(0);
            user.setIsDelete(0);
            user.setUserRole(0);
            user.setPlanetCode("11111111");
            user.setTags("[]");
            userMapper.insert(user);
        }
        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis());
    }



}
