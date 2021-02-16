package io.akash.openbanking.accountaggregator.configuration;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import io.akash.openbanking.accountaggregator.accountdiscovery.model.FinancialInformationProvider;

/**
 * Purpose:- 
 * Takes care of all configurations related to Redis programatically
 * 
 * @author  Akash Gupta(akash75gupta@gmail.com)
 * @version 1.0
 * @since   2019-04-01
 *  
 **/

@Configuration
@EnableCaching
@PropertySource("classpath:application.properties")
@ComponentScan("io.akash.openbanking")
public class RedisConfiguration {
	
	@Autowired
	Environment env;
	
	public static final String  REDIS_HOST_PROPERTY = "spring.redis.host";
	public static final String  REDIS_PORT_PROPERTY = "spring.redis.port";
	public static final String  REDIS_TTL_PROPERTY 	= "spring.cache.redis.time-to-live";
	

	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration redisConf = new RedisStandaloneConfiguration();
		redisConf.setHostName(env.getProperty(REDIS_HOST_PROPERTY));
		redisConf.setPort(Integer.parseInt(env.getProperty(REDIS_PORT_PROPERTY)));
		return new LettuceConnectionFactory(redisConf);
	}

	  @Bean
	   public RedisCacheManager cacheManager() {
		RedisCacheManager rcm = RedisCacheManager.builder(redisConnectionFactory())
		  .cacheDefaults(cacheConfiguration())
		  .transactionAware()
		  .build();
		return rcm;
	   }


	  @Bean
	  public RedisCacheConfiguration cacheConfiguration() {
		RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
		  .entryTtl(Duration.ofSeconds(600))
		  .disableCachingNullValues();	
		return cacheConfig;
	   }
	  
	  @Bean
	  RedisTemplate<String, Object> redisTemplate() {
	   final RedisTemplate< String, Object > template =  new RedisTemplate< String, Object >();
	   template.setConnectionFactory( redisConnectionFactory());
	   template.setKeySerializer( new StringRedisSerializer() );
	   template.setHashValueSerializer( new GenericToStringSerializer< Object >( Object.class ) );
	   template.setValueSerializer( new GenericToStringSerializer< Object >( Object.class ) );
	   return template;
	  }
	  
	  @Bean
	RedisTemplate<String, FinancialInformationProvider> redisTemplateForFip() {
		final RedisTemplate<String, FinancialInformationProvider> redisTemplate = new RedisTemplate<String, FinancialInformationProvider>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());

		redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(FinancialInformationProvider.class));
		redisTemplate.setKeySerializer(new StringRedisSerializer());

		return redisTemplate;
	}
}

