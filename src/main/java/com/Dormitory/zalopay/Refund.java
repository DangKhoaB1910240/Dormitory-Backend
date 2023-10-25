package com.Dormitory.zalopay;

// Java version "1.8.0_201"
import org.apache.http.NameValuePair; // https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject; // https://mvnrepository.com/artifact/org.json/json
import com.Dormitory.zalopay.crypto.HMACUtil; // tải về ở mục DOWNLOADS

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

public class Refund {

    private static Map<String, String> config = new HashMap<String, String>(){{
        put("app_id", "2553");
        put("key1", "PcY4iZIKFCIdgZvA6ueMcMHHUbRLYjPL");
        put("key2", "kLtgPl8HHhfvMuDHPwKfgfsY4Ydm9eIz");
        put("refund_url", "https://sb-openapi.zalopay.vn/v2/refund");
    }};

    private static String getCurrentTimeString(String format) {
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT+7"));
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        fmt.setCalendar(cal);
        return fmt.format(cal.getTimeInMillis());
    }

    public static void main( String[] args ) throws Exception
    {
        String appid = config.get("appid");
        Random rand = new Random();
        long timestamp = System.currentTimeMillis(); // miliseconds
        String uid = timestamp + "" + (111 + rand.nextInt(888)); // unique id

        Map<String, Object> order = new HashMap<String, Object>(){{
            put("app_id", appid);
            put("zp_trans_id", 123456789);
            put("m_refund_id", getCurrentTimeString("yyMMdd") +"_"+ appid +"_"+uid);
            put("timestamp", timestamp);
            put("amount", 50000);
            put("description", "ZaloPay Intergration Demo");
        }};

        // appid|zptransid|amount|description|timestamp
        String data = order.get("app_id") +"|"+ order.get("zp_trans_id") +"|"+ order.get("amount")
                +"|"+ order.get("description") +"|"+ order.get("timestamp");
        order.put("mac", HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, config.get("key1"), data));

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(config.get("refund_url"));

        List<NameValuePair> params = new ArrayList<>();
        for (Map.Entry<String, Object> e : order.entrySet()) {
            params.add(new BasicNameValuePair(e.getKey(), e.getValue().toString()));
        }

        post.setEntity(new UrlEncodedFormEntity(params));

        CloseableHttpResponse res = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(res.getEntity().getContent()));
        StringBuilder resultJsonStr = new StringBuilder();
        String line;

        while ((line = rd.readLine()) != null) {
            resultJsonStr.append(line);
        }

        JSONObject result = new JSONObject(resultJsonStr.toString());
        for (String key : result.keySet()) {
            System.out.format("%s = %s\n", key, result.get(key));
        }
    }
}