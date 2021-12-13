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
 * 部门表
 * </p>
 *
 * @author XieHua
 * @since 2021-12-06
 */
@Getter
@Setter
@TableName("pe_department")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 企业微信部门id
     */
    @TableField("department_id")
    private Long departmentId;

    /**
     * 部门名称
     */
    @TableField("name")
    private String name;

    /**
     * 父部门id
     */
    @TableField("parent_id")
    private Long parentId;

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
     * 是否删除 0否 1是
     */
    @TableField("is_delete")
    private Integer isDelete;


    public static final String ID = "id";

    public static final String DEPARTMENT_ID = "department_id";

    public static final String NAME = "name";

    public static final String PARENT_ID = "parent_id";

    public static final String CREATED_TIME = "created_time";

    public static final String MODIFIED_TIME = "modified_time";

    public static final String IS_DELETE = "is_delete";

}
