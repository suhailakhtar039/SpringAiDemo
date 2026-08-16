package com.demo.springai.configuration;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import redis.clients.jedis.RedisClient;

@Configuration
public class AppConfig {

//    @Bean
//    public RedisClient redisClient() {
//        return RedisClient.builder()
//                .hostAndPort("localhost", 6379)
//                .build();
//    }

    @Bean
    @Primary
    public EmbeddingModel embeddingModel(
            @Qualifier("openAiEmbeddingModel")
            EmbeddingModel openAiEmbeddingModel) {

        return openAiEmbeddingModel;
    }

//    @Bean
//    public VectorStore vectorStore(
//            RedisClient redisClient,
//            @Qualifier("openAiEmbeddingModel")
//            EmbeddingModel embeddingModel) {
//
//        return RedisVectorStore.builder(redisClient, embeddingModel)
//                .build();
//    }

}
