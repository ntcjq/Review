package com.sea.review.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient() {

        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        return Redisson.create(config);
    }


    public static void main(String[] args) {


        Jedis jedis = new Jedis();
        SetParams setParams = new SetParams();
        setParams.nx().ex(30L);
        jedis.set("key", "requestId", setParams);
    }
}
