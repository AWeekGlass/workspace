package com.hengyu.common.util;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * 
 * @author zhaojin
 *
 */
@Component
public class RedisUtils<T> {
	@Autowired
	private RedisTemplate<String, T> redisTemplate;

	@Autowired
	private ValueOperations<String, T> valueOperations;

	public boolean existsKey(String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 重名名key，如果newKey已经存在，则newKey的原值被覆盖
	 *
	 * @param oldKey
	 * @param newKey
	 */
	public void renameKey(String oldKey, String newKey) {
		redisTemplate.rename(oldKey, newKey);
	}

	/**
	 * newKey不存在时才重命名
	 *
	 * @param oldKey
	 * @param newKey
	 * @return 修改成功返回true
	 */
	public boolean renameKeyNotExist(String oldKey, String newKey) {
		return redisTemplate.renameIfAbsent(oldKey, newKey);
	}

	/**
	 * 删除多个key
	 *
	 * @param keys
	 */
	public void deleteKey(String... keys) {
		Set<String> kSet = Stream.of(keys).map(k -> k).collect(Collectors.toSet());
		redisTemplate.delete(kSet);
	}

	/**
	 * 删除Key的集合
	 *
	 * @param keys
	 */
	public void deleteKey(Collection<String> keys) {
		Set<String> kSet = keys.stream().map(k -> k).collect(Collectors.toSet());
		redisTemplate.delete(kSet);
	}

	public Boolean setExpire(String key, Long timeout) {
		return redisTemplate.expire(key, timeout, TimeUnit.MINUTES);
	}

	/**
	 * 指定key在指定的日期过期
	 *
	 * @param key
	 * @param date
	 */
	public void expireKeyAt(String key, Date date) {
		redisTemplate.expireAt(key, date);
	}

	/**
	 * 查询key的生命周期
	 *
	 * @param key
	 * @param timeUnit
	 * @return
	 */
	public long getKeyExpire(String key, TimeUnit timeUnit) {
		return redisTemplate.getExpire(key, timeUnit);
	}

	/**
	 * 将key设置为永久有效
	 *
	 * @param key
	 */
	public void persistKey(String key) {
		redisTemplate.persist(key);
	}

	public void setValue(String key, T value) {
		valueOperations.set(key, value);
	}

	public T getValue(String key) {
		return valueOperations.get(key);
	}
}