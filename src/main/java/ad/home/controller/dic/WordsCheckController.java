package ad.home.controller.dic;

import ad.home.common.vo.ResultInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 词语检查过滤
 */
@RestController
@RequestMapping(value = "/adword")
public class WordsCheckController {
    /**
     * 根据行业类型检测广告内容的黑词
     * 这里同步过滤看看时间
     * @param adContent
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "/c", method = {RequestMethod.POST, RequestMethod.OPTIONS})
    public ResultInfo<Object> checkWordsAdBlocklistDic(String adContent, Integer categoryId) {
        ResultInfo<Object> result = ResultInfo.getFailResult("参数错误");

        return result;
    }


}
