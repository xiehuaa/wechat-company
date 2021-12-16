package com.xh.wechat.company.redis;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Create By IntelliJ IDEA
 *
 * @author: XieHua
 * @date: 2021-12-16 14:11
 */
@Slf4j
public class RedisClient {
    private final String keyPrefix;
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisClient(String keyPrefix, RedisTemplate<String, Object> redisTemplate) {
        this.keyPrefix = keyPrefix;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 判断key是否存在
     *
     * @param keySuffix key
     * @return 是否存在
     */
    public Boolean exists(String keySuffix) {
        String key = keyPrefix + keySuffix;
        try {
            return redisTemplate.hasKey(key);
        } catch (NullPointerException e) {
            this.log(key, "exists", "");
            return Boolean.FALSE;
        }
    }

    /**
     * 删除缓存
     *
     * @param keySuffix 缓存key
     * @return 是否删除成功
     */
    public Boolean del(String keySuffix) {
        String key = keyPrefix + keySuffix;
        try {
            return redisTemplate.delete(key);
        } catch (Exception e) {
            this.log(key, "del", "");
            return false;
        }
    }


    /**
     * 设置过期时间 秒
     *
     * @param keySuffix 缓存key
     * @param duration  有效时长
     * @return 是否设置成功
     */
    public Boolean expire(String keySuffix, Long duration) {
        String key = keyPrefix + keySuffix;
        try {
            return redisTemplate.expire(key, duration, TimeUnit.SECONDS);
        } catch (Exception e) {
            this.log(key, "expire", String.valueOf(duration));
            return false;
        }
    }

    /**
     * 获取有效时长
     *
     * @param keySuffix 缓存key
     * @return 有效时长
     */
    public Long ttl(String keySuffix) {
        String key = keyPrefix + keySuffix;
        try {
            return redisTemplate.getExpire(key);
        } catch (Exception e) {
            this.log(key, "ttl", "");
            return null;
        }
    }

    // ============================== String ==============================

    /**
     * 设置缓存
     *
     * @param keySuffix 缓存key
     * @param value     缓存值
     * @return 是否操作成功
     */
    public Boolean set(String keySuffix, Object value) {
        String key = keyPrefix + keySuffix;
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            this.log(key, "set", JSON.toJSONString(value));
            return false;
        }
    }

    /**
     * 设置缓存
     *
     * @param keySuffix 缓存key
     * @param value     缓存值
     * @param duration  有效时长
     * @return 是否设置成功
     */
    public Boolean set(String keySuffix, Object value, Long duration) {
        String key = keyPrefix + keySuffix;
        try {
            redisTemplate.opsForValue().set(key, value, duration);
            return true;
        } catch (Exception e) {
            this.log(key, "set ex", "duration:" + duration + ",value:" + JSON.toJSONString(value));
            return false;
        }
    }

