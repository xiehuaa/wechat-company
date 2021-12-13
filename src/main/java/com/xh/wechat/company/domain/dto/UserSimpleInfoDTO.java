package com.xh.wechat.company.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Create By IntelliJ IDEA
 *
 * @author: XieHua
 * @date: 2021-12-08 20:15
 */
@Setter
@Getter
public class UserSimpleInfoDTO implements Serializable {
    /**
     * 企业微信成员id
     */
    private String userid;
    /**
     * 企业微信成员名称
     */
    private String name;
    /**
     * 企业微信成员唯一标识
     */
    private String openUserid;
}
