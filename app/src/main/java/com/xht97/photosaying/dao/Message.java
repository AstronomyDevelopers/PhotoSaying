package com.xht97.photosaying.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Message{
    @Id
    private int messageId;
    private int userId;
    private String text;
    private String audio;
    private String photo;
    private int like;
    private int transmit;
    private int reply;
    @Generated(hash = 1660803338)
    public Message(int messageId, int userId, String text, String audio,
            String photo, int like, int transmit, int reply) {
        this.messageId = messageId;
        this.userId = userId;
        this.text = text;
        this.audio = audio;
        this.photo = photo;
        this.like = like;
        this.transmit = transmit;
        this.reply = reply;
    }
    @Generated(hash = 637306882)
    public Message() {
    }
    public int getMessageId() {
        return this.messageId;
    }
    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
    public int getUserId() {
        return this.userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getAudio() {
        return this.audio;
    }
    public void setAudio(String audio) {
        this.audio = audio;
    }
    public String getPhoto() {
        return this.photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public int getLike() {
        return this.like;
    }
    public void setLike(int like) {
        this.like = like;
    }
    public int getTransmit() {
        return this.transmit;
    }
    public void setTransmit(int transmit) {
        this.transmit = transmit;
    }
    public int getReply() {
        return this.reply;
    }
    public void setReply(int reply) {
        this.reply = reply;
    }
}