    /**
     * 存入并设置过期时间（秒）
     *
     * @param keySuffix 键
     * @param value     值
     * @param duration  有效时长
     * @return
     */
    public Boolean setEx(String keySuffix, String value, Long duration) {
        String key = keyPrefix + keySuffix;
        try {
            redisTemplate.opsForValue().set(key, value, duration, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            this.log(key, "setEx", "duration:" + duration + ",value:" + JSON.toJSONString(value));
            return false;
        }
    }

    /**
     * 存入并设置过期时间（毫秒）
     *
     * @param keySuffix 键
     * @param value     值
     * @param duration  有效时长
     * @return
     */
    public boolean setPx(String keySuffix, String value, Long duration) {
        String key = keyPrefix + keySuffix;
        try {
            redisTemplate.opsForValue().set(key, value, duration, TimeUnit.MILLISECONDS);
            return true;
        } catch (Exception e) {
            this.log(key, "setPx", "duration:" + duration + ",value:" + JSON.toJSONString(value));
            log.error("存入带有过期时间(毫秒)String类型数据报错：", e);
            return false;
        }
    }

    /**
     * 键不存在时存入
     *
     * @param keySuffix 键
     * @param value     值
     * @return
     */
    public Boolean setNx(String keySuffix, Object value) {
        String key = keyPrefix + keySuffix;
        try {
            return redisTemplate.opsForValue().setIfAbsent(key, value);
        } catch (Exception e) {
            this.log(key, "setNx", JSON.toJSONString(value));
            log.info("存入（当key不存在）String类型数据报错：", e);
            return false;
        }
    }

    /**
     * 键不存在时并带有过期时间的存入
     *
     * @param keySuffix 键
     * @param value     值
     * @param duration  有效时长
     * @return
     */
    public Boolean setNx(String keySuffix, Object value, Long duration) {
        String key = keyPrefix + keySuffix;
        try {
            return redisTemplate.opsForValue().setIfAbsent(key, value, duration, TimeUnit.SECONDS);
        } catch (Exception e) {
            this.log(key, "setNx", "duration:" + duration + ",value:" + JSON.toJSONString(value));
            return false;
        }
    }

    /**
     * 键存在时存入
     *
     * @param keySuffix 键
     * @param value     值
     * @return
     */
    public Boolean setXx(String keySuffix, Object value) {
        String key = keyPrefix + keySuffix;
        try {
            return redisTemplate.opsForValue().setIfPresent(key, value);
        } catch (Exception e) {
            this.log(key, "setXx", JSON.toJSONString(value));
            return false;
        }
    }

    /**
     * 键存在时并带有过期时间的存入
     *
     * @param keySuffix 键
     * @param value     值
     * @param duration  有效时长
     * @return
     */
    public Boolean setXx(String keySuffix, Object value, Long duration) {
        String key = keyPrefix + keySuffix;
        try {
            return redisTemplate.opsForValue().setIfPresent(key, value, duration, TimeUnit.SECONDS);
        } catch (Exception e) {
            this.log(key, "setXx", "duration:" + duration + ",value:" + JSON.toJSONString(value));
            return false;
        }
    }

    /**
     * 获取键的值
     *
     * @param keySuffix 键
     * @return 对应的value
     */
    public Object get(String keySuffix) {
        String key = keyPrefix + keySuffix;
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log(key, "get", "");
            return null;
        }
    }

    /**
     * 设置新增并返回旧值
     *
     * @param keySuffix 键
     * @param value     新值
     * @return
     */
    public Object getSet(String keySuffix, String value) {
        String key = keyPrefix + keySuffix;
        try {
            return redisTemplate.opsForValue().getAndSet(key, value);
        } catch (Exception e) {
            this.log(key, "getSet", JSON.toJSONString(value));
            return null;
        }
    }

    /**
     * 获取字符串长度
     *
     * @param keySuffix 键
     * @return
     */
    public Long strLen(String keySuffix) {
        String key = keyPrefix + keySuffix;
        try {
            return redisTemplate.opsForValue().size(key);
        } catch (Exception e) {
            this.log(key, "strLen", "");
            return null;
        }
    }

    /**
     * 如果键 key 已经存在并且它的值是一个字符串， APPEND 命令将把 value 追加到键 key 现有值的末尾。
     *
     * @param keySuffix 键
     * @param value     追加的值
     * @return
     */
    public Integer append(String keySuffix, String value) {
        String key = keyPrefix + keySuffix;
        try {
            return redisTemplate.opsForValue().append(key, value);
        } catch (Exception e) {
            this.log(key, "append", JSON.toJSONString(value));
            return null;
        }
    }

    /**
     * 从偏移量 offset 开始， 用 value 参数覆写(overwrite)键 key 储存的字符串值。
     * 不存在的键 key 当作空白字符串处理。
     *
     * @param keySuffix 键
     * @param value     值
     * @param offset    偏移量
     */
    public Boolean setRange(String keySuffix, Object value, long offset) {
        String key = keyPrefix + keySuffix;
        try {
            redisTemplate.opsForValue().set(key, value, offset);
            return true;
        } catch (Exception e) {
            this.log(key, "setRange", "offset:" + offset + ",value:" + JSON.toJSONString(value));
            return false;
        }
    }

