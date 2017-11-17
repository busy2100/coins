package com.example.button.webcoursesbangkok;

import java.util.Date;

public class ChatMessage {

    private String messageText;
    private String messageUser;
    private String channel;
    private long messageTime;

    public ChatMessage(String messageText, String messageUser, String channel) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.channel = channel;

        // Initialize to current time
        messageTime = new Date().getTime();
    }

    public ChatMessage(){

    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }


    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}