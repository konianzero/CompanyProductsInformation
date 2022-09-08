package org.company.cache.external.jms;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.company.cache.exception.WaitingTimeExceededException;
import org.company.cache.external.jms.to.ProductInfo;
import org.company.cache.external.jms.to.ProductsInfoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

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
    private List<ProductInfo> receivedPayload = null;

    @PostConstruct
    public void init() {
        log.info("{} started", this.getClass().getSimpleName());
    }

    public List<ProductInfo> requestData(final Object payload) {
        sendMessage(payload);
        return getReceivedPayload();
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

    @SneakyThrows
    private List<ProductInfo> getReceivedPayload() {
        int responseTimeout = responseTimeoutInSeconds;
        try {
            do {
                if (receivedPayload != null) {
                    return receivedPayload;
                }
                Thread.sleep(1000);
                responseTimeout--;
            } while (responseTimeout > 0);
        } catch (InterruptedException e) {
            log.error("Thread {} interrupt", Thread.currentThread().getName());
            Thread.currentThread().interrupt();
        }
        throw new WaitingTimeExceededException("Response waiting time exceeded");
    }

    /**
     * Прослушиватель сообщений.
     */
    @JmsListener(destination = "${jms.queue.in}")
    private void receiveMessage(final Message<ProductsInfoResponse> message) {
        log.info("Header - {}", message.getHeaders());
        ProductsInfoResponse payload = message.getPayload();
        log.info("Inbound payload='{}'", payload);
        receivedPayload = payload.getProductsInfo();
    }
}
