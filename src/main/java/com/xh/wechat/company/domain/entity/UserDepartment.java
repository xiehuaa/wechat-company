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
 * 成员部门表
 * </p>
 *
 * @author XieHua
 * @since 2021-12-06
 */
@Getter
@Setter
@TableName("pr_user_department")
public class UserDepartment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 企业微信成员id
     */
    @TableField("user_id")
    private String userId;

    /**
     * 企业微信部门id
     */
    @TableField("department_id")
    private Long departmentId;

    /**
     * 是否为主要部门 0否 1是
     */
    @TableField("is_main")
    private Integer isMain;

    /**
     * 创建时间
     */
    @TableField("created_time")
    private Date createdTime;

    /**
     * 更新时间
     */
    @TableField("modified_time")
    private Date modifiedTime;

    /**
     * 是否删除 0否
     */
    @TableField("is_delete")
    private Integer isDelete;


    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String DEPARTMENT_ID = "department_id";

    public static final String IS_MAIN = "is_main";

    public static final String CREATED_TIME = "created_time";

    public static final String MODIFIED_TIME = "modified_time";

    public static final String IS_DELETE = "is_delete";

}
