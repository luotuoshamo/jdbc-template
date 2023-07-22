package cn.topicstudy.jdbctemplate.util;

import java.util.HashMap;
import java.util.Map;

public class UrlUtil {
    private UrlUtil() {
    }

    /**
     * 获取url中的query string，然后转为map
     *
     * @param url http://www.123.com?name=wjh&q=apple
     * @return {name:wjh, q:apple}
     */
    public static Map<String, String> getQueryMapFromUrl(String url) {
        Map<String, String> queryMap = new HashMap<>();
        if (StringUtil.isBlank(url)) return queryMap;
        if (!url.contains("?")) return queryMap;

        // name=wjh&q=apple
        String queryString = url.split("\\?")[1];
        String[] kvs = queryString.split("&");
        for (String kv : kvs) {
            String[] split = kv.split("=");
            queryMap.put(split[0], split[1]);
        }
        return queryMap;
    }

    public static void main(String[] args) {
        System.out.println(getQueryMapFromUrl("http://www.123.com?name=wjh&q=apple"));
    }
}
