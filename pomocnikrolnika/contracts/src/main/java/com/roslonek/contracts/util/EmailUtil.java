package com.roslonek.contracts.util;

public interface EmailUtil {

    void sendEmail(String toAdress, String subject, String body);
    void sendFile(String toAdress, String subject, String body, String name);
}
