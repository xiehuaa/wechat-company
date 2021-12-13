package com.xh.wechat.company.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Create By IntelliJ IDEA
 *
 * @author: XieHua
 * @date: 2021-12-09 16:55
 */
@Slf4j
public class RSAUtil {
    /**
     * 使用公钥进行RSA加密
     *
     * @param content   需要加密的内容
     * @param publicKey 公钥
     * @return 加密后的内容
     */
    public static String encrypt(String content, String publicKey) throws Exception {
        // base64编码的公钥
        byte[] decoded = Base64.decodeBase64(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        // RAS加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return Base64.encodeBase64String(cipher.doFinal(content.getBytes("UTF-8")));
    }

    /**
     * 使用私钥进行RSA解密
     *
     * @param content    需要解密的内容
     * @param privateKey 私钥
     * @return 解密后的内容
     */
    public static String decrypt(String content, String privateKey) throws Exception {
        if (StringUtils.isBlank(content)) {
            return "";
        }
        // Base64解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(content.getBytes("UTF-8"));
        // Base64编码的私钥
        byte[] decoded = Base64.decodeBase64(privateKey);
        PrivateKey priKey = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        // RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        return new String(cipher.doFinal(inputByte));
    }
}
