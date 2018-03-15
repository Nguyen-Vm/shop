package com.nguyen.shop.utils;

import com.nguyen.shop.common.RedisPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * @author RWM
 * @date 2018/3/6
 *
 * @description Redis连接池工具类
 */
@Slf4j
public class RedisPoolUtil {

    public static String get(String key){
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getResource();
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("get key:{} error", key, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static String set(String key, String value){
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getResource();
            result = jedis.set(key, value);
        }catch (Exception e){
            log.error("set key:{}, value:{} error", key, value, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static String setEx(String key, String value, int exTime){
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getResource();
            result = jedis.setex(key, exTime, value);
        }catch (Exception e){
            log.error("setEx key:{}, value:{} error", key, value, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static Long expire(String key, int exTime){
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getResource();
            result = jedis.expire(key, exTime);
        }catch (Exception e){
            log.error("expire key:{} error", key, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static Long del(String key){
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getResource();
            result = jedis.del(key);
        }catch (Exception e){
            log.error("del key:{} error", key, e);
            RedisPool.returnBrokenResource(jedis);
            return result;
        }
        RedisPool.returnResource(jedis);
        return result;
    }

    public static void main(String[] args) {
        Jedis jedis = RedisPool.getResource();
        RedisPoolUtil.set("nguyenKey","nguyenValue");
        RedisPoolUtil.set("del","del");

        String nguyenValue = RedisPoolUtil.get("nguyenKey");

        RedisPoolUtil.setEx("nguyen","nguyen",1000*10);

        RedisPoolUtil.expire("nguyenKey",1000*10);

        RedisPoolUtil.del("del");

        System.out.println("End");
    }
}
