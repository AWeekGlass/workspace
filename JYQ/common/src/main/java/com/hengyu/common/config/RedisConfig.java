package com.hengyu.common.config;

import java.lang.reflect.Method;
import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.alibaba.fastjson.parser.ParserConfig;
import com.hengyu.common.serializer.FastJsonRedisSerializer;

/**
 * 
 * @author zhaojin
 *
 */
@Configuration
public class RedisConfig extends CachingConfigurerSupport {
	@Bean
	@Override
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				for (Object obj : params) {
					sb.append(obj.toString());
				}
				return sb.toString();
			}
		};
	}

	@Bean
	public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
		RedisCacheConfiguration userCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofMinutes(30)).disableCachingNullValues();
		return RedisCacheManager.builder(connectionFactory).cacheDefaults(userCacheConfiguration).build();
	}

	@Bean
	public <T> RedisTemplate<String, T> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(factory);
		
		FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
		// 小范围指定白名单
		ParserConfig.getGlobalInstance().addAccept("com.hengyu.");
		
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(fastJsonRedisSerializer);
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
		redisTemplate.afterPropertiesSet();
		redisTemplate.setEnableTransactionSupport(true);// 开启事务
		return redisTemplate;
	}

	@Bean
	public <T> HashOperations<String, String, T> hashOperations(RedisTemplate<String, T> redisTemplate) {
		return redisTemplate.opsForHash();
	}

	@Bean
	public <T> ValueOperations<String, T> valueOperations(RedisTemplate<String, T> redisTemplate) {
		return redisTemplate.opsForValue();
	}

	@Bean
	public <T> ListOperations<String, T> listOperations(RedisTemplate<String, T> redisTemplate) {
		return redisTemplate.opsForList();
	}

	@Bean
	public <T> SetOperations<String, T> setOperations(RedisTemplate<String, T> redisTemplate) {
		return redisTemplate.opsForSet();
	}

	@Bean
	public <T> ZSetOperations<String, T> zSetOperations(RedisTemplate<String, T> redisTemplate) {
		return redisTemplate.opsForZSet();
	}
}
