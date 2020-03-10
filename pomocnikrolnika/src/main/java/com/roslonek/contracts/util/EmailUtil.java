package com.roslonek.contracts.util;

import com.roslonek.contracts.entities.Worker;

import java.util.List;

public interface EmailUtil {

    void sendEmail(String toAdress, String subject, String body);
    void sendFile(List<Worker> workers, String when);
}
