package com.wechat.utils;

import com.thoughtworks.xstream.XStream;
import com.wechat.vo.TextMessage;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wuxing on 2016/9/13.
 */
public class MessageUtil {
    public static final String MESSAGE_TEXT = "text";
    public static final String MESSAGE_IMAGE = "image";
    public static final String MESSAGE_VOICE = "voice";
    public static final String MESSAGE_VIDEO = "video";
    public static final String MESSAGE_LINK = "link";
    public static final String MESSAGE_LOCATION = "location";
    public static final String MESSAGE_EVENT = "event";
    public static final String MESSAGE_SUBSCRIBE = "subscribe";
    public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
    public static final String MESSAGE_CLICK = "CLICK";
    public static final String MESSAGE_VIEW = "VIEW";


    /**
     * xml转为map
     * @param request
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
        Map<String, String> map = new HashMap<String, String>();

        SAXReader reader = new SAXReader();
        InputStream inputStream = request.getInputStream();
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();
        List<Element> list = root.elements();
        for (Element element : list) {
            map.put(element.getName(), element.getText());
        }
        inputStream.close();
        return map;
    }

    /**
     * object转为xml
     * @param textMessage
     * @return
     */
    public static String textMessageToXml(TextMessage textMessage){
        XStream xStream = new XStream();
        xStream.alias("xml", TextMessage.class);
        return xStream.toXML(textMessage);
    }

    public static String initMessage(String toUserName, String fromUserName, String content){
        TextMessage textMessage = new TextMessage();
        textMessage.setFromUserName(toUserName);
        textMessage.setToUserName(fromUserName);
        textMessage.setMsgType(MessageUtil.MESSAGE_TEXT);
        textMessage.setCreateTime((new Date()).getTime());
        textMessage.setContent("你发送的消息是: " + content);
        return textMessageToXml(textMessage);
    }

    /**
     * 主菜单
     * @return
     */
    public static String menuText(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("欢迎您的关注,请按照菜单提示进行操作: \n\n");
        stringBuffer.append("1. 课程1介绍\n");
        stringBuffer.append("2. 课程2介绍\n");
        stringBuffer.append("3. 课程3介绍\n");
        stringBuffer.append("回复?调出此菜单");
        return stringBuffer.toString();
    }
}
