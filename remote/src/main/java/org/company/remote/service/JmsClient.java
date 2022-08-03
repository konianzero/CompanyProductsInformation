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
     * @param message сообщение.
     */
    public void sendMessage(final Object message) {
        jmsTemplate.convertAndSend(outQueueName, message);
    }

    /**
     * Прослушиватель сообщений.
     */
    @JmsListener(destination = "${jms.queue.in}")
    public void receiveMessage(final Message<String> message) {
        log.info("Header - {}", message.getHeaders());
        String inPayload = message.getPayload();
        log.info("Inbound json='{}'", inPayload);

        ProductInfoRequest request = null;
        try {
            request = mapper.readValue(inPayload, ProductInfoRequest.class);
        } catch (Exception e) {
            log.error("Error while converting jms message payload(object) to json", e);
        }

        String outPayload = "";
        try {
            outPayload = mapper.writeValueAsString(mockInfoMap.get(request.getId()));
            log.info("Outbound json='{}'", outPayload);
        } catch (JsonProcessingException e) {
            log.error("Error while converting jms message from json", e);
        }
        sendMessage(outPayload);
    }
}
