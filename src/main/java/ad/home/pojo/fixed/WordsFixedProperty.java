package ad.home.pojo.fixed;

import lombok.Getter;

/**
 * 黑词处理相关固定属性
 */
public enum WordsFixedProperty {
    DEFAULT_LOAD_PAGESIZE("默认每页加载数量", 1000),
    DEFAULT_MATCH_TYPE("默认匹配规则", 1),
    MATCH_TYPE_MIN("最小匹配规则", 1),
    MATCH_TYPE_MAX("最大匹配规则", 2)
    ;

    @Getter
    private String name;
    @Getter
    private Integer value;

    WordsFixedProperty(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

}
