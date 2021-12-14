package com.xh.wechat.company.domain.dto;

import com.xh.wechat.company.domain.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Create By IntelliJ IDEA
 *
 * @author: XieHua
 * @date: 2021-12-08 20:33
 */
@Setter
@Getter
public class UserDTO extends User {
    /**
     * 对外属性列表
     */
    private List<UserExternalAttributeDTO> externalAttributeList;

    /**
     * 部门列表
     */
    private List<Long> departmentIdList;

    /**
     * 主要部门id
     */
    private Long mainDepartmentId;
}
