package com.demo.springai.configuration;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

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
    public ChatModel chatModel(
            @Qualifier("openAiChatModel")
            ChatModel openAiChatModel) {

        return openAiChatModel;
    }

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
