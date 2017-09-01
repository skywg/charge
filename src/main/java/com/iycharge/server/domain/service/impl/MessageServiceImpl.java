package com.iycharge.server.domain.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iycharge.server.domain.entity.notification.Notification;
import com.iycharge.server.domain.repository.NotificationRepository;
import com.iycharge.server.domain.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Set;

@Service
public class MessageServiceImpl implements MessageService {

    private final static String PUSH_API_PATH = "notifications";
    private final static String MAIL_API_PATH = "mails";
    private final static String SMS_API_PATH = "sms";
   // private String endpoint = "http://notifier.itemspot.com/";
    private String endpoint =" http://www.sendcloud.net/smsapi/send";
   
    @Autowired
    private NotificationRepository notificationRepository;
    private RestTemplate restTemplate = new RestTemplate();

    @Override
	public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
	public void push(String alert, Integer badge, Notification notification) {
        sendPushMessage(PUSH_API_PATH, null, alert, badge, asString(notification));
    }

    @Override
	public void push(Set<String> group, String alert, Integer badge, Notification notification) {

        if (group.isEmpty() || notification == null)
            return;

        sendPushMessage(PUSH_API_PATH, StringUtils.collectionToCommaDelimitedString(group), alert, badge, asString(notification));
    }

    @Override
	public void push(String deviceToken, String alert, Integer badge, Notification notification) {
        if (notification == null) return;
        notificationRepository.save(notification);

        if (deviceToken == null || deviceToken.equals("")) return;
        sendPushMessage(PUSH_API_PATH, deviceToken, alert, badge, asString(notification));
    }


    @Override
	public void sendMail(String message) {
        if (StringUtils.isEmpty(message)) return;

        sendMessage(MAIL_API_PATH, null, message);
    }

    @Override
	public void sendMail(Set<String> emails, String message) {
        if (emails.isEmpty() || StringUtils.isEmpty(message)) return;

        sendMessage(MAIL_API_PATH, StringUtils.collectionToCommaDelimitedString(emails), message);
    }

    @Override
	public void sendMail(String email, String message) {
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(message))
            return;

        sendMessage(MAIL_API_PATH, email, message);
    }

    @Override
	public void sendSMS(String message) {
        if (StringUtils.isEmpty(message)) return;

        sendMessage(SMS_API_PATH, null, message);
    }

    @Override
	public void sendSMS(Set<String> mobiles, String message) {
        if (mobiles.isEmpty() || StringUtils.isEmpty(message)) return;

        sendMessage(SMS_API_PATH, StringUtils.collectionToCommaDelimitedString(mobiles), message);
    }

    @Override
	public void sendSMS(String mobile, String message) {
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(message)) return;
        sendMessage(SMS_API_PATH, mobile, message);
    }

    private String asString(Notification notification){
        ObjectMapper mapper = new ObjectMapper();
        String message = null;

        try {
            message = mapper.writeValueAsString(notification);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return message;
    }

    private void sendPushMessage(String path, String recipient, String alert, Integer badge, String message) {
        if (StringUtils.isEmpty(message)) return;

        MultiValueMap<String, String> mvm = new LinkedMultiValueMap<>();
        mvm.add("recipient", recipient);
        mvm.add("alert", alert);
        mvm.add("badge", badge.toString());
        mvm.add("message", message);

        try {
            restTemplate.postForObject(endpoint + path, mvm, String.class);
        }
        catch (RestClientException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String path, String recipient, String message) {
        if (StringUtils.isEmpty(message)) return;

        MultiValueMap<String, String> mvm = new LinkedMultiValueMap<>();
        mvm.add("recipient", recipient);
        mvm.add("message", message);

        try {
            restTemplate.postForObject(endpoint + path, mvm, String.class);
        }
        catch (RestClientException e) {
            e.printStackTrace();
        }
    }
}
