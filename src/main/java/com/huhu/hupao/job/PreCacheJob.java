package com.huhu.hupao.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huhu.hupao.mapper.UserMapper;
import com.huhu.hupao.model.domain.User;
import com.huhu.hupao.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author xxx
 * @date 2024/1/31 18:56
 */
@Slf4j
@Component
public class PreCacheJob {
    @Resource
    private UserService userService;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private RedissonClient redissonClient;

    // 重点用户
    private List<Long> mainUserList = Arrays.asList(1L);
    @Scheduled(cron = "0 28 18 ? * *")
     public void doCacheRecommendUser(){
        RLock lock=redissonClient.getLock("huyu:precachejob:docache:lock");

        try {
            if(lock.tryLock(0,-1L,TimeUnit.MILLISECONDS)){
                Thread.sleep(300000);
                System.out.println("getLock"+Thread.currentThread().getId());
                for (Long userId : mainUserList) {
                    //查数据库
                    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                    Page<User> userPage = userService.page(new Page<>(1,20),queryWrapper);
                    String redisKey = String.format("shayu:user:recommend:%s",userId);
                    ValueOperations valueOperations = redisTemplate.opsForValue();
                    //写缓存,30s过期
                    try {
                        valueOperations.set(redisKey,userPage,30000, TimeUnit.MILLISECONDS);
                    } catch (Exception e){
                        log.error("redis set key error",e);
                    }
                }

            }

        }catch (InterruptedException e){
            log.error("docacheRecommendUser error",e);
        }finally {
            //一定要放到finally里面
            //只能释放自己的锁
            if(lock.isHeldByCurrentThread()){
                System.out.println("unLock"+Thread.currentThread().getId());
                lock.unlock();
            }
        }

    }


}
