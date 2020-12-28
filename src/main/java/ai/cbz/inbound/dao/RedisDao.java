package ai.cbz.inbound.dao;


import ai.cbz.inbound.serializer.SerializeUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: Jinzw
 * @Date: 2020/12/10 18:15
 */

@Repository
//@Service
public class RedisDao {

    //    @Autowired
    private StringRedisTemplate template;

    @Resource
    private  RedisTemplate<String,Object> redisTemplate;

    /**
     * 向redis中写入键值对
     *
     * @param key
     * @param value
     */
    public void setStringValue(String key, String value) {
        ValueOperations<String, String> ops = template.opsForValue();
        ops.set(key, value, 10, TimeUnit.MINUTES);
    }

    /**
     * 从redis中根据键取值
     *
     * @param key
     * @return
     */
    public String getStringValue(String key) {
        ValueOperations<String, String> ops = template.opsForValue();
        return ops.get(key);
    }

    /**
     * 字符串：存对象
     * @param key
     * @param value
     */
    public void setObject(String key,Object value) {
        final byte[] values = SerializeUtil.serialize(value);
        redisTemplate.execute((RedisCallback<Void>) connection -> {
            connection.set(key.getBytes(), values);
            return null;
        });
    }


    /**
     * 字符串：取对象
     * @param key
     * @param targetClass：实体对象
     * @return
     */
    public <T> T getObject(final String key, Class<T> targetClass) {

        byte[] result = redisTemplate.execute((RedisCallback<byte[]>) connection -> connection.get(key.getBytes()));
        if (result == null) {
            return null;
        }
        return SerializeUtil.deserialize(result, targetClass);
    }


    /**
     * 从hashMap中取出对象
     *
     * @param key
     * @param hashKey
     * @param targetClass
     * @param <T>
     * @return
     */
    public <T> T hGetObject(final String key, final String hashKey, Class<T> targetClass) {
        byte[] result = redisTemplate.execute((RedisCallback<byte[]>) connection -> connection.hGet(key.getBytes(), hashKey.getBytes()));
        if (result == null) {
            return null;
        }
        return SerializeUtil.deserialize(result, targetClass);
    }


    /**
     * 在hashMap中放入对象
     *
     * @param key
     * @param hashKey
     * @param obj
     * @param <T>
     * @return
     */
    public <T> boolean hSetObject(String key, String hashKey, T obj) {
        final byte[] value = SerializeUtil.serialize(obj);
        return redisTemplate.execute( (RedisCallback<Boolean>) connection -> connection.hSet(key.getBytes(), hashKey.getBytes(), value));
    }
}
