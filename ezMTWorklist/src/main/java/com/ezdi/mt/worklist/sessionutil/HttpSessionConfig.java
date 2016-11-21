/*package com.ezdi.mt.worklist.sessionutil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;

@Configuration
@EnableRedisHttpSession
//@PropertySource("classpath:application.properties")
public class HttpSessionConfig {

	@Value("${redis.hostname}")
	private String redisHostName;

	@Value("${redis.port}")
	private int redisPort;

	@Bean
	public JedisConnectionFactory connectionFactory() {
		System.out.println("#$#HttpSessionConfig:connectionFactory()"
				+ redisHostName + ":" + redisPort);
		JedisConnectionFactory jedisConnectionFectory = new JedisConnectionFactory();
		jedisConnectionFectory.setHostName(redisHostName);
		jedisConnectionFectory.setPort(redisPort);
		return jedisConnectionFectory;
	}

	@Bean
	public HeaderHttpSessionStrategy sessionStrategy() {
		System.out.println("#$#HttpSessionConfig:httpSessionStrategy");
		return new HeaderHttpSessionStrategy();
	}
	
}
*/