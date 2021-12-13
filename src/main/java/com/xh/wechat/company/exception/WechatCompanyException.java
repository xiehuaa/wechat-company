package com.xh.wechat.company.exception;

/**
 * Create By IntelliJ IDEA
 *
 * @author: XieHua
 * @date: 2021-12-09 17:01
 */
public class WechatCompanyException extends RuntimeException {
    protected int code;

    public WechatCompanyException() {

    }

    public WechatCompanyException(WechatCompanyErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
