package com.example.webrtc.domain.websocket.controller;

import com.example.webrtc.domain.websocket.dto.SignalMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
@Controller
public class SignalingController {

    @MessageMapping("/signal")
    @SendTo("/topic/signal")
    public SignalMessage handleSignal(@Payload SignalMessage message) {
        return message;
    }
}