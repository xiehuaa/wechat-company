package com.xh.wechat.company.service.impl;

import com.alibaba.fastjson.JSON;
import com.xh.wechat.company.config.WechatCompanyConfig;
import com.xh.wechat.company.domain.dto.*;
import com.xh.wechat.company.exception.WechatCompanyErrorCode;
import com.xh.wechat.company.exception.WechatCompanyException;
import com.xh.wechat.company.service.IWxCpHelpService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpDepartmentService;
import me.chanjar.weixin.cp.api.WxCpExternalContactService;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.api.WxCpUserService;
import me.chanjar.weixin.cp.bean.WxCpDepart;
import me.chanjar.weixin.cp.bean.WxCpUser;
import me.chanjar.weixin.cp.bean.external.contact.ExternalContact;
import me.chanjar.weixin.cp.bean.external.contact.FollowedUser;
import me.chanjar.weixin.cp.bean.external.contact.WxCpExternalContactInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * Create By IntelliJ IDEA
 *
 * @author: XieHua
 * @date: 2021-12-08 19:26
 */
@Slf4j
@Service
public class WxCpHelpServiceImpl implements IWxCpHelpService {
    @Resource
    private WechatCompanyConfig wechatCompanyConfig;

    @Override
    public List<DepartmentDTO> departmentList(Integer agentId, Long departmentId) {
        WxCpDepartmentService departmentService = this.getByAgentId(agentId).getDepartmentService();
        try {
            List<DepartmentDTO> departmentDTOList = new ArrayList<>();
            List<WxCpDepart> wxCpDepartList = departmentService.list(departmentId);
            if (CollectionUtils.isEmpty(wxCpDepartList)) {
                return departmentDTOList;
            }

            for (WxCpDepart wxCpDepart : wxCpDepartList) {
                DepartmentDTO departmentDTO = new DepartmentDTO();
                departmentDTO.setDepartmentId(wxCpDepart.getId());
                departmentDTO.setParentId(wxCpDepart.getParentId());
                departmentDTO.setName(wxCpDepart.getName());

                departmentDTOList.add(departmentDTO);
            }
            return departmentDTOList;
        } catch (WxErrorException e) {
            log.error("获取部门列表失败：", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<UserSimpleInfoDTO> userSimpleInfoList(Integer agentId, Long departmentId, boolean fetchChild) {
        WxCpUserService userService = this.getByAgentId(agentId).getUserService();
        try {
            List<WxCpUser> wxCpUsers = userService.listSimpleByDepartment(departmentId, fetchChild, 0);
            if (CollectionUtils.isEmpty(wxCpUsers)) {
                return Collections.emptyList();
            }
            List<UserSimpleInfoDTO> result = new ArrayList<>();
            for (WxCpUser wxCpUser : wxCpUsers) {
                UserSimpleInfoDTO userSimpleInfoDTO = new UserSimpleInfoDTO();
                userSimpleInfoDTO.setName(wxCpUser.getName());
                userSimpleInfoDTO.setOpenUserid(wxCpUser.getOpenUserId());
                userSimpleInfoDTO.setUserid(wxCpUser.getUserId());

                result.add(userSimpleInfoDTO);
            }
            return result;
        } catch (WxErrorException e) {
            log.error("获取成员基本信息列表失败：", e);
            return Collections.emptyList();
        }
    }

    @Override
    public List<String> externalUserIdList(Integer agentId, String userId) {
        WxCpExternalContactService externalContactService = this.getByAgentId(agentId).getExternalContactService();
        try {
            return externalContactService.listExternalContacts(userId);
        } catch (WxErrorException e) {
            log.error("获取外部联系人id列表失败：", e);
        }
        return Collections.emptyList();
    }

    @Override
    public List<UserDTO> userList(Integer agentId, Long departmentId, boolean fetchChild) {
        WxCpUserService userService = this.getByAgentId(agentId).getUserService();
        List<UserDTO> userDTOList = new ArrayList<>();
        try {
            List<WxCpUser> wxCpUsers = userService.listByDepartment(departmentId, fetchChild, 0);
            for (WxCpUser wxCpUser : wxCpUsers) {
                UserDTO userDTO = new UserDTO();
                BeanUtils.copyProperties(wxCpUser, userDTO);
                userDTO.setDepartmentIdList(Arrays.asList(wxCpUser.getDepartIds()));
                long mainDepartmentId = 0L;
                if (StringUtils.isNotBlank(wxCpUser.getMainDepartment())) {
                    mainDepartmentId = Long.parseLong(wxCpUser.getMainDepartment());
                }
                userDTO.setMainDepartmentId(mainDepartmentId);
                userDTO.setGender(Integer.parseInt(wxCpUser.getGender().getCode()));

                List<UserExternalAttributeDTO> externalAttributeList = new ArrayList<>();
                List<WxCpUser.ExternalAttribute> externalAttrs = wxCpUser.getExternalAttrs();
                if (!CollectionUtils.isEmpty(externalAttrs)) {
                    for (WxCpUser.ExternalAttribute externalAttribute : externalAttrs) {
                        UserExternalAttributeDTO externalAttributeDTO = new UserExternalAttributeDTO();
                        BeanUtils.copyProperties(externalAttribute, externalAttributeDTO);

                        externalAttributeList.add(externalAttributeDTO);
                    }
                }
                userDTO.setExternalAttributeList(externalAttributeList);

                userDTOList.add(userDTO);
            }
        } catch (WxErrorException e) {
            log.error("获取成员信息列表失败：", e);
            return Collections.emptyList();
        }
        return userDTOList;
    }

    @Override
    public ExternalUserDTO externalUserDetail(Integer agentId, String externalUserId) {
        if (StringUtils.isBlank(externalUserId)) {
            return null;
        }
        WxCpExternalContactService externalContactService = this.getByAgentId(agentId).getExternalContactService();
        try {
            WxCpExternalContactInfo externalContact = externalContactService.getContactDetail(externalUserId);
            return this.convertToExternalUserDTO(externalContact);
        } catch (WxErrorException e) {
            log.error("获取外部联系人详情失败：", e);
        }
        return null;
    }

    private ExternalUserDTO convertToExternalUserDTO(WxCpExternalContactInfo externalContactInfo) {
        if (externalContactInfo == null) {
            return null;
        }
        ExternalUserDTO externalUserDTO = new ExternalUserDTO();

        ExternalContact externalContact = externalContactInfo.getExternalContact();

        externalUserDTO.setName(externalContact.getName());
        externalUserDTO.setAvatar(externalContact.getAvatar());
        externalUserDTO.setGender(externalContact.getGender());
        externalUserDTO.setExternalUserId(externalContact.getExternalUserId());
        externalUserDTO.setType(externalContact.getType());
        externalUserDTO.setUnionId(externalContact.getUnionId());

        List<ExternalUserFollowDTO> followDTOList = new ArrayList<>();
        List<FollowedUser> followedUsers = externalContactInfo.getFollowedUsers();
        if (!CollectionUtils.isEmpty(followedUsers)) {
            for (FollowedUser followedUser : followedUsers) {
                ExternalUserFollowDTO followDTO = new ExternalUserFollowDTO();
                followDTO.setUserId(followedUser.getUserId());
                followDTO.setExternalUserId(externalContact.getExternalUserId());
                if (StringUtils.isNotBlank(followedUser.getAddWay())) {
                    followDTO.setAddWay(Integer.parseInt(followedUser.getAddWay()));
                }
                followDTO.setAddTime(new Date(followedUser.getCreateTime() * 1000));
                followDTO.setRemark(followedUser.getRemark());
                followDTO.setDescription(followedUser.getDescription());
                followDTO.setRemarkCorpName(followedUser.getRemarkCorpName());
                followDTO.setRemarkMobiles(JSON.toJSONString(followedUser.getRemarkMobiles()));
                followDTO.setState(followedUser.getState());
                followDTO.setOperUserId(followedUser.getOperatorUserId());
                // followDTO.setTag(JSON.toJSONString(followedUser.getTags()));

                followDTOList.add(followDTO);
            }
        }
        externalUserDTO.setFollowList(followDTOList);

        return externalUserDTO;
    }

    /**
     * 通过企业微信应用id获取WxCpService对象
     *
     * @param agentId
     * @return
     */
    private WxCpService getByAgentId(Integer agentId) {
        if (agentId == null) {
            throw new WechatCompanyException(WechatCompanyErrorCode.AGENT_IS_EMPTY);
        }
        WxCpService wxCpService = wechatCompanyConfig.getWxCpService(agentId);
        if (wxCpService == null) {
            throw new WechatCompanyException(WechatCompanyErrorCode.AGENT_NOT_EXISTS);
        }
        return wxCpService;
    }
}
