package com.xyz.retail.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.xyz.retail.entities.OrderEntity;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class SmsService {

    @Value("${sms.twilio.accountSid}")
    private String twilioAccountSid;

    @Value("${sms.twilio.authToken}")
    private String twilioAuthToken;

    @Value("${sms.twilio.number}")
    private String twilioNumber;

    @HystrixCommand(fallbackMethod = "fallbackSMS")
    public void sendSMS(final OrderEntity orderEntity, final String customerName, final String customerPhoneNumber) throws ApiException {
        if (!StringUtils.isBlank(twilioAccountSid)) {
            Twilio.init(twilioAccountSid, twilioAuthToken);
            Message.creator(new PhoneNumber(customerPhoneNumber),
                    new PhoneNumber(twilioNumber), String.format("Thanks %s for choosing us,order created with id: %d and total amount is: EUR %,.2f", customerName, orderEntity.getId(), orderEntity.getTotalAmount())).create();
        }
    }

    public void fallbackSMS(final OrderEntity orderEntity, final String customerName, final String customerPhoneNumber) {
        log.info("SMS fallback triggered");
        // send email
    }
}
