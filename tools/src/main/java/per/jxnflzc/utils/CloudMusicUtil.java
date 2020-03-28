package per.jxnflzc.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CloudMusicUtil {
    public static List<String> getSongId(String name) {
        JSONObject jsonObject = null;
        List<String> songs = new ArrayList<>();

        String host = "https://api.imjad.cn";
        String path = "/cloudmusic";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> querys = new HashMap<String, String>();

        querys.put("type", "search");
        querys.put("search_type", "1");
        querys.put("s", name);

        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            jsonObject = JSONObject.fromObject(EntityUtils.toString(response.getEntity()));

            JSONObject result = JSONObject.fromObject(jsonObject.get("result"));
            JSONArray songsRet = JSONArray.fromObject(result.get("songs"));

            for (int i = 0; i < songsRet.size(); i++) {
                String id = Integer.toString((Integer)((JSONObject.fromObject(songsRet.get(i)).get("id"))));
                songs.add(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return songs;
    }

    public static String getSongUrl(String id) {
        JSONObject jsonObject = null;
        String url = null;

        String host = "https://api.imjad.cn";
        String path = "/cloudmusic";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> querys = new HashMap<String, String>();

        querys.put("type", "song");
        querys.put("id", id);

        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            jsonObject = JSONObject.fromObject(EntityUtils.toString(response.getEntity()));

            JSONArray data = JSONArray.fromObject(jsonObject.get("data"));

            for (int i = 0; i < data.size(); i++) {
                JSONObject song = JSONObject.fromObject(data.get(i));
                url = (String)song.get("url");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    public static void getSong(String name) {
        JSONObject jsonObject;

        String host = "https://api.imjad.cn";
        String path = "/cloudmusic";
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        Map<String, String> querys = new HashMap<String, String>();

        querys.put("type", "search");
        querys.put("search_type", "1");
        querys.put("s", name);

        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            jsonObject = JSONObject.fromObject(EntityUtils.toString(response.getEntity()));

            JSONObject result = JSONObject.fromObject(jsonObject.get("result"));
            JSONArray songsRet = JSONArray.fromObject(result.get("songs"));

            for (int i = 0; i < songsRet.size(); i++) {
                JSONObject songInfo = JSONObject.fromObject(songsRet.get(i));
                String songName = (String)songInfo.get("name");
                String id = Integer.toString((Integer)((songInfo.get("id"))));
                String singer = (String)JSONObject.fromObject(JSONArray.fromObject(songInfo.get("ar")).get(0)).get("name");
                System.out.println(songName + "\t" + getSongUrl(id) + "\t" + singer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
