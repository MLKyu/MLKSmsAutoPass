package com.mlk.smsautopass.vo;

/**
 * Created by mingeek on 2016-12-26.
 */

public class PhoneNumberInfo {
    private boolean enabled;
    public int id;
    private String phoneNumber;
    private String profileName;
    private String recentTime;
    private String rules;

    public PhoneNumberInfo() {
    }

    public PhoneNumberInfo(int paramInt, String paramString1, String paramString2, boolean paramBoolean, String paramString3, String paramString4) {
        this.id = paramInt;
        this.phoneNumber = paramString1;
        this.profileName = paramString2;
        this.enabled = paramBoolean;
        this.rules = paramString3;
        this.recentTime = paramString4;
    }

    public int getId() {
        return this.id;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getProfileName() {
        return this.profileName;
    }

    public String getRecentTime() {
        return this.recentTime;
    }

    public String getRules() {
        return this.rules;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean paramBoolean) {
        this.enabled = paramBoolean;
    }

    public void setId(int paramInt) {
        this.id = paramInt;
    }

    public void setPhoneNumber(String paramString) {
        this.phoneNumber = paramString;
    }

    public void setProfileName(String paramString) {
        this.profileName = paramString;
    }

    public void setRecentTime(String paramString) {
        this.recentTime = paramString;
    }

    public void setRules(String paramString) {
        this.rules = paramString;
    }
}