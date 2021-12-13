package com.xh.wechat.company.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xh.wechat.company.domain.dto.DepartmentDTO;
import com.xh.wechat.company.domain.entity.Department;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author XieHua
 * @since 2021-12-06
 */
public interface IDepartmentService extends IService<Department> {
    /**
     * 新增或修改
     *
     * @param departmentDTO 部门对象
     * @return 是否保存成功
     */
    Boolean saveOrUpdate(DepartmentDTO departmentDTO);

    /**
     * 通过部门id删除部门
     *
     * @param departmentId 企业微信部门id
     * @return 是否删除成功
     */
    Boolean deleteByDepartmentId(Long departmentId);
}
