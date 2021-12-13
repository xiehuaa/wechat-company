package com.xh.wechat.company.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xh.wechat.company.domain.dto.DepartmentDTO;
import com.xh.wechat.company.domain.entity.Department;
import com.xh.wechat.company.mapper.DepartmentMapper;
import com.xh.wechat.company.service.IDepartmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author XieHua
 * @since 2021-12-06
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {
    @Override
    public Boolean saveOrUpdate(DepartmentDTO departmentDTO) {
        if (departmentDTO == null || departmentDTO.getDepartmentId() == null) {
            throw new IllegalArgumentException("保存部门参数错误");
        }
        Department dbDepartment = this.getByDepartmentId(departmentDTO.getDepartmentId());

        Department department = new Department();
        BeanUtils.copyProperties(departmentDTO, department);
        if (dbDepartment == null) {
            department.setIsDelete(0);
            department.setCreatedTime(new Date());

            this.save(department);
        } else {
            department.setId(dbDepartment.getId());

            this.updateById(department);
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteByDepartmentId(Long departmentId) {
        UpdateWrapper<Department> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(Department.DEPARTMENT_ID, departmentId).eq(Department.IS_DELETE, 0);

        Department department = new Department();
        department.setIsDelete(1);
        return this.update(department, updateWrapper);
    }

    /**
     * 通过部门id获取部门
     *
     * @param departmentId 部门id
     * @return 部门对象
     */
    private Department getByDepartmentId(Long departmentId) {
        QueryWrapper<Department> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(Department.DEPARTMENT_ID, departmentId).eq(Department.IS_DELETE, 0);
        List<Department> departmentList = this.list(queryWrapper);

        return CollectionUtils.isEmpty(departmentList) ? null : departmentList.get(0);
    }
}
