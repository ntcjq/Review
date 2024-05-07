package com.sea.review.controller;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECPublicKey;
import java.util.Map;

@RequestMapping("apple")
@RestController
@Slf4j
public class InnAppPayController {

    @RequestMapping("/notification")
    @ResponseBody
    public String notification(@RequestBody JSONObject object) {
        //请求值{"signedPayload":"eyJhbGciOiJFUzI1NiIsIng1YyI6WyJNSUlFTURDQ0E3YWdBd0l..."}
        /**
         * 解析后的值
         *         {
         *           "notificationType": "REFUND",
         *           "notificationUUID": "334d1548-****-4ea9-****-e104731870b9",
         *           "data": {
         *             "appAppleId": 1617026651,
         *             "bundleId": "com.*****",
         *             "bundleVersion": "1",
         *             "environment": "Sandbox",
         *             "signedTransactionInfo": "HeGxJRmR2Y214a2QybGtaU0JFWlhabGJHOXdaWElnVW1Wc1lYUnBi..."
         *           },
         *           "version": "2.0",
         *           "signedDate": 1680778196476
         *         }
         */
        log.info("InnAppPayController notification = {}", object.toJSONString());

        try {
            String signedPayload = object.getString("signedPayload");
            //苹果返回的值进行了两层加密首先获取解密第一层下data中的信息在进行解码
            DecodedJWT decode = JWT.decode(signedPayload);
            Map<String, Claim> map = decode.getClaims();
            Claim claim = map.get("data");
            Map<String, Object> asMap = claim.asMap();
            //获取到解码后的signedTransactionInfo信息
            String signedTransactionInfo = asMap.get("signedTransactionInfo").toString();
            //查询订阅状态
            String notificationType = JWT.decode(signedPayload).getClaim("notificationType").asString();
            //获取苹果原始交易ID
            String originalTransactionId = JWT.decode(signedTransactionInfo).getClaim("originalTransactionId").asString();
            log.info("InnAppPayController notificationType={}, transactionId = {}", notificationType, originalTransactionId);
            // 处理自动续订成功
            if ("DID_RENEW".equals(notificationType)) {
                // 业务逻辑
            }
        } catch (Exception e) {
            log.info("InnAppPayController notification msg = {}", e.getMessage());

        }
        return null;
    }


    /**
     * 验签
     *
     * @param jws
     * @return
     * @throws CertificateException
     */
    private void verify(String jws) throws CertificateException {
        DecodedJWT decodedJWT = JWT.decode(jws);
        // 拿到 header 中 x5c 数组中第一个
        String header = new String(java.util.Base64.getDecoder().decode(decodedJWT.getHeader()));
        String x5c = JSONObject.parseObject(header).getJSONArray("x5c").getString(0);
        // 获取公钥
        PublicKey publicKey = getPublicKeyByX5c(x5c);
        // 验证 token
        Algorithm algorithm = Algorithm.ECDSA256((ECPublicKey) publicKey, null);

        try {
            algorithm.verify(decodedJWT);
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取公钥
     *
     * @param x5c
     * @return
     * @throws CertificateException
     */
    private PublicKey getPublicKeyByX5c(String x5c) throws CertificateException {
        byte[] x5c0Bytes = java.util.Base64.getDecoder().decode(x5c);
        CertificateFactory fact = CertificateFactory.getInstance("X.509");
        X509Certificate cer = (X509Certificate) fact.generateCertificate(new ByteArrayInputStream(x5c0Bytes));
        return cer.getPublicKey();
    }
}