package io.pivotal.notifier;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.function.Consumer;
import java.util.function.Function;

@SpringBootApplication
@Log
public class NotifierApplication implements Consumer<Message> {

	@Autowired
	public JavaMailSender emailSender;

	public static void main(String[] args) {
		SpringApplication.run(NotifierApplication.class, args);
	}

	@Override
	public void accept(Message message) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setTo(message.getTo());
		simpleMailMessage.setSubject(message.getSubject());
		simpleMailMessage.setText(message.getBody());
		simpleMailMessage.setFrom(message.getFrom());
		emailSender.send(simpleMailMessage);
		log.info("Sent email to " + message.getTo());
	}
}
