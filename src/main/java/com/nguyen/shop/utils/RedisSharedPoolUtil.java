package com.nguyen.shop.utils;

import com.nguyen.shop.common.RedisPool;
import com.nguyen.shop.common.RedisSharedPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

/**
 * @author RWM
 * @date 2018/3/13
 */
@Slf4j
public class RedisSharedPoolUtil {

    public static String get(String key){
        ShardedJedis jedis = null;
        String result = null;
        try {
            jedis = RedisSharedPool.getResource();
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("get key:{} error", key, e);
            RedisSharedPool.returnBrokenResource(jedis);
            return result;
        }
        RedisSharedPool.returnResource(jedis);
        return result;
    }

    public static String set(String key, String value){
        ShardedJedis jedis = null;
        String result = null;
        try {
            jedis = RedisSharedPool.getResource();
            result = jedis.set(key, value);
        }catch (Exception e){
            log.error("set key:{}, value:{} error", key, value, e);
            RedisSharedPool.returnBrokenResource(jedis);
            return result;
        }
        RedisSharedPool.returnResource(jedis);
        return result;
    }

    public static String setEx(String key, String value, int exTime){
        ShardedJedis jedis = null;
        String result = null;
        try {
            jedis = RedisSharedPool.getResource();
            result = jedis.setex(key, exTime, value);
        }catch (Exception e){
            log.error("setEx key:{}, value:{} error", key, value, e);
            RedisSharedPool.returnBrokenResource(jedis);
            return result;
        }
        RedisSharedPool.returnResource(jedis);
        return result;
    }

    public static Long expire(String key, int exTime){
        ShardedJedis jedis = null;
        Long result = null;
        try {
            jedis = RedisSharedPool.getResource();
            result = jedis.expire(key, exTime);
        }catch (Exception e){
            log.error("expire key:{} error", key, e);
            RedisSharedPool.returnBrokenResource(jedis);
            return result;
        }
        RedisSharedPool.returnResource(jedis);
        return result;
    }

    public static Long del(String key){
        ShardedJedis jedis = null;
        Long result = null;
        try {
            jedis = RedisSharedPool.getResource();
            result = jedis.del(key);
        }catch (Exception e){
            log.error("del key:{} error", key, e);
            RedisSharedPool.returnBrokenResource(jedis);
            return result;
        }
        RedisSharedPool.returnResource(jedis);
        return result;
    }

    public static void main(String[] args) {
        Jedis jedis = RedisPool.getResource();
        RedisSharedPoolUtil.set("nguyenKey","nguyenValue");
        RedisSharedPoolUtil.set("del","del");

        String nguyenValue = RedisSharedPoolUtil.get("nguyenKey");

        RedisSharedPoolUtil.setEx("nguyen","nguyen",1000*10);

        RedisSharedPoolUtil.expire("nguyenKey",1000*10);

        RedisSharedPoolUtil.del("del");

        System.out.println("End");
    }
}
