package com.xh.wechat.company.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xh.wechat.company.domain.dto.ExternalUserDTO;
import com.xh.wechat.company.domain.entity.ExternalUser;

/**
 * <p>
 * 外部联系人表 服务类
 * </p>
 *
 * @author XieHua
 * @since 2021-12-06
 */
public interface IExternalUserService extends IService<ExternalUser> {
    /**
     * 保存或更新
     *
     * @param externalUserDTO 外部联系人对象
     * @return 是否操作成功
     */
    Boolean saveOrUpdate(ExternalUserDTO externalUserDTO);
}