    /**
     * 返回键 key 储存的字符串值的指定部分， 字符串的截取范围由 start 和 end 两个偏移量决定
     * (包括 start 和 end 在内)。
     *
     * @param keySuffix 键
     * @param start     起始偏移量
     * @param end       截止偏移量
     * @return
     */
    public Object getRange(String keySuffix, long start, long end) {
        String key = keyPrefix + keySuffix;
        try {
            return redisTemplate.opsForValue().get(key, start, end);
        } catch (Exception e) {
            this.log(key, "getRange", "start:" + start + ",end:" + end);
            return null;
        }
    }

    /**
     * 为键 key 储存的数字值加上一。
     * 如果键 key 不存在， 那么它的值会先被初始化为 0 ， 然后再执行 INCR 命令。
     * 如果键 key 储存的值不能被解释为数字， 那么 INCR 命令将返回一个错误。
     *
     * @param keySuffix 键
     */
    public Long incr(String keySuffix) {
        String key = keyPrefix + keySuffix;
        try {
            return redisTemplate.opsForValue().increment(key);
        } catch (Exception e) {
            this.log(key, "incr", "");
            return null;
        }
    }

    /**
     * 为键 key 储存的数字值加上增量 increment
     *
     * @param keySuffix 键
     * @param increment 增加量
     */
    public Long incrBy(String keySuffix, long increment) {
        String key = keyPrefix + keySuffix;
        try {
            return redisTemplate.opsForValue().increment(key, increment);
        } catch (Exception e) {
            this.log(key, "incrBy", String.valueOf(increment));
            return null;
        }
    }

    /**
     * 为键 key 储存的数字值加上增量 increment 。
     *
     * @param keySuffix 键
     * @param increment 增加量
     */
    public Double incrByFloat(String keySuffix, double increment) {
        String key = keyPrefix + keySuffix;
        try {
            return redisTemplate.opsForValue().increment(key, increment);
        } catch (Exception e) {
            this.log(key, "incrByFloat", String.valueOf(increment));
            return null;
        }
    }

    /**
     * 为键 key 储存的数字值减去一。
     * 如果键 key 不存在， 那么键 key 的值会先被初始化为 0 ， 然后再执行 DECR 操作
     * 如果键 key 储存的值不能被解释为数字， 那么 DECR 命令将返回一个错误
     *
     * @param keySuffix 键
     */
    public Long decr(String keySuffix) {
        String key = keyPrefix + keySuffix;
        try {
            return redisTemplate.opsForValue().decrement(key);
        } catch (Exception e) {
            this.log(key, "decr", "");
            return null;
        }
    }

    /**
     * 将键 key 储存的整数值减去减量 decrement 。
     *
     * @param key       键
     * @param decrement 减量
     */
    public Long decrBy(String keySuffix, long decrement) {
        String key = keyPrefix + keySuffix;
        try {
            return redisTemplate.opsForValue().decrement(key, decrement);
        } catch (Exception e) {
            this.log(key, "decrBy", String.valueOf(decrement));
            return null;
        }
    }

    /**
     * 同时为多个键设置值。
     *
     * @param dictMap 键值对集合
     */
    public Boolean mSet(Map<String, Object> dictMap) {
        Map<String, Object> keyValueMap = new HashMap<>();
        for (String key : dictMap.keySet()) {
            keyValueMap.put(keyPrefix + key, dictMap.get(key));
        }
        try {
            redisTemplate.opsForValue().multiSet(keyValueMap);
            return Boolean.TRUE;
        } catch (Exception e) {
            this.log(JSON.toJSONString(keyValueMap), "mSet", "");
            return Boolean.FALSE;
        }
    }

    /**
     * 当且仅当所有给定键都不存在时， 为所有给定键设置值。
     *
     * @param dictMap 键值对集合
     */
    public Boolean mSetNx(Map<String, Object> dictMap) {
        Map<String, Object> keyValueMap = new HashMap<>();
        for (String key : dictMap.keySet()) {
            keyValueMap.put(keyPrefix + key, dictMap.get(key));
        }
        try {
            return redisTemplate.opsForValue().multiSetIfAbsent(keyValueMap);
        } catch (Exception e) {
            this.log(JSON.toJSONString(keyValueMap), "mSetNx", "");
            return false;
        }
    }

