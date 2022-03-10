package com.sea.review.service;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 注意点:解锁时除了要判断是否锁着（isLocked）,还要判断是否是当前线程持有该锁（isHeldByCurrentThread），因为解铃还须系铃人
 */
@Service
public class LockService {
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Autowired
    private RedissonClient redissonClient;

    private static int money = 100;
    private final String INCREMENT_LOCK = "test_lock_increment";
    private final String DECREMENT_LOCK = "test_lock_decrement";


    public int increment(int amount) {
        RLock lock = redissonClient.getLock(INCREMENT_LOCK);
        try {
            System.out.println(format.format(new Date()) + " increment 准备拿锁 参数:" + amount);
            lock.lock(10, TimeUnit.SECONDS);
            System.out.println(format.format(new Date()) + " increment 获得锁 参数:" + amount);
            int temp = money + amount;
            Thread.sleep(5000);
            money = temp;
        } catch (Exception e) {
            System.out.println(format.format(new Date()) + " 异常：" + e.toString());
        } finally {
            System.out.println(format.format(new Date()) + " islocked:" + lock.isLocked() + ", isHeldByCurrentThread:" + lock.isHeldByCurrentThread() + "参数:" + amount);
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                try {
                    System.out.println(format.format(new Date()) + " increment 解锁 参数:" + amount);
                    lock.unlock();
                } catch (Exception e) {
                    System.out.println(DateFormatUtils.format(new Date(), "yyyy-MM-dd hh:mm:ss") + "解锁失败" + amount);
                }
            }
        }
        System.out.println(format.format(new Date()) + " 计算结束" + amount);
        System.out.println(format.format(new Date()) + " islocked:" + lock.isLocked() + ", isHeldByCurrentThread:" + lock.isHeldByCurrentThread() + "参数:" + amount);
        return money;
    }

    public int decrement(int amount) {
        RLock lock = redissonClient.getLock(DECREMENT_LOCK);
        try {
            // 尝试加锁，最多等待100秒，上锁以后10秒自动解锁
            boolean res = lock.tryLock(100, 10, TimeUnit.SECONDS);
            if (res) {
                System.out.println("decrement 获得锁 参数:" + amount);
                int temp = money - amount;
                Thread.sleep(5000);
                money = temp;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(format.format(new Date()) + " islocked:" + lock.isLocked() + ", isHeldByCurrentThread:" + lock.isHeldByCurrentThread() + "参数:" + amount);
            System.out.println("decrement 解锁 参数:" + amount);
            if (lock.isLocked()) {
                //判断是否是当前线程持有该锁
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        }
        return money;
    }

    public int getMoney() {
        return money;
    }

}
