package ad.home.controller.dic;

import ad.home.common.vo.ResultInfo;
import ad.home.service.dic.AdvertContentCheckService;
import ad.home.service.words.WordsFilterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * 词语检查过滤
 */
@RestController
@RequestMapping(value = "/adword")
public class WordsCheckController {
    @Autowired
    private AdvertContentCheckService advertContentCheckService;


    /**
     * 根据行业类型检测广告内容的黑词
     * 这里同步过滤看看时间
     * @param adContent
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "/c", method = {RequestMethod.POST, RequestMethod.OPTIONS})
    public ResultInfo<Set<String>> checkWordsAdBlocklistDic(String adContent, Integer categoryId) {
        ResultInfo<Set<String>> result = ResultInfo.getFailResult("参数错误");
        if (StringUtils.isNotBlank(adContent) && categoryId != null && categoryId.intValue() > 0) {
            Set<String> set = advertContentCheckService.wordsCheckAdvertContent(adContent, categoryId);
            result.getSelfSuccessResult("OK", set);
        }
        return result;
    }


}
