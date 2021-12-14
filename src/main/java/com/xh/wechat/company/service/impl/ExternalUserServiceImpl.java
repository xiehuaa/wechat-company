package com.xh.wechat.company.service.impl;

import com.xh.wechat.company.domain.dto.ExternalUserDTO;
import com.xh.wechat.company.domain.entity.ExternalUser;
import com.xh.wechat.company.mapper.ExternalUserMapper;
import com.xh.wechat.company.service.IExternalUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 外部联系人表 服务实现类
 * </p>
 *
 * @author XieHua
 * @since 2021-12-06
 */
@Slf4j
@Service
public class ExternalUserServiceImpl extends ServiceImpl<ExternalUserMapper, ExternalUser> implements IExternalUserService {
    @Override
    public Boolean saveOrUpdate(ExternalUserDTO externalUserDTO) {
        return null;
    }
}
