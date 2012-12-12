package com.huiztech.androiddemo;

public class MultiMailsenderInfo extends MailSenderInfo {
    // 邮件接收者可以有多个
    private String[] receivers;
    // 邮件的抄送者可以有多个
    private String[] ccs;

    public String[] getCcs() {
        return ccs;
    }

    public void setCcs(String[] ccs) {
        this.ccs = ccs;
    }

    public String[] getReceivers() {
        return receivers;
    }

    public void setReceivers(String[] receiverws) {
        this.receivers = receiverws;
    }

}
