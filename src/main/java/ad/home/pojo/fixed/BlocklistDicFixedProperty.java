package ad.home.pojo.fixed;

import lombok.Getter;

/**
 * 黑词相关固定属性值
 */
public enum BlocklistDicFixedProperty {
    STATUS_OK("状态正常", 1),
    STATUS_DIS("已禁用或删除", 0),
    SOURCE_OUT_ETHIS("外部直接导入数据库来源", 0),
    SOURCE_SYS_ETHIS("系统导入", 1),
    SOURCE_SYS_ITHIS("系统用户添加", 2)
    ;

    @Getter
    private String name;
    @Getter
    private Integer value;

    BlocklistDicFixedProperty(String name, Integer value) {
        this.name = name;
        this.value = value;
    }


}
