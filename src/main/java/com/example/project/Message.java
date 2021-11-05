package com.example.project;

public class Message {
    private String content;
    private String sender;
    private String receiver;
    private String timeStamp;

    public Message() {
        content = "";
        sender = "";
        receiver = "";
        timeStamp = "";
    }
    public String getContent() {
        return content;
    }
    public String getSender() {
        return sender;
    }
    public String getReceiver() {
        return receiver;
    }
    public String getTimeStamp() {
        return timeStamp;
    }
    public void setContent(String newContent) {
        content = newContent;
    }
    public void setReceiver(String newReceiver) {
        receiver = newReceiver;
    }
    public void setSender(String newSender) {
        sender = newSender;
    }
    public void setTimeStamp(String newTime) {
        timeStamp = newTime;
    }
}
