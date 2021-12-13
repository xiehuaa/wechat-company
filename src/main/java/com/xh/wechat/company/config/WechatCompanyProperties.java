package com.xh.wechat.company.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Create By IntelliJ IDEA
 *
 * @author: XieHua
 * @date: 2021-12-06 19:45
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "wechat.company")
public class WechatCompanyProperties {
    /**
     * 企业id
     */
    private String corpId;
    /**
     * 企业secret
     */
    private String secret;
    /**
     * 私钥
     */
    private String privateKey;

    /**
     * 应用配置
     */
    private AppProperties app;

    @Setter
    @Getter
    public static class AppProperties {
        /**
         * 应用id
         */
        private Integer agentId;
        /**
         * 应用secret
         */
        private String secret;
        /**
         * 应用token
         */
        private String token;
        /**
         * 应用aesKey
         */
        private String aesKey;
    }
}
