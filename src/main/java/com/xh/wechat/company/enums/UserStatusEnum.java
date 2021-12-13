package com.xh.wechat.company.enums;

/**
 * Create By IntelliJ IDEA
 *
 * @author: XieHua
 * @date: 2021-12-09 17:48
 */
public enum UserStatusEnum {
    UNKNOWN(0, "未知"),
    ACTIVATED(1, "已激活"),
    DISABLED(2, "已禁用"),
    INACTIVATED(4, "未激活"),
    LEFT(5, "已离开企业"),
    ;

    private final Integer code;
    private final String description;

    UserStatusEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
