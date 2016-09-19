package com.wechat.utils;

import com.wechat.vo.AccessToken;
import com.wechat.vo.menu.Button;
import com.wechat.vo.menu.ClickButton;
import com.wechat.vo.menu.Menu;
import com.wechat.vo.menu.ViewButton;
import net.sf.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

/**
 * Created by wuxing on 2016/9/13.
 */
public class WechatUtil {
    private static final String TOKEN = "wuxing";
    private static final String APPID = "wx1b4d2d737b5d4fe7";
    private static final String APPSECRET = "e6df2b2a83782cfc605a9659780e458c";
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private static final String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
    private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";


    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] array = new String[]{TOKEN, timestamp, nonce};
        Arrays.sort(array);
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < array.length; i++) {
            stringBuffer.append(array[i]);
        }
        String encryptContent = SHA1Util.SHA1(stringBuffer.toString());
        return encryptContent.equals(signature);
    }

    public static AccessToken getAccessToken() {
        AccessToken accessToken = new AccessToken();
        String url = ACCESS_TOKEN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
        JSONObject jsonObject = HttpUtil.get(url);
        if (jsonObject != null) {
            accessToken.setAccessToken((String) jsonObject.get("access_token"));
            accessToken.setExpiresIn((int) jsonObject.get("expires_in"));
        }
        return accessToken;
    }

    public static String upload(String filePath, String accessToken, String type) throws IOException {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("文件不存在");
        }

        String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);

        URL urlObject = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();

        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setRequestProperty("Charset", "UTF-8");

        String BOUNDARY = "-----------" + System.currentTimeMillis();
        connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("--");
        stringBuilder.append(BOUNDARY);
        stringBuilder.append("\r\n");
        stringBuilder.append("Content-Disposition:form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
        stringBuilder.append("Content-Type:application/octet-stream\r\n\r\n");

        byte[] head = stringBuilder.toString().getBytes("UTF-8");

        OutputStream out = new DataOutputStream(connection.getOutputStream());
        out.write(head);

        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();

        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("UTF-8");

        out.write(foot);
        out.flush();
        out.close();

        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader reader = null;
        String result = null;
        try {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
            }
            if (result == null) {
                result = stringBuffer.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        JSONObject jsonObject = JSONObject.fromObject(result);
        System.out.println(jsonObject.toString());
        String typeName = "media_id";
        if (!MessageUtil.MESSAGE_IMAGE.equals(type)) {
            typeName = type + "_media_id";
        }
        String mediaId = (String) jsonObject.get(typeName);
        return mediaId;
    }

    /**
     * 初始化菜单
     *
     * @return
     */
    public static Menu initMenu() {
        Menu menu = new Menu();
        ClickButton button11 = new ClickButton();
        button11.setName("click菜单");
        button11.setType("click");
        button11.setKey("11");

        ViewButton button21 = new ViewButton();
        button21.setName("view菜单");
        button21.setType("view");
        button21.setUrl("http://www.baidu.com");

        ClickButton button31 = new ClickButton();
        button31.setName("扫码菜单");
        button31.setType("scancode_push");
        button31.setKey("31");

        ClickButton button32 = new ClickButton();
        button32.setName("地理菜单");
        button32.setType("location_select");
        button32.setKey("32");

        Button button = new Button();
        button.setName("菜单");
        button.setSub_button(new Button[]{button31, button32});

        menu.setButton(new Button[]{button11, button21, button});
        return menu;
    }

    public static int createMenu(String token, String menu) {
        int result = 0;
        String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = HttpUtil.post(url, menu);
        if (jsonObject != null) {
            result = (int) jsonObject.get("errcode");
        }
        return result;
    }
}
