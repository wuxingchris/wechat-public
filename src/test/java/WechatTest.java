import com.wechat.utils.WechatUtil;
import com.wechat.vo.AccessToken;
import net.sf.json.JSONObject;

/**
 * Created by wuxing on 2016/9/18.
 */
public class WechatTest {
    public static void main(String[] args) {
        try {
            AccessToken accessToken = WechatUtil.getAccessToken();
            System.out.println("token:" + accessToken.getAccessToken());
            System.out.println("time:" + accessToken.getExpiresIn());

//            String path = "E:/github/wuxing.jpg";
//            String mediaId = WechatUtil.upload(path, accessToken.getAccessToken(), MessageUtil.MESSAGE_THUMB);
//            System.out.println("mediaId:" + mediaId);

            String menu = JSONObject.fromObject(WechatUtil.initMenu()).toString();
            int result = WechatUtil.createMenu(accessToken.getAccessToken(), menu);

            if (result == 0) {
                System.out.println("创建菜单成功");
            } else {
                System.out.println("错误码:" + result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
