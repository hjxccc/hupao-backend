package com.huhu.hupao.once;
import java.util.Date;

import com.huhu.hupao.mapper.UserMapper;
import com.huhu.hupao.model.domain.User;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;

/**
 * @author xxx
 * @date 2024/1/31 0:03
 */
@Component
public class InsertUsers {

    @Resource
    private UserMapper userMapper;

   // @Scheduled(fixedDelay = 5000,fixedRate = Long.MAX_VALUE)
    public void doInsertUsers(){
        StopWatch stopWatch=new StopWatch();
        stopWatch.start();
        final int INSERT_NUM=1000;

        for (int i = 0; i < INSERT_NUM; i++) {
           User user=new User();

           user.setUsername("假鱼皮");
           user.setUserAccount("fakeyupi");
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
/*
    public static void main(String[] args) {
        new InsertUsers().doInsertUsers();
    }*/
}
