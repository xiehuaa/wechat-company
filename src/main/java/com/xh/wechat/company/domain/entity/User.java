package com.xh.wechat.company.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 成员表
 * </p>
 *
 * @author XieHua
 * @since 2021-12-06
 */
@Getter
@Setter
@TableName("pe_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 成员id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 成员名称
     */
    @TableField("name")
    private String name;

    /**
     * 职位
     */
    @TableField("position")
    private String position;

    /**
     * 手机号
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 性别 0未知 1男 2女
     */
    @TableField("gender")
    private Integer gender;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 头像缩略图
     */
    @TableField("thumb_avatar")
    private String thumbAvatar;

    /**
     * 座机
     */
    @TableField("telephone")
    private String telephone;

    /**
     * 别名
     */
    @TableField("alias")
    private String alias;

    /**
     * 地址
     */
    @TableField("address")
    private String address;

    /**
     * 全局唯一   仅第三方应用可获取
     */
    @TableField("open_user_id")
    private String openUserId;

    /**
     * 状态 1已激活 2已禁用 4未激活 5退出企业
     */
    @TableField("status")
    private Integer status;

    /**
     * 二维码
     */
    @TableField("qr_code")
    private String qrCode;

    /**
     * 创建时间
     */
    @TableField("created_time")
    private Date createdTime;

    /**
     * 修改时间
     */
    @TableField("modified_time")
    private Date modifiedTime;

    /**
     * 是否删除 0否 1是
     */
    @TableField("is_delete")
    private Integer isDelete;


    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String NAME = "name";

    public static final String POSITION = "position";

    public static final String MOBILE = "mobile";

    public static final String GENDER = "gender";

    public static final String EMAIL = "email";

    public static final String AVATAR = "avatar";

    public static final String THUMB_AVATAR = "thumb_avatar";

    public static final String TELEPHONE = "telephone";

    public static final String ALIAS = "alias";

    public static final String ADDRESS = "address";

    public static final String OPEN_USER_ID = "open_user_id";

    public static final String STATUS = "status";

    public static final String QR_CODE = "qr_code";

    public static final String CREATED_TIME = "created_time";

    public static final String MODIFIED_TIME = "modified_time";

    public static final String IS_DELETE = "is_delete";

}