    /**
     * 返回给定的一个或多个字符串键的值。
     *
     * @param keySuffixList 键列表
     * @return value列表
     */
    public List<Object> mGet(List<String> keySuffixList) {
        List<String> keys = keySuffixList.stream().map(key -> keyPrefix + key).collect(Collectors.toList());
        try {
            return redisTemplate.opsForValue().multiGet(keys);
        } catch (Exception e) {
            this.log(JSON.toJSONString(keys), "mGet", "");
            return null;
        }
    }

    // ============================== Hash ==============================

    /**
     * 向哈希表中添加字段
     *
     * @param keySuffix 缓存key
     * @param field     字段
     * @param value     缓存value
     */
    public Boolean hSet(String keySuffix, String field, Object value) {
        String key = keyPrefix + keySuffix;
        try {
            redisTemplate.opsForHash().put(key, field, value);
            return Boolean.TRUE;
        } catch (Exception e) {
            this.log(key, "hSet", JSON.toJSONString(value));
            return Boolean.FALSE;
        }
    }

    /**
     * 当字段不存在时设置
     *
     * @param keySuffix 缓存key
     * @param field     字段
     * @param value     缓存value
     * @return 是否添加成功
     */
    public Boolean hSetNx(String keySuffix, String field, Object value) {
        String key = keyPrefix + keySuffix;
        try {
            return redisTemplate.opsForHash().putIfAbsent(key, field, value);
        } catch (Exception e) {
            this.log(key, "hSetNx", "field:" + field + ",value:" + JSON.toJSONString(value));
            return Boolean.FALSE;
        }
    }

    /**
     * 从哈希结构中获取数据
     *
     * @param keySuffix 缓存key
     * @param field     需要获取的字段
     * @return 字段对应的值
     */
    public Object hGet(String keySuffix, String field) {
        String key = keyPrefix + keySuffix;
        try {
            return redisTemplate.opsForHash().get(key, field);
        } catch (Exception e) {
            this.log(key, "hGet", field);
            return null;
        }
    }

    /**
     * 判断字段是否存在
     *
     * @param keySuffix key
     * @param field     字段
     * @return 是否存在
     */
    public Boolean hExists(String keySuffix, String field) {
        String key = keyPrefix + keySuffix;
        try {
            return redisTemplate.opsForHash().hasKey(key, field);
        } catch (Exception e) {
            this.log(key, "hExists", field);
            return Boolean.FALSE;
        }
    }

    /**
     * 删除哈希表中的字段
     *
     * @param keySuffix 缓存key
     * @param field     字段
     * @return 删除结果
     */
    public Long hDel(String keySuffix, String field) {
        String key = keyPrefix + keySuffix;
        try {
            return redisTemplate.opsForHash().delete(key, field);
        } catch (Exception e) {
            this.log(key, "hDel", field);
            return null;
        }
    }

    /**
     * 获取哈希表的字段数量
     *
     * @param keySuffix 缓存key
     * @return 字段数量
     */
    public Long hLen(String keySuffix) {
        String key = keyPrefix + keySuffix;
        try {
            return redisTemplate.opsForHash().size(key);
        } catch (Exception e) {
            this.log(key, "hLen", "");
            return null;
        }
    }

    /**
     * 获取字段对应的value的长度
     *
     * @param keySuffix 缓存key
     * @param field     字段
     * @return 值的长度
     */
    public Long hStrLen(String keySuffix, String field) {
        return null;
    }

    // ============================== List ==============================

    // ============================== Set ==============================

    // ============================== ZSet ==============================

    /**
     * 添加元素
     *
     * @param keySuffix 缓存key
     * @param value     元素
     * @param score     分值
     * @return 是否添加成功
     */
    public Boolean zAdd(String keySuffix, Object value, Double score) {
        String key = keyPrefix + keySuffix;
        try {
            return redisTemplate.opsForZSet().add(key, value, score);
        } catch (Exception e) {
            this.log(key, "zAdd", "score:" + score + ",value:" + JSON.toJSONString(value));
            return Boolean.FALSE;
        }
    }

    /**
     * 获取元素的分值
     *
     * @param keySuffix 缓存key
     * @param value     元素
     * @return 元素分值
     */
    public Double zScore(String keySuffix, Object value) {
        String key = keyPrefix + keySuffix;
        try {
            return redisTemplate.opsForZSet().score(key, value);
        } catch (Exception e) {
            this.log(key, "zScore", JSON.toJSONString(value));
            return null;
        }
    }

