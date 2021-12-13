package com.xh.wechat.company.exception;

/**
 * Create By IntelliJ IDEA
 *
 * @author: XieHua
 * @date: 2021-12-09 17:01
 */
public enum WechatCompanyErrorCode {
    UNKNOWN_ERROR(10000, "系统位置异常"),
    PARAM_VALID_ERROR(10001, "请求参数错误"),
    AGENT_IS_EMPTY(100002, "企业微信应用id为空"),
    AGENT_NOT_EXISTS(10003, "企业微信应用配置不存在"),
    ;
    private final int code;
    private final String message;

    WechatCompanyErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
