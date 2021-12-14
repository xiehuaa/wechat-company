package com.xh.wechat.company.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Create By IntelliJ IDEA
 *
 * @author: XieHua
 * @date: 2021-12-14 15:27
 */
@Setter
@Getter
public class UserDepartmentDTO implements Serializable {
    /**
     * 企业微信成员id
     */
    private String userId;
    /**
     * 企业微信部门id
     */
    private Long departmentId;
    /**
     * 是否为主部门
     */
    private Integer isMain;
}
