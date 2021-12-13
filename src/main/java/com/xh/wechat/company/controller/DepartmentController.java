package com.xh.wechat.company.controller;

import com.xh.wechat.company.domain.dto.DepartmentDTO;
import com.xh.wechat.company.domain.result.R;
import com.xh.wechat.company.service.IDepartmentService;
import com.xh.wechat.company.service.IWxCpHelpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * @date: 2021-12-09 18:14
 */
@Api(value = "DepartmentController", tags = "部门相关接口")
@RestController
@RequestMapping(value = "/api/department")
public class DepartmentController {
    @Resource
    private IDepartmentService departmentService;

    @Resource
    private IWxCpHelpService wxCpHelpService;

    @ApiOperation(value = "同步数据", httpMethod = "GET")
    @GetMapping(value = "/sync", produces = MediaType.APPLICATION_JSON_VALUE)
    public R<Boolean> sync(@RequestParam Integer agentId) {
        List<DepartmentDTO> departmentDTOList = wxCpHelpService.departmentList(agentId, null);
        departmentDTOList.forEach(department -> departmentService.saveOrUpdate(department));

        return R.success(Boolean.TRUE);
    }
}
