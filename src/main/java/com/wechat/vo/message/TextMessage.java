package com.wechat.vo.message;

/**
 * Created by wuxing on 2016/9/13.
 */
public class TextMessage extends BaseMessage {

    private String Content;
    private String MsgId;

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }
}

