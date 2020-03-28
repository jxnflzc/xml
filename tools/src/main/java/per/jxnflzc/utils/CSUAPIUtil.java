package per.jxnflzc.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

public class CSUAPIUtil {
    public static String getToken(String xh, String pwd) {
        JSONObject jsonObject = null;

        String host = "http://csujwc.its.csu.edu.cn/";
        String path = "/app.do";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> querys = new HashMap<String, String>();

        querys.put("method", "authUser");
        querys.put("xh", xh);
        querys.put("pwd", pwd);

        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            jsonObject = JSONObject.fromObject(EntityUtils.toString(response.getEntity()));
            return (String)jsonObject.get("token");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getUserInfo(String token, String xh) {
        JSONObject jsonObject = null;

        String host = "http://csujwc.its.csu.edu.cn/";
        String path = "/app.do";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> querys = new HashMap<String, String>();

        headers.put("token", token);
        querys.put("method", "getUserInfo");
        querys.put("xh", xh);

        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            jsonObject = JSONObject.fromObject(EntityUtils.toString(response.getEntity()));
            System.out.println(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONArray getClasses(String token, String xh, String xnxqid, String zc) {
        JSONArray jsonArray = null;

        String host = "http://csujwc.its.csu.edu.cn/";
        String path = "/app.do";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> querys = new HashMap<String, String>();

        headers.put("token", token);
        querys.put("method", "getKbcxAzc");
        querys.put("xh", xh);
        querys.put("xnxqid", xnxqid);
        querys.put("zc", zc);

        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            jsonArray = JSONArray.fromObject(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonArray;
    }
}
