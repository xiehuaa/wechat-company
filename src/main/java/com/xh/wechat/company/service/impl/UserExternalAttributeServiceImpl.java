package com.xh.wechat.company.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xh.wechat.company.domain.dto.UserExternalAttributeDTO;
import com.xh.wechat.company.domain.entity.UserExternalAttribute;
import com.xh.wechat.company.mapper.UserExternalAttributeMapper;
import com.xh.wechat.company.service.IUserExternalAttributeService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 成员对外属性表 服务实现类
 * </p>
 *
 * @author XieHua
 * @since 2021-12-09
 */
@Service
public class UserExternalAttributeServiceImpl extends ServiceImpl<UserExternalAttributeMapper, UserExternalAttribute> implements IUserExternalAttributeService {
    @Override
    public Boolean save(String userId, List<UserExternalAttributeDTO> externalAttributeDTOList) {
        UpdateWrapper<UserExternalAttribute> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq(UserExternalAttribute.USER_ID, userId).eq(UserExternalAttribute.IS_DELETE, 0);

        UserExternalAttribute userExternalAttribute = new UserExternalAttributeDTO();
        userExternalAttribute.setIsDelete(1);

        this.update(userExternalAttribute, updateWrapper);

        if (CollectionUtils.isEmpty(externalAttributeDTOList)) {
            return Boolean.TRUE;
        }

        for (UserExternalAttributeDTO externalAttributeDTO : externalAttributeDTOList) {
            UserExternalAttribute externalAttribute = new UserExternalAttributeDTO();
            BeanUtils.copyProperties(externalAttributeDTO, externalAttribute);
            externalAttribute.setUserId(userId);

            this.save(externalAttribute);
        }

        return Boolean.TRUE;
    }


}
