package com.roslonek.contracts.util;

import com.roslonek.contracts.controllers.WorkerController;
import com.roslonek.contracts.entities.Worker;
import com.roslonek.contracts.repositories.UserRepository;
import com.roslonek.contracts.repositories.WorkerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmailUtilImpl implements EmailUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkerController.class);

    @Autowired
    private JavaMailSender sender;

    @Autowired
    UserRepository userRepository;

    @Autowired
    WorkerRepository workerRepository;

    @Override
    public void sendEmail(String toAdress, String subject, String body) {

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(toAdress);
            helper.setSubject(subject);
            helper.setText(body);
        } catch (MessagingException e) {
            e.printStackTrace();
        }


        sender.send(message);

    }


    @Override
    public void sendFile(List<Worker> workers, String when) {


        MimeMessage message = sender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(userRepository.findById(workers.get(0).getUserId()).get().getEmail());
            helper.setSubject("Dokumenty");
            helper.setText("Zamieszczone w załączniku.");

            FileSystemResource file;

            if (when.equals("start")) {
                for (Worker worker : workers) {
                    String name = worker.getName() + " " + worker.getSurname();
                    file = new FileSystemResource(worker.getId() + " - praca.docx");
                    helper.addAttachment(name + " - praca", file);
                    file = new FileSystemResource(worker.getId() + " - najem.docx");
                    helper.addAttachment(name + " - najem", file);
                }
            }
            else {
                for (Worker worker : workers) {
                    String name = worker.getName() + " " + worker.getSurname();
                    file = new FileSystemResource(worker.getId() + " - rozliczenie.docx");
                    helper.addAttachment(name + " - rozliczenie", file);

                }
            }
        } catch (MessagingException e) {
            throw new MailParseException(e);
        }


        sender.send(message);
    }
}
