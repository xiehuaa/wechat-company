package com.xh.wechat.company.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xh.wechat.company.domain.dto.UserExternalAttributeDTO;
import com.xh.wechat.company.domain.entity.UserExternalAttribute;

import java.util.List;

/**
 * <p>
 * 成员对外属性表 服务类
 * </p>
 *
 * @author XieHua
 * @since 2021-12-09
 */
public interface IUserExternalAttributeService extends IService<UserExternalAttribute> {
    /**
     * 修改成员的对外属性
     *
     * @param userId                   企业成员id
     * @param externalAttributeDTOList 对外属性列表
     * @return 是否操作成功
     */
    Boolean save(String userId, List<UserExternalAttributeDTO> externalAttributeDTOList);
}
