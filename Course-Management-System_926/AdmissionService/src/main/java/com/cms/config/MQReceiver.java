package com.cms.config;

import com.cms.model.MQMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class MQReceiver {

    @Autowired
    JavaMailSender emailSender;

    @RabbitListener(queues = {"register_queue"})
    public void receiveMessage(@Payload MQMessage message) {

        System.out.println("Received message: " + message);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("homesweethome182011@gmail.com");
		mailMessage.setTo(message.getAssociateEmail());
		mailMessage.setSubject("Welcome to TekGain E-Learning");

		mailMessage.setText("Dear " + message.getAssociateName() + ",\n" +
                            "\n" +
                            "Welcome to TekGain E-Learning \n" +
                            "You are Successfully Registered. " +
                            "Below are you Registration details: \n" +
                            "\n" +
                            "Registration Id: " + message.getToSendRegistrationId() + "\n" +
                            "Course Id: " + message.getToSendCourseId() + "\n" +
                            "Associate Id: " + message.getToSendAssociateId() + "\n" +
                            "Date: " + message.getToSendDate() + "\n" +
                            "\n" +
                            "Happy Learning!!!\n" +
                            "\n" +
                            "\n" +
                            "Thanks & Regards,\n" +
                            "Team TekGain\n");

		this.emailSender.send(mailMessage);

    }

}
