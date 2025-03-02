package org.demo.springbootcache.config

import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext

@Configuration
@EnableCaching
class AppConfig {

    @Bean
    fun cacheManager(connectionFactory: RedisConnectionFactory?): RedisCacheManager {
        return RedisCacheManager.builder(connectionFactory!!)
            .cacheDefaults(
                RedisCacheConfiguration.defaultCacheConfig()
                    .computePrefixWith { cacheName -> "$cacheName:" }
                    .serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                            GenericJackson2JsonRedisSerializer()
                        )
                    )
            )
            .build()
    }
}