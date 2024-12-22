package org.baouz.ems_api.consumer;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.baouz.ems_api.email.EmailDTO;
import org.baouz.ems_api.email.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitMQJsonConsumer {

    private final EmailService emailService;

    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
    public void receive(EmailDTO emailDTO) throws MessagingException {
        log.info("Received email: {}", emailDTO.toString());
        emailService.sendEmail(emailDTO);
    }

}
