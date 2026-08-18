package com.demo.springai.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

@RestController
public class ImageGenController {

    private static final Logger log = LoggerFactory.getLogger(ImageGenController.class);

    private final OpenAiImageModel openAiImageModel;

    private ChatClient chatClient;

    public ImageGenController(OpenAiImageModel openAiImageModel, OpenAiChatModel chatModel) {
        this.openAiImageModel = openAiImageModel;
        this.chatClient = ChatClient.create(chatModel);
    }

    @GetMapping(value = "/image/{query}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> genImage(@PathVariable String query) {
        try {
            ImagePrompt prompt = new ImagePrompt(
                    query,
                    OpenAiImageOptions.builder()
                            .model("gpt-image-1")
                            .height(1024)
                            .width(1024)
                            .build()
            );

            ImageResponse response = openAiImageModel.call(prompt);
            var output = response.getResult().getOutput();

            String b64 = output.getB64Json();
            if (b64 != null) {
                byte[] imageBytes = Base64.getDecoder().decode(b64);
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG)
                        .body(imageBytes);
            }

            // fallback: some models still return a hosted url instead of b64
            log.warn("No b64Json in response, output was: {}", output);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (Exception e) {
            log.error("Image generation failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/image/describe")
    public String descImage(@RequestParam String query, @RequestParam MultipartFile file) {
        return chatClient
                .prompt()
                .user(us -> us.text(query)
                        .media(MimeTypeUtils.IMAGE_JPEG, file.getResource())
                )
                .call()
                .content();
    }
}