// package com.Dormitory.zalopay;

// import java.util.HashMap;
// import java.util.Map;

// import org.apache.http.client.methods.CloseableHttpResponse;
// import org.apache.http.client.methods.HttpPost;
// import org.apache.http.impl.client.CloseableHttpClient;
// import org.apache.http.impl.client.HttpClients;
// import org.apache.http.util.EntityUtils;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import com.Dormitory.zalopay.crypto.HMACUtil;

// import io.jsonwebtoken.io.IOException;

// @RestController
// @RequestMapping("/zalopay")
// public class ZaloPayResource {

//     @Value("${zalopay.app_id}")
//     private String appId;

//     @Value("${zalopay.key1}")
//     private String key1;

//     @Value("${zalopay.create_order_url}")
//     private String createOrderUrl;

//     @Value("${zalopay.query_order_url}")
//     private String queryOrderUrl;

//     @PostMapping("/createOrder")
//     public String createOrder(@RequestBody Map<String, Object> orderRequest) throws IOException {
//         // Tạo dữ liệu đơn hàng
//         orderRequest.put("app_id", appId);
//         String data = convertMapToJson(orderRequest);
//         String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, key1, data);
//         orderRequest.put("mac", mac);

//         // Gửi yêu cầu tới ZaloPay để tạo đơn hàng
//         String result = sendRequest(createOrderUrl, orderRequest);

//         // Xử lý kết quả và trả về cho client
//         return result;
//     }

//     @GetMapping("/checkOrderStatus")
//     public String checkOrderStatus(@RequestParam("app_trans_id") String appTransId) throws IOException {
//         Map<String, String> request = new HashMap<>();
//         request.put("app_id", appId);
//         request.put("app_trans_id", appTransId);
//         String data = convertMapToJson(request);
//         String mac = HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, key1, data);
//         request.put("mac", mac);

//         // Gửi yêu cầu tới ZaloPay để kiểm tra trạng thái đơn hàng
//         String result = sendRequest(queryOrderUrl, request);

//         // Xử lý kết quả và trả về cho client
//         return result;
//     }

//     private String convertMapToJson(Map<String, String> data) throws IOException {
//         ObjectMapper objectMapper = new ObjectMapper();
//         return objectMapper.writeValueAsString(data);
//     }

//     private String sendRequest(String url, Object requestData) throws IOException {
//         CloseableHttpClient httpClient = HttpClients.createDefault();
//         HttpPost httpPost = new HttpPost(url);

//         httpPost.addHeader("Content-Type", "application/json");
//         httpPost.setEntity(new StringEntity(convertMapToJson(requestData)));

//         CloseableHttpResponse response = httpClient.execute(httpPost);
//         String result = EntityUtils.toString(response.getEntity());

//         return result;
//     }
// }
