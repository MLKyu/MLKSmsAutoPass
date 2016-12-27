package com.mlk.smsautopass.vo;

/**
 * Created by mingeek on 2016-12-26.
 */

public class SendLogInfo {
    private String fromPhoneNumber;
    private int id;
    private boolean isSuccess;
    private String sendText;
    private String sendTime;
    private String thisRule;
    private String toPhoneNumber;

    public SendLogInfo() {
    }

    public SendLogInfo(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, boolean paramBoolean) {
        this.id = paramInt;
        this.thisRule = paramString1;
        this.fromPhoneNumber = paramString2;
        this.toPhoneNumber = paramString3;
        this.sendText = paramString4;
        this.sendTime = paramString5;
        this.isSuccess = paramBoolean;
    }

    public String getFromPhoneNumber() {
        return this.fromPhoneNumber;
    }

    public int getId() {
        return this.id;
    }

    public String getSendText() {
        return this.sendText;
    }

    public String getSendTime() {
        return this.sendTime;
    }

    public String getThisRule() {
        return this.thisRule;
    }

    public String getToPhoneNumber() {
        return this.toPhoneNumber;
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }

    public void setFromPhoneNumber(String paramString) {
        this.fromPhoneNumber = paramString;
    }

    public void setId(int paramInt) {
        this.id = paramInt;
    }

    public void setSendText(String paramString) {
        this.sendText = paramString;
    }

    public void setSendTime(String paramString) {
        this.sendTime = paramString;
    }

    public void setSuccess(boolean paramBoolean) {
        this.isSuccess = paramBoolean;
    }

    public void setThisRule(String paramString) {
        this.thisRule = paramString;
    }

    public void setToPhoneNumber(String paramString) {
        this.toPhoneNumber = paramString;
    }
}