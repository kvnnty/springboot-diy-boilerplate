package com.kvn.starter.v1.services.mail;

public interface MailService {
  void sendMail(String to, String subject, String message);
}
