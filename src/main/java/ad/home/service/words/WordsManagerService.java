package ad.home.service.words;

import ad.home.pojo.appentity.PageInfo;
import ad.home.pojo.dbentity.BlocklistDicEntity;
import ad.home.pojo.fixed.BlocklistDicFixedProperty;
import ad.home.pojo.fixed.WordsFixedProperty;
import ad.home.service.dic.BlocklistDicService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 黑词管理-必须单例
 */
@Scope("singleton")
@Slf4j
@Service("wordsManagerService")
public class WordsManagerService {
    // 内存管理黑词，将黑词加载到这个map中。分行业
    @Getter
    private Map<Integer, Map<String, String>> categoryWordMaps = new HashMap<>();

    @Autowired
    private BlocklistDicService blocklistDicService;

    /**
     * 加载所有的黑词到内存
     * @return
     */
    public Map<Integer, Integer> loadAllBlockWords() {
        // 查询所有行业
        Map<Integer, Integer> resMap = new HashMap<>();
        // 初始化各行业的Map应该在其他方法中完成
        Map<String, String> adCateMap = new HashMap<>();
        categoryWordMaps.put(1, adCateMap);
        for (int cate = 1; cate<=1; cate++) {
            // 分页加载
            PageInfo<BlocklistDicEntity> blocklistDicEntityPageInfo = new PageInfo<>();
            blocklistDicEntityPageInfo.setPageSize(new Long(WordsFixedProperty.DEFAULT_LOAD_PAGESIZE.getValue()));
            blocklistDicEntityPageInfo.setPageNo(1L);
            // 参数，要求是未被禁用的
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("categoryId", cate);
            parameter.put("dicStatus", BlocklistDicFixedProperty.STATUS_OK.getValue());
            blocklistDicEntityPageInfo = blocklistDicService.loadBlocklistDicForPage(blocklistDicEntityPageInfo, parameter);
            // 具有数据，分页查询
            int totleSize = 0;
            totleSize += addWordDataToMem(blocklistDicEntityPageInfo, cate);

            // 如果大于1页继续查询
            long pageCount = 0;
            if (blocklistDicEntityPageInfo.getTotal() % WordsFixedProperty.DEFAULT_LOAD_PAGESIZE.getValue() == 0) {
                pageCount = blocklistDicEntityPageInfo.getTotal() / WordsFixedProperty.DEFAULT_LOAD_PAGESIZE.getValue();
            } else {
                pageCount = (blocklistDicEntityPageInfo.getTotal() / WordsFixedProperty.DEFAULT_LOAD_PAGESIZE.getValue()) + 1;
            }
            if (pageCount > 1) {
                long beginIndex = blocklistDicEntityPageInfo.getCurrent() + 1;
                for (long pageIndex = beginIndex; pageIndex <= pageCount; pageIndex++) {
                    blocklistDicEntityPageInfo.setPageNo(pageIndex);
                    blocklistDicEntityPageInfo = blocklistDicService.loadBlocklistDicForPage(blocklistDicEntityPageInfo, parameter);
                    totleSize += addWordDataToMem(blocklistDicEntityPageInfo, cate);
                }
            }

            // 设置返回值
            resMap.put(cate, totleSize);
        }
        return resMap;
    }


    /**
     * 向内存添加数据
     * @param blocklistDicEntityPageInfo
     */
    private int addWordDataToMem(PageInfo<BlocklistDicEntity> blocklistDicEntityPageInfo, Integer cate) {
        int size = 0;
        if (blocklistDicEntityPageInfo.getRecords() != null && blocklistDicEntityPageInfo.getRecords().size() > 0) {
            size = blocklistDicEntityPageInfo.getRecords().size();

            String word = null;
            Map childMap = null;
            Map<String, String> newWordMap = null;
            for (BlocklistDicEntity w : blocklistDicEntityPageInfo.getRecords()) {
                //关键字
                word = w.getDicWords();
                childMap = categoryWordMaps.get(cate);
                //遍历该关键字
                for (int i = 0; i < word.length(); i++) {
                    char key = word.charAt(i);
                    Object wordMap = childMap.get(key);
                    if (wordMap != null) {
                        childMap = (Map) wordMap;
                    } else {
                        newWordMap = new HashMap<>();
                        newWordMap.put("isEnd", "0");
                        childMap.put(key, newWordMap);
                        //指向当前map,继续遍历
                        childMap = newWordMap;
                    }

                    if (i == word.length() - 1) {
                        //最后一个
                        childMap.put("isEnd", "1");
                    }
                }
            }
        }
        return size;
    }

}
