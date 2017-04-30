package com.example.service.impl;

import com.example.service.ChatService;
import com.example.service.gitter.GitterClient;
import com.example.service.gitter.dto.MessageResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class GitterService implements ChatService<MessageResponse> {

    private final Flux<MessageResponse> gitterMessageSource;

    @Autowired
    public GitterService(@Qualifier("ReactorGitterClient") GitterClient gitterClient) {
        gitterMessageSource = ((Flux<MessageResponse>) gitterClient.getMessages(null)).publish().autoConnect(0);
    }

    @Override
    @SneakyThrows
    public Flux<MessageResponse> stream() {
        return gitterMessageSource;
    }
}