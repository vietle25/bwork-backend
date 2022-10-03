package com.ieltshub.utility;

import com.ieltshub.config.ApplicationConfig;
import com.ieltshub.viewmodel.user.EmailModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author tuannd
 * @date 11/10/2016
 * @since 1.0
 */
@Component
public class EmailHelper {
    @Autowired
    private ApplicationConfig appConfig;

    /**
     * send email
     *
     * @param model
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     * @since 1.0
     */
    public void sendEmail(EmailModel model) throws MessagingException, UnsupportedEncodingException {
        Boolean hasToRecipent = model.getTo().isPresent() && !model.getTo().get().isEmpty();
        Boolean hasCCRecipient = model.getCc().isPresent() && !model.getCc().get().isEmpty();
        Boolean hasBCCRecipient = model.getBcc().isPresent() && !model.getBcc().get().isEmpty();
        if (!hasToRecipent && !hasCCRecipient && !hasBCCRecipient) {
            throw new IllegalArgumentException("don't have any recipients");
        }
        String username = appConfig.getAppEmail();
        String password = appConfig.getAppEmailPassword();
        Properties properties = System.getProperties();
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        Session session = Session.getDefaultInstance(properties);

        Transport transport = null;
        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            InternetAddress myAddress = new InternetAddress(username);
            myAddress.setPersonal("Chấm công HD", "UTF-8");
            mimeMessage.setFrom(myAddress);

            if (hasToRecipent) {
                Address[] recipients = getRecipients(model.getTo().get());
                if (recipients == null || recipients.length == 0) {
                    throw new IllegalArgumentException("don't have any valid TO recipients");
                }
                mimeMessage.addRecipients(Message.RecipientType.TO, recipients);
            }
            if (hasCCRecipient) {
                Address[] recipients = getRecipients(model.getCc().get());
                if (recipients != null && recipients.length != 0) {
                    mimeMessage.addRecipients(Message.RecipientType.CC, recipients);
                }
            }
            if (hasBCCRecipient) {
                Address[] recipients = getRecipients(model.getBcc().get());
                if (recipients != null && recipients.length != 0) {
                    mimeMessage.addRecipients(Message.RecipientType.BCC, recipients);
                }
            }

            mimeMessage.setSubject(model.getSubject(), "UTF-8");
            mimeMessage.setContent(model.getBody(), "text/html; charset=UTF-8");

            transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", username, password);
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        } finally {
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {

                }
            }
        }
    }

    private Address[] getRecipients(List<String> recipients) {
        List<Address> recipientsAddress = recipients.stream().map(recipient -> {
            try {
                return new InternetAddress(recipient);
            } catch (AddressException e) {
                return null;
            }
        }).filter(address -> address != null).collect(Collectors.toList());
        return recipientsAddress.toArray(new Address[recipientsAddress.size()]);
    }
}
