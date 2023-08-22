package com.dekapx.apps.consumer;

import com.dekapx.apps.model.MessageModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Component
public class KafkaMessageConsumer {
    @Bean
    public Consumer<List<byte[]>> consumer() {
        return messages -> {
            log.info("------------------ Collection size [{}] ------------------", messages.size());
            messages.forEach(processMessage);
        };
    }

    private Consumer<byte[]> processMessage = (msg) -> {
        log.info("Message received {}", convertToMessageModel(msg));
    };

    private MessageModel convertToMessageModel(byte[] bytes){
        return (MessageModel) SerializationUtils.deserialize(bytes);
    }
}
