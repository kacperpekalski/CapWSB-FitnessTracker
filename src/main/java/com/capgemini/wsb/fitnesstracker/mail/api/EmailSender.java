package com.capgemini.wsb.fitnesstracker.mail.api;

/**
 * API interface for component responsible for sending emails.
 */
public interface EmailSender {

    void send(EmailDto email);

}
