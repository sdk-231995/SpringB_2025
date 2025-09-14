package com.abnamro.retailer.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class NotificationService {

    private final MessageSource messageSource;

    @Value("${retailer.notification.twilio.sms.accountSID}")
    private String ACCOUNT_SID;

    @Value("${retailer.notification.twilio.sms.authToken}")
    private String AUTH_TOKEN;

    @Value("${retailer.notification.twilio.sms.from}")
    private String smsFrom;

    public NotificationService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Async
    public void sendOrderCreatedSMS(String toNumber, String customerName, Long orderId) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber(toNumber),
                new PhoneNumber(smsFrom),
                buildOrderCreatedMessage(customerName, orderId)
        ).create();

        System.out.println(message.getSid());
    }

    public String buildOrderCreatedMessage(String customerName, Long orderId) {
        return messageSource.getMessage("sms.order.created", new String[]{customerName, orderId.toString()}, Locale.ENGLISH);
    }
}
