package com.xh.wechat.company.controller;

import com.xh.wechat.company.domain.dto.DepartmentDTO;
import com.xh.wechat.company.domain.dto.UserDTO;
import com.xh.wechat.company.domain.result.R;
import com.xh.wechat.company.service.IUserService;
import com.xh.wechat.company.service.IWxCpHelpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Create By IntelliJ IDEA
 *
 * @author: XieHua
 * @date: 2021-12-09 18:26
 */
@Api(value = "UserController", tags = "成员相关接口")
@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    @Resource
    private IWxCpHelpService wxCpHelpService;

    @Resource
    private IUserService userService;

    @ApiOperation(value = "同步成员", httpMethod = "GET")
    @GetMapping(value = "/sync", produces = MediaType.APPLICATION_JSON_VALUE)
    public R<Boolean> sync(@ApiParam("企业微信应用id") @RequestParam Integer agentId,
                           @ApiParam("企业微信部门id") @RequestParam(required = false) Long departmentId) {
        if (departmentId != null) {
            List<UserDTO> userDTOList = wxCpHelpService.userList(agentId, departmentId, true);
            userDTOList.forEach(userDTO -> userService.saveOrUpdate(userDTO));
        } else {
            List<DepartmentDTO> departmentList = wxCpHelpService.departmentList(agentId, null);
            departmentList.forEach(department -> {
                List<UserDTO> userDTOList = wxCpHelpService.userList(agentId, department.getDepartmentId(), false);
                userDTOList.forEach(userDTO -> userService.saveOrUpdate(userDTO));
            });
        }

        return R.success(Boolean.TRUE);
    }
}
