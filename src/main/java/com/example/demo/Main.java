package com.example.demo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.args.FlushMode;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        JedisPool pool = new JedisPool("10.10.14.227", 34999, null, "BIPaS#c5Xm5J");

        try (Jedis jedis = pool.getResource()) {
            // Store & Retrieve a simple string
            System.out.println(jedis.ping());
            System.out.println(jedis.info());
            System.out.println(jedis.clusterInfo());
            System.out.println(jedis.flushAll(FlushMode.ASYNC));
            System.out.println(jedis.get("foo")); // prints bar

            // Store & Retrieve a HashMap
            Map<String, String> hash = new HashMap<>();
            ;
            hash.put("name", "John");
            hash.put("surname", "Smith");
            hash.put("company", "Redis");
            hash.put("age", "29");
            jedis.hset("user-session:123", hash);
            System.out.println(jedis.hgetAll("user-session:123"));
            // Prints: {name=John, surname=Smith, company=Redis, age=29}
        }
    }
}