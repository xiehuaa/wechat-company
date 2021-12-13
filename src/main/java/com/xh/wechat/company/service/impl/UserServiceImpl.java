package com.xh.wechat.company.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xh.wechat.company.domain.dto.UserDTO;
import com.xh.wechat.company.domain.entity.User;
import com.xh.wechat.company.mapper.UserMapper;
import com.xh.wechat.company.service.IUserExternalAttributeService;
import com.xh.wechat.company.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 成员表 服务实现类
 * </p>
 *
 * @author XieHua
 * @since 2021-12-06
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    private IUserExternalAttributeService userExternalAttributeService;

    @Override
    public Boolean saveOrUpdate(UserDTO userDTO) {
        User dbUser = this.getByUserId(userDTO.getUserId());

        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        if (dbUser == null) {
            this.save(user);
        } else {
            user.setId(dbUser.getId());
            this.updateById(user);
        }
        userExternalAttributeService.save(userDTO.getUserId(), userDTO.getExternalAttributeList());
        return Boolean.TRUE;
    }

    /**
     * 通过企业微信成员id获取成员对象
     *
     * @param userId 企业微信成员id
     * @return 返回成员对象
     */
    private User getByUserId(String userId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(User.USER_ID, userId).eq(User.IS_DELETE, 0);
        List<User> userList = this.list(queryWrapper);

        return CollectionUtils.isEmpty(userList) ? null : userList.get(0);
    }
}
