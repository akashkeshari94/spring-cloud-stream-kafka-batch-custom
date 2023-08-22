package com.dekapx.apps.producer;

import com.dekapx.apps.model.MessageModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.util.Map;
import java.util.function.Supplier;

@Slf4j
@Component
public class KafkaMessageProducer {
    @Bean
    public Supplier<byte[]> producer() {
        return () -> convertToByteArray(buildMessage("[Producer] - Test Message"));
    }

    private MessageModel buildMessage(String json){
        return MessageModel.builder()
                .message(json)
                .headers(Map.of("testKey", "testValue"))
                .build();
    }

    private byte[] convertToByteArray(MessageModel messageModel){
        return SerializationUtils.serialize(messageModel);
    }
}
