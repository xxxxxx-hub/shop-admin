package com.fh.util;


import redis.clients.jedis.Jedis;

public class RedisUitl {

    public static void del(String key){
        Jedis jedis =null;
        try {
            jedis = RadisPool.getResource();
            jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            if(jedis !=null){
                jedis.close();
            }
        }
    }

    public static void set(String key, String value){

            Jedis jedis = null;
        try {
            jedis = RadisPool.getResource();
            jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            if(jedis !=null){
                jedis.close();
            }
        }

    }

    public static String get(String key){
        Jedis jedis =null;
        try {
            jedis = RadisPool.getResource();
            String res = jedis.get(key);
            return res ;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            if(jedis !=null){
                jedis.close();
            }
        }
    }

    public static void setEx(String key,int seconds,String value){
        Jedis jedis =null;
        try {
            jedis = RadisPool.getResource();
            jedis.setex(key,seconds,value);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }finally {
            if(jedis !=null){
                jedis.close();
            }
        }
    }



}
