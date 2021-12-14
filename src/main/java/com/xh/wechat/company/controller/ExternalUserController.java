package com.xh.wechat.company.controller;

import com.xh.wechat.company.domain.dto.ExternalUserDTO;
import com.xh.wechat.company.domain.result.R;
import com.xh.wechat.company.service.IExternalUserService;
import com.xh.wechat.company.service.IWxCpHelpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Create By IntelliJ IDEA
 *
 * @author: XieHua
 * @date: 2021-12-10 15:12
 */
@Api(value = "ExternalUserController", tags = "外部联系人相关接口")
@RestController
@RequestMapping(value = "/api/external-user")
public class ExternalUserController {
    @Resource
    private IWxCpHelpService wxCpHelpService;

    @Resource
    private IExternalUserService externalUserService;

    @ApiOperation(value = "同步外部联系人", httpMethod = "GET")
    @GetMapping(value = "/sync", produces = MediaType.APPLICATION_JSON_VALUE)
    public R<Boolean> sync(@ApiParam(value = "应用id", required = true) @RequestParam Integer agentId,
                           @ApiParam(value = "成员id") @RequestParam String userId,
                           @ApiParam(value = "外部联系人id") @RequestParam String externalUserId,
                           @ApiParam(value = "部门id") @RequestParam Long departmentId) {
        if (StringUtils.isNotBlank(externalUserId)) {
            ExternalUserDTO externalUserDTO = wxCpHelpService.externalUserDetail(agentId, externalUserId);
            externalUserService.saveOrUpdate(externalUserDTO);
        }
        return R.success(Boolean.TRUE);
    }
}
