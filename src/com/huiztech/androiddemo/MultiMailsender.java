package com.huiztech.androiddemo;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MultiMailsender {

    public boolean sendTextMail(MultiMailsenderInfo mailInfo) {
        MyAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        if (mailInfo.isValidate()) {
            // 如果需要身份验证，则创建一个密码验证器
            authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        Session sendmailSession = Session.getDefaultInstance(pro, authenticator);
        // 根据session创建一个邮件消息
        try {
            Message mailMessage = new MimeMessage(sendmailSession);
            // 创建邮件发送者地址
            Address from = new InternetAddress(mailInfo.getFromAddress());
            // 设置邮件消息的发送者
            mailMessage.setFrom(from);

            // 创建邮件的接收者地址，并设置到邮件消息中
            Address[] tos = null;
            String[] receivers = mailInfo.getReceivers();
            if (receivers != null) {
                // 为每个邮件接收者创建一个地址
                tos = new InternetAddress[receivers.length + 1];
                tos[0] = new InternetAddress(mailInfo.getToAddress());
                for (int i = 0; i < receivers.length; i++) {
                    tos[i + 1] = new InternetAddress(receivers[i]);
                }
            } else {
                tos = new InternetAddress[1];
                tos[0] = new InternetAddress(mailInfo.getToAddress());
            }
            // Message.RecipientType.TO属性表示接收者的类型为TO
            mailMessage.setRecipients(Message.RecipientType.TO, tos);
            // 设置邮件消息的主题
            mailMessage.setSubject(mailInfo.getSubject());
            // 设置邮件消息发送的时间
            mailMessage.setSentDate(new Date());
            // 设置邮件消息的主要内容
            String mailContent = mailInfo.getContent();
            mailMessage.setText(mailContent);
            // 发送邮件
            Transport.send(mailMessage);
            return true;
        } catch (Exception ex) {

            ex.printStackTrace();

        }
        return false;
    }

    public static boolean sendMailtoMultiReceiver(MultiMailsenderInfo mailInfo) {

        MyAuthenticator authenticator = null;
        if (mailInfo.isValidate()) {
            authenticator = new MyAuthenticator(mailInfo.getUserName(),

            mailInfo.getPassword());
        }

        Session sendMailSession = Session.getInstance(mailInfo.getProperties(), authenticator);

        try {

            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址
            Address from = new InternetAddress(mailInfo.getFromAddress());

            mailMessage.setFrom(from);
            // 创建邮件的接收者地址，并设置到邮件消息中
            Address[] tos = null;
            String[] receivers = mailInfo.getReceivers();

            if (receivers != null) {
                // 为每个邮件接收者创建一个地址
                tos = new InternetAddress[receivers.length + 1];
                tos[0] = new InternetAddress(mailInfo.getToAddress());

                for (int i = 0; i < receivers.length; i++) {
                    tos[i + 1] = new InternetAddress(receivers[i]);
                }

            } else {
                tos = new InternetAddress[1];

                tos[0] = new InternetAddress(mailInfo.getToAddress());
            }
            // 将所有接收者地址都添加到邮件接收者属性中

            mailMessage.setRecipients(Message.RecipientType.TO, tos);
            mailMessage.setSubject(mailInfo.getSubject());
            mailMessage.setSentDate(new Date());

            // 设置邮件内容
            Multipart mainPart = new MimeMultipart();
            BodyPart html = new MimeBodyPart();
            html.setContent(mailInfo.getContent(), "text/html; charset=GBK");
            mainPart.addBodyPart(html);
            mailMessage.setContent(mainPart);
            // 发送邮件
            Transport.send(mailMessage);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;

    }

    public static boolean sendMailtoMultiCC(MultiMailsenderInfo mailInfo) {
        MyAuthenticator authenticator = null;

        if (mailInfo.isValidate()) {
            authenticator = new MyAuthenticator(mailInfo.getUserName(),

            mailInfo.getPassword());

        }

        Session sendMailSession = Session.getInstance(mailInfo.getProperties(), authenticator);

        try {
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址
            Address from = new InternetAddress(mailInfo.getFromAddress());
            mailMessage.setFrom(from);
            // 创建邮件的接收者地址，并设置到邮件消息中

            Address to = new InternetAddress(mailInfo.getToAddress());
            mailMessage.setRecipient(Message.RecipientType.TO, to);
            // 获取抄送者信息
            String[] ccs = mailInfo.getCcs();

            if (ccs != null) {
                // 为每个邮件接收者创建一个地址
                Address[] ccAdresses = new InternetAddress[ccs.length];
                for (int i = 0; i < ccs.length; i++) {
                    ccAdresses[i] = new InternetAddress(ccs[i]);
                }
                // 将抄送者信息设置到邮件信息中，注意类型为Message.RecipientType.CC

                mailMessage.setRecipients(Message.RecipientType.CC, ccAdresses);
            }

            mailMessage.setSubject(mailInfo.getSubject());
            mailMessage.setSentDate(new Date());
            // 设置邮件内容
            Multipart mainPart = new MimeMultipart();
            BodyPart html = new MimeBodyPart();
            html.setContent(mailInfo.getContent(), "text/html; charset=GBK");

            mainPart.addBodyPart(html);
            mailMessage.setContent(mainPart);
            // 发送邮件
            Transport.send(mailMessage);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        MultiMailsenderInfo mailInfo = new MultiMailsenderInfo();

        mailInfo.setMailServerHost("smtp.163.com");
        mailInfo.setMailServerPort("25");
        mailInfo.setValidate(true);
        mailInfo.setUserName("xxx@163.com");
        mailInfo.setPassword("**********");// 您的邮箱密码
        mailInfo.setFromAddress("xxx@163.com");
        mailInfo.setToAddress("xxx@163.com");
        mailInfo.setSubject("设置邮箱标题");
        mailInfo.setContent("设置邮箱内容");
        String[] receivers = new String[] { "***@163.com", "***@tom.com" };
        String[] ccs = receivers;
        mailInfo.setReceivers(receivers);
        mailInfo.setCcs(ccs);
        // 这个类主要来发送邮件
        MultiMailsender sms = new MultiMailsender();

        sms.sendTextMail(mailInfo);// 发送文体格式
        MultiMailsender.sendMailtoMultiCC(mailInfo);// 发送抄送

    }

}
