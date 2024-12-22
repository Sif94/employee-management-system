package org.baouz.ems_api.publisher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.baouz.ems_api.email.EmailDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMQJsonProducer {
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public void sendJsonMessage(EmailDTO emailDTO){
        log.info("Sending email to RabbitMQ: {}", emailDTO.toString());
        rabbitTemplate.convertAndSend(exchange, routingKey, emailDTO);
    }
}
