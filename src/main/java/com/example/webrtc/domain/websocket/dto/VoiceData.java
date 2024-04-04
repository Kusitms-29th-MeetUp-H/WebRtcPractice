package com.example.webrtc.domain.websocket.dto;

import lombok.Getter;

@Getter
public class VoiceData {
    private String data; // Base64 인코딩된 오디오 데이터

}
