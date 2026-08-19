package com.demo.springai.controller;

import com.openai.models.audio.AudioResponseFormat;
import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AudioGenController {

    private OpenAiAudioTranscriptionModel audioModel;
    private OpenAiAudioSpeechModel audioSpeechModel;

    public AudioGenController(
            OpenAiAudioTranscriptionModel audioModel
            , OpenAiAudioSpeechModel audioSpeechModel
    ) {
        this.audioModel = audioModel;
        this.audioSpeechModel = audioSpeechModel;
    }

    @PostMapping("/api/stt")
    public String speechToText(@RequestParam MultipartFile file) {

        OpenAiAudioTranscriptionOptions options =
                OpenAiAudioTranscriptionOptions.builder()
                        .responseFormat(AudioResponseFormat.SRT)
                        .build();

        AudioTranscriptionPrompt transcriptionPrompt =
                new AudioTranscriptionPrompt(file.getResource(), options);

        AudioTranscriptionResponse response = audioModel.call(transcriptionPrompt);
        return response
                .getResult()
                .getOutput();
    }

    @PostMapping("api/tts")
    public byte[] tts(@RequestParam String text){
        return audioSpeechModel.call(text);
    }

}
