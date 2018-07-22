package com.AstronomyDevelopers.photosaying.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User {
    @Id
    private int userId;
    private String name;
    private String sex;
    private String logo;
    private String signature;
    private int level;
    private int exp;
    @Generated(hash = 1074983245)
    public User(int userId, String name, String sex, String logo, String signature,
            int level, int exp) {
        this.userId = userId;
        this.name = name;
        this.sex = sex;
        this.logo = logo;
        this.signature = signature;
        this.level = level;
        this.exp = exp;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public int getUserId() {
        return this.userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getLogo() {
        return this.logo;
    }
    public void setLogo(String logo) {
        this.logo = logo;
    }
    public String getSignature() {
        return this.signature;
    }
    public void setSignature(String signature) {
        this.signature = signature;
    }
    public int getLevel() {
        return this.level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public int getExp() {
        return this.exp;
    }
    public void setExp(int exp) {
        this.exp = exp;
    }
}
