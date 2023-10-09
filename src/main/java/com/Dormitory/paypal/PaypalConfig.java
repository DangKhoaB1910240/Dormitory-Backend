// package com.Dormitory.paypal;

// import java.util.HashMap;
// import java.util.Map;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import com.paypal.base.rest.APIContext;
// import com.paypal.base.rest.OAuthTokenCredential;
// import com.paypal.base.rest.PayPalRESTException;
// @Configuration
// public class PaypalConfig {
//     @Value("${paypal.client-id}")
//     private String clientId;

//     @Value("${paypal.client-secret}")
//     private String clientSecret;
    
//     @Value("${paypal.mode}")
//     private String mode;

//     @Bean
//     public PayPalHttpClient payPalHttpClient() {
//         PayPalEnvironment  environment = new PayPalEnvironment.Sandbox(clientId, clientSecret);
//         return new PayPalHttpClient(environment);
//     }
// }
