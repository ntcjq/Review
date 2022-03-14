package com.sea.review.util;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class LockUtil {

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 加锁
     *
     * @param lockKey
     */
    public void lock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
    }

    /**
     * 加锁
     *
     * @param lockKey
     * @param expireTime 超时时间
     * @return
     */
    public void lock(String lockKey, long expireTime) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(expireTime, TimeUnit.SECONDS);
    }

    /**
     * 加锁
     *
     * @param lockKey
     * @param expireTime 超时时间
     * @param waitTime   等待时间
     * @return
     * @throws InterruptedException
     */
    public boolean tryLock(String lockKey, long waitTime, long expireTime) throws InterruptedException {
        RLock lock = redissonClient.getLock(lockKey);
        return lock.tryLock(waitTime, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 解锁
     *
     * @param lockKey
     */
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        //判断是否锁着
        if (lock.isLocked()) {
            //判断是否是当前线程
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
