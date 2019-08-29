package ad.home.service.dic;

import org.springframework.stereotype.Service;


/**
 * 针对广告内容进行检查
 */
@Service("advertContentCheckService")
public class AdvertContentCheckService {
    /**
     * 根据广告行业类型检查广告内容
     * @param adContent
     * @param adCategory
     * @return
     */
    public Object wordsCheckAdvertContent(String adContent, Integer adCategory) {
        // 广告行业类型是词库的Key

        return null;
    }

}
