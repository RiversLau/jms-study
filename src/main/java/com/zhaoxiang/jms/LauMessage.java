package com.zhaoxiang.jms;

import java.io.Serializable;

/**
 * Author: RiversLau
 * Date: 2017/8/22 10:26
 */
public class LauMessage implements Serializable {

    /**
     * 消息类型
     * PHONE_CODE:手机验证码
     * WX_TEMPLATE_MSG:微信模板消息
     * EMAIL_MSG:邮件消息
     */
    public enum MsgType {
        PHONE_CODE, WX_TEMPLATE_MSG, EMAIL_MSG
    }

    private MsgType type;
    private String content;

    public MsgType getType() {
        return type;
    }

    public void setType(MsgType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
