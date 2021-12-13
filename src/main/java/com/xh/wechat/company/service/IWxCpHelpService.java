package com.xh.wechat.company.service;

import com.xh.wechat.company.domain.dto.DepartmentDTO;
import com.xh.wechat.company.domain.dto.ExternalUserDTO;
import com.xh.wechat.company.domain.dto.UserDTO;
import com.xh.wechat.company.domain.dto.UserSimpleInfoDTO;

import java.util.List;

/**
 * Create By IntelliJ IDEA
 *
 * @author: XieHua
 * @date: 2021-12-08 19:26
 */
public interface IWxCpHelpService {
    /**
     * 获取部门列表
     *
     * @param agentId      企业微信应用id
     * @param departmentId 部门id
     * @return 部门列表
     */
    List<DepartmentDTO> departmentList(Integer agentId, Long departmentId);

    /**
     * 获取企业成员基本信息列表
     *
     * @param agentId      企业微信应用id
     * @param departmentId 部门id
     * @param fetchChild   是否迭代
     * @return 成员列表
     */
    List<UserSimpleInfoDTO> userSimpleInfoList(Integer agentId, Long departmentId, boolean fetchChild);

    /**
     * 获取企业成员列表
     *
     * @param agentId      企业微信应用id
     * @param departmentId 部门id
     * @param fetchChild   是否迭代
     * @return 成员列表
     */
    List<UserDTO> userList(Integer agentId, Long departmentId, boolean fetchChild);

    /**
     * 获取企业成员添加的外部联系人id列表
     *
     * @param agentId 企业微信应用id
     * @param userId  企业微信成员id
     * @return 外部联系人id列表
     */
    List<String> externalUserIdList(Integer agentId, String userId);

    /**
     * 获取外部联系人详情
     *
     * @param agentId        企业微信应用id
     * @param externalUserId 外部联系人id
     * @return 外部联系人详情
     */
    ExternalUserDTO externalUserDetail(Integer agentId, String externalUserId);
}
