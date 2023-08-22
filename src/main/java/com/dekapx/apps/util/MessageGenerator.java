package com.dekapx.apps.util;

import com.dekapx.apps.model.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static com.dekapx.apps.common.CommonConstants.PRODUCER_BINDING_NAME;

@Component
public class MessageGenerator {
    private AtomicInteger counter = new AtomicInteger();
    @Autowired
    private StreamBridge streamBridge;

    @Scheduled(cron = "*/1 * * * * *")
    public void generateAndSendMessages() {
        IntStream.rangeClosed(1, 5).forEach(i -> {
            this.streamBridge.send(PRODUCER_BINDING_NAME,convertToByteArray(buildMessage("[StreamBridge] - Test Message #" + counter.incrementAndGet())));
        });
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
