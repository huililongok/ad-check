package ad.home.pojo.dbentity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 黑词库
 */
@Data
@TableName("tb_blocklist_dic")
public class BlocklistDicEntity {
    private Integer dicId;
    // 黑词内容
    private String dicWords;
    // 黑词行业类型
    private Integer categoryId;
    // 黑词级别
    private Integer dicLevel;
    // 黑词状态；1：正常；0：弃用
    private Integer dicStatus;
    // 描述
    private String dicDesc;
    // 来源：0：外部导入；1：系统导入；2：系统添加；
    private Integer dicSource;
    // 创建人或者导入人
    private Integer createUserId;
    // 创建时间
    private Date createTime;
    // 这条记录最后修改时间
    private Date updateTime;


}
