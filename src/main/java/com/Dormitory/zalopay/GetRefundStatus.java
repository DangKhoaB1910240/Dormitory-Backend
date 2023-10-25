package com.Dormitory.zalopay;

// Java version "1.8.0_201"
import org.apache.http.NameValuePair; // https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject; // https://mvnrepository.com/artifact/org.json/json
import com.Dormitory.zalopay.crypto.HMACUtil; // tải về ở mục DOWNLOADS
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetRefundStatus {
    private static final Map<String, String> config = new HashMap<String, String>(){{
        put("app_id", "2553");
        put("key1", "PcY4iZIKFCIdgZvA6ueMcMHHUbRLYjPL");
        put("key2", "kLtgPl8HHhfvMuDHPwKfgfsY4Ydm9eIz");
        put("query_refund_url", "https://sb-openapi.zalopay.vn/v2/query_refund");
    }};

    public static void main(String[] args) throws Exception {
        String m_refund_id = "190308_2553_123456";
        String timestamp = Long.toString(System.currentTimeMillis()); // miliseconds
        String data = config.get("app_id") +"|"+ m_refund_id  +"|"+ timestamp; // app_id|m_refund_id|timestamp
        String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, config.get("key1"), data);

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("app_id", config.get("app_id")));
        params.add(new BasicNameValuePair("m_refund_id", m_refund_id));
        params.add(new BasicNameValuePair("timestamp", timestamp));
        params.add(new BasicNameValuePair("mac", mac));

        URIBuilder uri = new URIBuilder(config.get("query_refund_url"));
        uri.addParameters(params);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(uri.build());
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