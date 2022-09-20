import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JedisMain {

    //the jedis connection pool..
    Jedis jedis = new Jedis();

    public static void main(String[] args) {
        JedisMain main = new JedisMain();

        main.addStrings();
        main.addLists();
        main.addSets();
        main.addHash();
        main.addTransactions();
    }

    public void addStrings() {
        //add some string values in Redis
        String key = "events/city/rome";
        String value = "32,15,223,828";

        try {
            //save to redis
            jedis.set(key, value);

            //after saving the data, lets retrieve them to be sure that it has really added in redis
            String cachedResponse = jedis.get(key);

            System.out.println(cachedResponse);
            System.out.println("*****************************");
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void addLists() {
        //add some values in Redis List
        String key = "queue#tasks";
        String value1 = "firstTask";
        String value2 = "secondTask";

        try {
            //save to redis
            jedis.lpush(key, value1);
            jedis.lpush(key, value2);

            //after saving the data, lets retrieve them to be sure that it has really added in redis
            String task = jedis.rpop(key);

            System.out.println(task);
            System.out.println("*****************************");
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void addSets() {
        //let us first add some data in our redis server using Redis SET.
        String key = "members";
        String member1 = "Sedarius";
        String member2 = "Richard";
        String member3 = "Joe";

        try {
            //save to redis
            jedis.sadd(key, member1, member2, member3);

            //after saving the data, lets retrieve them to be sure that it has really added in redis
            Set<String> members = jedis.smembers(key);

            for (String member : members) {
                System.out.println(member);
            }

            System.out.println("*****************************");
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void addHash() {
        //add some values in Redis HASH
        String key = "javapointers";
        Map<String, String> map = new HashMap<>();
        map.put("name", "Java Pointers");
        map.put("domain", "www.javapointers.com");
        map.put("description", "Learn how to program in Java");

        try {
            //save to redis
            jedis.hmset(key, map);

            //after saving the data, lets retrieve them to be sure that it has really added in redis
            Map<String, String> retrieveMap = jedis.hgetAll(key);

            for (String keyMap : retrieveMap.keySet()) {
                System.out.println(keyMap + " " + retrieveMap.get(keyMap));
            }

            System.out.println("*****************************");
        } catch (JedisException e) {
            System.err.println(e);
        }
    }

    public void addTransactions() {
        //add some values in Redis HASH
        String friendsPrefix = "friends#";
        String userOneId = "4352523";
        String userTwoId = "5552321";

        try {
            //save to redis
            Transaction t = jedis.multi();
            t.sadd(friendsPrefix + userOneId, userTwoId);
            t.sadd(friendsPrefix + userTwoId, userOneId);

            //after saving the data, lets retrieve them to be sure that it has really added in redis
            t.exec();

            System.out.println("*****************************");
        } catch (JedisException e) {
            System.err.println(e);
        }
    }
}
