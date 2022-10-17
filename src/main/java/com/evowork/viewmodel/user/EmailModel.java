package com.evowork.viewmodel.user;

import java.util.List;
import java.util.Optional;

public class EmailModel {
    private String subject;
    private String body;
    private Optional<List<String>> to = Optional.empty();
    private Optional<List<String>> cc = Optional.empty();
    private Optional<List<String>> bcc = Optional.empty();

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Optional<List<String>> getTo() {
        return to;
    }

    public void setTo(Optional<List<String>> to) {
        this.to = to;
    }

    public Optional<List<String>> getCc() {
        return cc;
    }

    public void setCc(Optional<List<String>> cc) {
        this.cc = cc;
    }

    public Optional<List<String>> getBcc() {
        return bcc;
    }

    public void setBcc(Optional<List<String>> bcc) {
        this.bcc = bcc;
    }
}
