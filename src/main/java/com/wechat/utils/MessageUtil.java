package com.wechat.utils;

import com.thoughtworks.xstream.XStream;
import com.wechat.vo.message.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by wuxing on 2016/9/13.
 */
public class MessageUtil {
    public static final String MESSAGE_TEXT = "text";
    public static final String MESSAGE_NEWS = "news";
    public static final String MESSAGE_IMAGE = "image";
    public static final String MESSAGE_VOICE = "voice";
    public static final String MESSAGE_VIDEO = "video";
    public static final String MESSAGE_MUSIC = "music";
    public static final String MESSAGE_LINK = "link";
    public static final String MESSAGE_LOCATION = "location";
    public static final String MESSAGE_EVENT = "event";
    public static final String MESSAGE_THUMB = "thumb";
    public static final String MESSAGE_SUBSCRIBE = "subscribe";
    public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
    public static final String MESSAGE_CLICK = "CLICK";
    public static final String MESSAGE_VIEW = "VIEW";
    public static final String MESSAGE_SCANCODE = "scancode_push";


    /**
     * xml转为map
     *
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
     *
     * @param textMessage
     * @return
     */
    public static String textMessageToXml(TextMessage textMessage) {
        XStream xStream = new XStream();
        xStream.alias("xml", TextMessage.class);
        return xStream.toXML(textMessage);
    }

    public static String initMessage(String toUserName, String fromUserName, String content) {
        TextMessage textMessage = new TextMessage();
        textMessage.setFromUserName(toUserName);
        textMessage.setToUserName(fromUserName);
        textMessage.setMsgType(MessageUtil.MESSAGE_TEXT);
        textMessage.setCreateTime((new Date()).getTime());
        textMessage.setContent(content);
        return textMessageToXml(textMessage);
    }

    /**
     * 主菜单
     *
     * @return
     */
    public static String poemMenu() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("欢迎您的关注,请按照菜单提示进行操作: \n\n");
        stringBuffer.append("1. 望庐山瀑布\n");
        stringBuffer.append("2. 早发白帝城\n");
        stringBuffer.append("3. 送孟浩然之广陵\n");
        stringBuffer.append("4. 图文消息\n");
        stringBuffer.append("5. 图片消息\n");
        stringBuffer.append("6. 音乐消息\n");
        stringBuffer.append("回复?调出此菜单");
        return stringBuffer.toString();
    }

    public static String firstPoem() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("望庐山瀑布:\n\n");
        stringBuffer.append("日照香炉生紫烟\n");
        stringBuffer.append("遥看瀑布挂前川\n");
        stringBuffer.append("飞流直下三千尺\n");
        stringBuffer.append("疑是银河落九天\n");
        return stringBuffer.toString();
    }

    public static String secondPoem() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("早发白帝城:\n\n");
        stringBuffer.append("朝辞白帝彩云间\n");
        stringBuffer.append("千里江陵一日还\n");
        stringBuffer.append("两岸猿声啼不尽\n");
        stringBuffer.append("轻舟已过万重山\n");
        return stringBuffer.toString();
    }

    public static String thirdPoem() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("送孟浩然之广陵:\n\n");
        stringBuffer.append("故人西辞黄鹤楼\n");
        stringBuffer.append("烟花三月下扬州\n");
        stringBuffer.append("孤帆远影碧空尽\n");
        stringBuffer.append("唯见长江天际流\n");
        return stringBuffer.toString();
    }

    public static String newsMessageToXml(NewsMessage newsMessage) {
        XStream xStream = new XStream();
        xStream.alias("xml", NewsMessage.class);
        xStream.alias("item", News.class);
        return xStream.toXML(newsMessage);
    }


    public static String initNewsMessage(String toUserName, String fromUserName) {
        List<News> newsList = new ArrayList<News>();
        NewsMessage newsMessage = new NewsMessage();

        News news = new News();
        news.setTitle("海贼王介绍");
        news.setDescription("海贼王是一部很好看的动漫");
        news.setPicUrl("http://wuxing.imwork.net/wechat-public/image/OP.jpg");
        news.setUrl("http://www.iqiyi.com/a_19rrhb3xvl.html?vfm=2008_aldbd");
        newsList.add(news);

        News secondNews = new News();
        secondNews.setTitle("棋魂介绍");
        secondNews.setDescription("棋魂是一部很好看的动漫");
        secondNews.setPicUrl("http://wuxing.imwork.net/wechat-public/image/Hikaru_no_Go.jpg");
        secondNews.setUrl("http://www.youku.com/show_page/id_zcc006556962411de83b1.html");
        newsList.add(secondNews);

        newsMessage.setFromUserName(toUserName);
        newsMessage.setToUserName(fromUserName);
        newsMessage.setMsgType(MessageUtil.MESSAGE_NEWS);
        newsMessage.setCreateTime((new Date()).getTime());
        newsMessage.setArticleCount(newsList.size());
        newsMessage.setArticles(newsList);

        return newsMessageToXml(newsMessage);
    }

    public static String imageMessageToXml(ImageMessage imageMessage) {
        XStream xStream = new XStream();
        xStream.alias("xml", ImageMessage.class);
        xStream.alias("Image", Image.class);
        return xStream.toXML(imageMessage);
    }

    public static String initImageMessage(String toUserName, String fromUserName) {
        Image image = new Image();
        image.setMediaId("2UZSHlM6JpXE58tFE-nS52GM_BJkD1UpCfSkKY68Sx_Sfz28CLxSPaNF_cLpDJC3");
        ImageMessage imageMessage = new ImageMessage();
        imageMessage.setFromUserName(toUserName);
        imageMessage.setToUserName(fromUserName);
        imageMessage.setMsgType(MessageUtil.MESSAGE_IMAGE);
        imageMessage.setCreateTime((new Date()).getTime());
        imageMessage.setImage(image);
        return imageMessageToXml(imageMessage);
    }

    public static String musicMessageToXml(MusicMessage musicMessage) {
        XStream xStream = new XStream();
        xStream.alias("xml", MusicMessage.class);
        xStream.alias("Music", Music.class);
        return xStream.toXML(musicMessage);
    }

    public static String initMusicMessage(String toUserName, String fromUserName) {
        Music music = new Music();
        music.setTitle("开不了口");
        music.setDescription("周杰伦-开不了口");
        music.setMusicUrl("http://wuxing.imwork.net/wechat-public/music/开不了口.mp3");
        music.setHQMusicUrl("http://wuxing.imwork.net/wechat-public/music/开不了口.mp3");
        music.setThumbMediaId("RAyf1VxSp_nE37WFVXlxj7HfNXbvrAQFIMoXVyRwB8whZ4GlL7nnmVReKv99a57U");

        MusicMessage musicMessage = new MusicMessage();
        musicMessage.setFromUserName(toUserName);
        musicMessage.setToUserName(fromUserName);
        musicMessage.setMsgType(MessageUtil.MESSAGE_MUSIC);
        musicMessage.setCreateTime((new Date()).getTime());
        musicMessage.setMusic(music);
        return musicMessageToXml(musicMessage);
    }
}
