package com.wechat.servlet;


import com.wechat.utils.MessageUtil;
import com.wechat.utils.WechatUtil;
import com.wechat.vo.TextMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

/**
 * Created by wuxing on 2016/9/13.
 */
public class WechatServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();
        if (WechatUtil.checkSignature(signature, timestamp, nonce)) {
            out.print(echostr);
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        try {
            Map<String, String> map = MessageUtil.xmlToMap(request);
            String toUserName = map.get("ToUserName");
            String fromUserName = map.get("FromUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");
//            String createTime = map.get("CreateTime");
//            String msgId = map.get("MsgId");

            String message = null;
            if (MessageUtil.MESSAGE_TEXT.equals(msgType)) {
                MessageUtil.initMessage(toUserName, fromUserName, content);
            }else if(MessageUtil.MESSAGE_EVENT.equals(msgType)){
                String enentType = map.get("Event");
                if(MessageUtil.MESSAGE_SUBSCRIBE.equals(enentType)){
                    MessageUtil.initMessage(toUserName, fromUserName, MessageUtil.menuText());
                }

            }
            out.print(message);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
}
