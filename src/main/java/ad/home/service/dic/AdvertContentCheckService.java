package ad.home.service.dic;

import ad.home.pojo.fixed.WordsFixedProperty;
import ad.home.service.words.WordsFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;


/**
 * 针对广告内容进行检查
 */
@Service("advertContentCheckService")
public class AdvertContentCheckService {
    @Autowired
    private WordsFilterService wordsFilterService;

    /**
     * 根据广告行业类型检查广告内容
     * @param adContent
     * @param adCategory
     * @return
     */
    public Set<String> wordsCheckAdvertContent(String adContent, Integer adCategory) {
        // 广告行业类型是词库的Key
        Set<String> set = wordsFilterService.getSensitiveWords(adContent, WordsFixedProperty.MATCH_TYPE_MAX.getValue(), adCategory);
        return set;
    }

}
