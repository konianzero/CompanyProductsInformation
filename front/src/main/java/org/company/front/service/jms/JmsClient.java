package org.company.front.service.jms;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.front.service.jms.to.ProductInfo;
import org.company.front.util.exception.WaitingTimeExceededException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@Slf4j
@RequiredArgsConstructor
public class JmsClient {

    private final JmsTemplate jmsTemplate;

    @Value("${jms.queue.out}")
    private String outQueueName;
    @Value("${jms.queue.in}")
    private String inQueueName;
    @Value("${jms.response-timeout-seconds}")
    private int responseTimeoutInSeconds;
    private ProductInfo receivedPayload = null;

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
        receivedPayload = null;
    }

    public ProductInfo getReceivedPayload() {
        try {
            do {
                if (receivedPayload != null) {
                    return receivedPayload;
                }
                Thread.sleep(1000);
                responseTimeoutInSeconds--;
            } while (responseTimeoutInSeconds > 0);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        throw new WaitingTimeExceededException("Response waiting time exceeded");
    }

    /**
     * Прослушиватель сообщений.
     */
    @JmsListener(destination = "${jms.queue.in}")
    public void receiveMessage(final Message<ProductInfo> message) {
        log.info("Header - {}", message.getHeaders());
        ProductInfo payload = message.getPayload();
        log.info("Inbound payload='{}'", payload);
        receivedPayload = payload;
    }
}
