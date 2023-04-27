package com.sea.review.service;

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
    private final String MY_LOCK = "my_lock";


    public int increment(int amount) {
        RLock lock = redissonClient.getLock(MY_LOCK);
        try {
            System.out.printf(format.format(new Date()) + " increment %s 准备拿锁%n", Thread.currentThread().getName());
            lock.lock(10, TimeUnit.SECONDS);
            System.out.printf(format.format(new Date()) + " increment %s 获的锁%n", Thread.currentThread().getName());
            int temp = money + amount;
            Thread.sleep(10);
            money = temp;
        } catch (Exception e) {
            System.out.printf(format.format(new Date()) + " increment %s 异常%n", Thread.currentThread().getName());
        } finally {
            if (lock.isLocked() && lock.isHeldByCurrentThread()) {
                try {
                    lock.unlock();
                    System.out.printf(format.format(new Date()) + " increment %s 解锁成功%n", Thread.currentThread().getName());
                } catch (Exception e) {
                    System.out.printf(format.format(new Date()) + " increment %s 解锁失败%n", Thread.currentThread().getName());
                }
            }
        }
        System.out.printf(format.format(new Date()) + " increment %s 计算结束 %s %n", Thread.currentThread().getName(), money);
        return money;
    }

    public int decrement(int amount) {
        RLock lock = redissonClient.getLock(MY_LOCK);
        try {
            System.out.printf(format.format(new Date()) + " decrement %s 准备拿锁%n", Thread.currentThread().getName());
            // 尝试加锁，最多等待100秒，上锁以后10秒自动解锁
            boolean res = lock.tryLock(100, 10, TimeUnit.SECONDS);
            if (res) {
                System.out.printf(format.format(new Date()) + " decrement %s 获得锁%n", Thread.currentThread().getName());
                int temp = money - amount;
                Thread.sleep(10);
                money = temp;
            }
        } catch (InterruptedException e) {
            System.out.printf(format.format(new Date()) + " decrement %s 异常%n", Thread.currentThread().getName());
        } finally {
            //判断是否是当前线程持有该锁
            try {
                lock.unlock();
                System.out.printf(format.format(new Date()) + " decrement %s 解锁成功%n", Thread.currentThread().getName());
            } catch (Exception e) {
                System.out.printf(format.format(new Date()) + " decrement %s 解锁失败%n", Thread.currentThread().getName());
            }
        }
        return money;
    }

    public int getMoney() {
        return money;
    }

}