    /**
     * 获取元素数量
     *
     * @param keySuffix 缓存key
     * @return 数量
     */
    public Long zCard(String keySuffix) {
        String key = keyPrefix + keySuffix;
        try {
            return redisTemplate.opsForZSet().zCard(key);
        } catch (Exception e) {
            this.log(key, "zCard", "");
            return null;
        }
    }

    // ============================== BitMap ==============================

    /**
     * 设置bit
     *
     * @param keySuffix 缓存key
     * @param offset    偏移量
     * @param value     值
     * @return 是否设置成功
     */
    public Boolean setBit(String keySuffix, Long offset, Boolean value) {
        String key = keyPrefix + keySuffix;
        try {
            return redisTemplate.opsForValue().setBit(key, offset, value);
        } catch (Exception e) {
            this.log(key, "setBit", "offset:" + offset + ",value:" + value);
            return Boolean.FALSE;
        }
    }

    /**
     * 获取bit
     *
     * @param keySuffix 缓存key
     * @param offset    偏移量
     * @return 对应偏移量的bit值
     */
    public Boolean getBit(String keySuffix, Long offset) {
        String key = keyPrefix + keySuffix;
        try {
            return redisTemplate.opsForValue().getBit(key, offset);
        } catch (Exception e) {
            this.log(key, "getBit", String.valueOf(offset));
            return Boolean.FALSE;
        }
    }

    // ============================== GEO ==============================

    /**
     * 添加元素
     *
     * @param keySuffix 缓存key
     * @param point     地理位置
     * @param value     value
     * @return 添加结果
     */
    public Long geoAdd(String keySuffix, Point point, Object value) {
        String key = keyPrefix + keySuffix;
        try {
            return redisTemplate.opsForGeo().add(key, point, value);
        } catch (Exception e) {
            this.log(key, "geoAdd", JSON.toJSONString(value));
            return null;
        }
    }

    // ============================== HyperLogLog ==============================

    /**
     * 添加元素
     *
     * @param keySuffix 缓存key
     * @param values    缓存value列表
     * @return 长度
     */
    public Long pfAdd(String keySuffix, Object... values) {
        String key = keyPrefix + keySuffix;
        try {
            return redisTemplate.opsForHyperLogLog().add(key, values);
        } catch (Exception e) {
            this.log(key, "pfAdd", JSON.toJSONString(values));
            return null;
        }
    }

    /**
     * 获取数量
     *
     * @param keySuffix 缓存key
     * @return 数量
     */
    public Long pfCount(String keySuffix) {
        String key = keyPrefix + keySuffix;
        try {
            return redisTemplate.opsForHyperLogLog().size(key);
        } catch (Exception e) {
            this.log(key, "pfCount", "");
            return null;
        }
    }

    /**
     * 合并多个HyperLogLog
     *
     * @param targetKeySuffix 合并后的key
     * @param sourceKeys      需要合并的key
     * @return 合并结果
     */
    public Long pfMerge(String targetKeySuffix, String... sourceKeys) {
        String targetKey = keyPrefix + targetKeySuffix;
        sourceKeys = this.addPrefix(sourceKeys);
        try {
            return redisTemplate.opsForHyperLogLog().union(targetKey, sourceKeys);
        } catch (Exception e) {
            this.log(targetKey, "pfMerge", "");
            return null;
        }
    }

    /**
     * key列表拼接前缀
     *
     * @param keys 需要拼接前缀的key数组
     * @return 拼接了前缀后的key数组
     */
    private String[] addPrefix(String... keys) {
        if (keys == null || keys.length == 0) {
            return null;
        }
        String[] result = new String[keys.length];
        for (int i = 0; i < keys.length; i++) {
            result[i] = keyPrefix + keys[i];
        }
        return result;
    }

    /**
     * 记录日志
     *
     * @param key     key
     * @param command 命令
     */
    private void log(String key, String command, String value) {
        log.info("redis [{}}] operate error：{}-{}", command, key, value);
    }


}
