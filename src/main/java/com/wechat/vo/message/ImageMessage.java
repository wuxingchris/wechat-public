package com.wechat.vo.message;

/**
 * Created by wuxing on 2016/9/18.
 */
public class ImageMessage extends BaseMessage{
    private com.wechat.vo.message.Image Image;

    public Image getImage() {
        return Image;
    }

    public void setImage(Image image) {
        Image = image;
    }
}
