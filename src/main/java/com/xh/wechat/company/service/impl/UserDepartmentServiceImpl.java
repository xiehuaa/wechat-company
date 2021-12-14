package com.xh.wechat.company.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xh.wechat.company.domain.entity.UserDepartment;
import com.xh.wechat.company.enums.YesNoEnum;
import com.xh.wechat.company.exception.WechatCompanyErrorCode;
import com.xh.wechat.company.exception.WechatCompanyException;
import com.xh.wechat.company.mapper.UserDepartmentMapper;
import com.xh.wechat.company.service.IUserDepartmentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 成员部门表 服务实现类
 * </p>
 *
 * @author XieHua
 * @since 2021-12-06
 */
@Service
public class UserDepartmentServiceImpl extends ServiceImpl<UserDepartmentMapper, UserDepartment> implements IUserDepartmentService {

    @Override
    public Boolean resetUserDepartment(String userId, List<Long> departmentIdList, Long mainDepartmentId) {
        if (StringUtils.isBlank(userId)) {
            throw new WechatCompanyException(WechatCompanyErrorCode.PARAM_VALID_ERROR);
        }
        for (Long departmentId : departmentIdList) {
            UserDepartment dbUserDepartment = this.getByUserAndDepartment(userId, departmentId);

            UserDepartment userDepartment = new UserDepartment();
            userDepartment.setUserId(userId);
            userDepartment.setDepartmentId(departmentId);
            if (departmentId.equals(mainDepartmentId)) {
                userDepartment.setIsMain(YesNoEnum.YES.getCode());
            } else {
                userDepartment.setIsMain(YesNoEnum.NO.getCode());
            }

            if (dbUserDepartment == null) {
                userDepartment.setIsDelete(YesNoEnum.NO.getCode());
                userDepartment.setCreatedTime(new Date());

                this.save(userDepartment);
            } else {
                userDepartment.setId(dbUserDepartment.getId());

                this.updateById(userDepartment);
            }
        }
        this.removeDepartment(userId, departmentIdList);
        return Boolean.TRUE;
    }

    /**
     * 移除部门
     *
     * @param userId                企业微信成员id
     * @param currentDepartmentList 成员当前部门列表
     */
    private void removeDepartment(String userId, List<Long> currentDepartmentList) {
        UpdateWrapper<UserDepartment> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(UserDepartment.IS_DELETE, YesNoEnum.NO.getCode()).eq(UserDepartment.USER_ID, userId);
        if (!CollectionUtils.isEmpty(currentDepartmentList)) {
            updateWrapper.notIn(UserDepartment.DEPARTMENT_ID, currentDepartmentList);
        }

        UserDepartment userDepartment = new UserDepartment();
        userDepartment.setIsDelete(YesNoEnum.YES.getCode());

        this.update(userDepartment, updateWrapper);
    }

    /**
     * 通过成员id和部门id获取
     *
     * @param userId       成员id
     * @param departmentId 部门id
     * @return 成员部门对象
     */
    private UserDepartment getByUserAndDepartment(String userId, Long departmentId) {
        QueryWrapper<UserDepartment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(UserDepartment.USER_ID, userId).eq(UserDepartment.DEPARTMENT_ID, departmentId)
                .eq(UserDepartment.IS_DELETE, YesNoEnum.NO.getCode());
        List<UserDepartment> userDepartmentList = this.list(queryWrapper);

        return CollectionUtils.isEmpty(userDepartmentList) ? null : userDepartmentList.get(0);
    }
}
