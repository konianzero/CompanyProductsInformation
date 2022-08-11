package org.company.remote.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.remote.to.ProductInfo;
import org.company.remote.to.ProductInfoRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class JmsClient {

    private final JmsTemplate jmsTemplate;
    private final Map<Integer, ProductInfo> mockInfoMap;
    private final ObjectMapper mapper;

    @Value("${jms.queue.out}")
    private String outQueueName;
    @Value("${jms.queue.in}")
    private String inQueueName;

    @PostConstruct
    public void init() {
        log.info("{} started", this.getClass().getSimpleName());
    }

    /**
     * Посылает сообщение в очередь.
     *
     * @param payload тело сообщения.
     */
    public void sendMessage(final Object payload) {
        jmsTemplate.convertAndSend(outQueueName, payload);
    }

    /**
     * Прослушиватель сообщений.
     */
    @JmsListener(destination = "${jms.queue.in}")
    public void receiveMessage(final Message<ProductInfoRequest> message) throws JsonProcessingException {
        log.info("Header - {}", message.getHeaders());
        ProductInfoRequest payload = message.getPayload();
        log.info("Inbound payload='{}'", payload);
        sendMessage(mockInfoMap.getOrDefault(payload.getId(), new ProductInfo()));
    }
}
