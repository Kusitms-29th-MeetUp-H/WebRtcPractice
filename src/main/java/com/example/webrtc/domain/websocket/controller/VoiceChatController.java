package com.example.webrtc.domain.websocket.controller;

import com.example.webrtc.domain.websocket.dto.ResponseMessage;
import com.example.webrtc.domain.websocket.dto.VoiceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

// 백엔드 (Spring)
@Controller
public class VoiceChatController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/voice")
    public void handleVoiceData(@RequestBody VoiceData voiceData) throws IOException {
        // Base64 인코딩된 데이터를 디코딩
        byte[] audioBytes = Base64.getDecoder().decode(voiceData.getData());

        // 파일 저장 경로 설정 (예: 현재 디렉토리의 "uploads" 폴더)
        Path path = FileSystems.getDefault().getPath("uploads", "audioFile-" + System.currentTimeMillis() + ".wav");

        try {
            // 파일로 저장
            Files.createDirectories(path.getParent()); // 상위 디렉토리가 없으면 생성
            Files.write(path, audioBytes); // 파일 쓰기

            // 처리 결과를 클라이언트에게 전송
            messagingTemplate.convertAndSend("/topic/voiceResponse", new ResponseMessage("File processed successfully"));

        } catch (IOException e) {
            e.printStackTrace();
            // 오류 처리 결과를 클라이언트에게 전송
            messagingTemplate.convertAndSend("/topic/voiceResponse", new ResponseMessage("Failed to process the file"));
        }
    }
    @MessageMapping("/voice")
    @SendTo("/topic/voice")
    public String handleVoiceMessage(@Payload String message) {
        // 수신한 음성 데이터 처리 로직 구현
        return message;
    }
}
