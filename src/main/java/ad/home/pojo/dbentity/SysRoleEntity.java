package ad.home.pojo.dbentity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_sys_role")
public class SysRoleEntity implements Serializable {
    private Integer roleId;

    // 角色名称
    private String roleName;
    // 英文别名
    private String roleEnName;
    // 状态；1：正常；0：弃用
    private Integer roleStatus;
    // 描述
    private String roleDesc;
    // 创建人
    private Integer createUserId;
    // 条记录入表时间
    private Date createTime;
    // 这条记录最后修改时间
    private Date updateTime;
}
