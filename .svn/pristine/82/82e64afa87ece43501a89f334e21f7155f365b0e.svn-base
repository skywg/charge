package com.iycharge.server.domain.service;


import com.iycharge.server.domain.entity.notification.Notification;

import java.util.Set;

public interface MessageService {

    public void setEndpoint(String endpoint);

    /*
     * Push Notification
     */

    // Push notification to all users.
    public void push(String alert, Integer badge, Notification notification);

    // Push notification to group.
    public void push(Set<String> group, String alert, Integer badge, Notification notification);

    // Push notification to single user.
    public void push(String deviceToken, String alert, Integer badge, Notification notification);

    /*
     * Email
     */

    // Send mail to all users.
    public void sendMail(String message);

    // Send mail to recipients.
    public void sendMail(Set<String> emails, String message);

    // Send mail to single user.
    public void sendMail(String email, String message);

    /*
     * SMS
     */

    // Send SMS to all users.
    public void sendSMS(String message);

    // Send SMS to mobiles.
    public void sendSMS(Set<String> mobiles, String message);

    // Send SMS to single mobile.
    public void sendSMS(String mobile, String message);
}
