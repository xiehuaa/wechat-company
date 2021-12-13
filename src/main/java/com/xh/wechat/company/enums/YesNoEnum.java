package com.xh.wechat.company.enums;

/**
 * Create By IntelliJ IDEA
 *
 * @author: XieHua
 * @date: 2021-12-09 17:46
 */
public enum YesNoEnum {
    YES(1, "是"),
    NO(0, "否");

    private final int code;
    private final String description;

    YesNoEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
