package com.Dormitory.zalopay;

// Java version "1.8.0_201"

import org.apache.http.NameValuePair; // https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject; // https://mvnrepository.com/artifact/org.json/json

import  com.Dormitory.zalopay.crypto.HMACUtil; // tải về ở mục DOWNLOADS

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class GetOrderStatus {
    private static final Map<String, String> config = new HashMap<String, String>(){{
        put("app_id", "2553");
        put("key1", "PcY4iZIKFCIdgZvA6ueMcMHHUbRLYjPL");
        put("key2", "kLtgPl8HHhfvMuDHPwKfgfsY4Ydm9eIz");
        put("endpoint", "https://sb-openapi.zalopay.vn/v2/query");
    }};

    public static void main(String[] args) throws Exception {
      String app_trans_id = "210608_2553_1623145380738";  // Input your app_trans_id
      String data = config.get("app_id") +"|"+ app_trans_id  +"|"+ config.get("key1"); // appid|app_trans_id|key1
      String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, config.get("key1"), data);

      List<NameValuePair> params = new ArrayList<>();
      params.add(new BasicNameValuePair("app_id", config.get("app_id")));
      params.add(new BasicNameValuePair("app_trans_id", app_trans_id));
      params.add(new BasicNameValuePair("mac", mac));

      URIBuilder uri = new URIBuilder(config.get("endpoint"));
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