package com.demo.springai.controller;

import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AudioGenController {

    private OpenAiAudioTranscriptionModel audioModel;

    public AudioGenController(OpenAiAudioTranscriptionModel audioModel) {
        this.audioModel = audioModel;
    }

    @PostMapping("/api/stt")
    public String speechToText(@RequestParam MultipartFile file) {
        AudioTranscriptionPrompt transcriptionPrompt =
                new AudioTranscriptionPrompt(file.getResource());
        AudioTranscriptionResponse response = audioModel.call(transcriptionPrompt);
        return response
                .getResult()
                .getOutput();
    }

}
