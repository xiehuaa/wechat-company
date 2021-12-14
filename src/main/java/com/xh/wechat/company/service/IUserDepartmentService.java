package com.xh.wechat.company.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xh.wechat.company.domain.entity.UserDepartment;

import java.util.List;

/**
 * <p>
 * 成员部门表 服务类
 * </p>
 *
 * @author XieHua
 * @since 2021-12-06
 */
public interface IUserDepartmentService extends IService<UserDepartment> {
    /**
     * 重置员工的部门
     *
     * @param userId           企业微信成员id
     * @param departmentIdList 部门列表
     * @param mainDepartmentId 成员住部门id
     * @return 是否操作成功
     */
    Boolean resetUserDepartment(String userId, List<Long> departmentIdList, Long mainDepartmentId);
}
