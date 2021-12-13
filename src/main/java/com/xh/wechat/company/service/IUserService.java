package com.xh.wechat.company.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xh.wechat.company.domain.dto.UserDTO;
import com.xh.wechat.company.domain.entity.User;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 成员表 服务类
 * </p>
 *
 * @author XieHua
 * @since 2021-12-06
 */
public interface IUserService extends IService<User> {
    /**
     * 新增或更新成员
     *
     * @param userDTO 企业微信成员对象
     * @return 是否操作成功
     */
    Boolean saveOrUpdate(UserDTO userDTO);
}
