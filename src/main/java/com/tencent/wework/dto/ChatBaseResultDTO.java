package com.tencent.wework.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Create By IntelliJ IDEA
 *
 * @author: XieHua
 * @date: 2021-12-14 16:45
 */
@Setter
@Getter
public class ChatBaseResultDTO implements Serializable {
    private static final Integer SUCCESS_CODE = 0;

    @JSONField(name = "errcode")
    private Integer errCode;

    @JSONField(name = "errmsg")
    private String errMsg;

    public boolean isSuccess() {
        return Objects.equals(errCode, SUCCESS_CODE);
    }
}
