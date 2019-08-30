package ad.home.service.words;

import ad.home.pojo.fixed.WordsFixedProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 黑词过滤工具
 */
@Service("wordsFilterService")
public class WordsFilterService {

    @Autowired
    private WordsManagerService wordsManagerService;

    /**
     * 判断文本是否包含关键字
     * @param txt 待判断文本
     * @param matchType 匹配类型： 1 最小匹配原则；2 最大匹配原则
     * @return true 包含；false 不包含
     */
    public Boolean isContainsSensitiveWords(String txt, Integer matchType, Integer adcategory) {
        if(txt == null || "".equals(txt)){
            return false;
        }
        for(int i = 0; i < txt.length();i++){
            Integer matchFlag = checkSensitiveWords(txt, i , matchType, adcategory);
            if(matchFlag > 0){
                return true;
            }
        }
        return false;
    }

    /**
     * 校验是否包含敏感词
     * @param txt 待判断文本
     * @param start 起始位置
     * @param matchType 匹配类型： 1 最小匹配原则；2 最大匹配原则
     * @return 大于0表示包含敏感词且表示敏感词匹配长度，否则不包含
     */
    private Integer checkSensitiveWords(String txt, Integer start, Integer matchType, Integer adcategory) {
        //敏感词结束标志
        Boolean flag = false;
        char word;
        //匹配标识数默认为0
        Integer matchFlag = 0;
        Map childMap = wordsManagerService.getCategoryWordMaps().get(adcategory);
        for(int i = start;i < txt.length();i++){
            word = txt.charAt(i);
            childMap = (Map)childMap.get(word);
            if(childMap == null){
                //不存在该字打头的敏感词
                break;
            } else {
                //匹配标识+1
                matchFlag++;
                if("1".equals(childMap.get("isEnd"))){
                    flag = true;
                    if(WordsFixedProperty.DEFAULT_MATCH_TYPE.getValue().equals(matchType)){
                        //最小匹配规则则跳出，否则继续匹配
                        break;
                    }
                }
            }
        }
        if(matchFlag < 2 || !flag){
            //匹配长度需大于2才为词，并且敏感词已结束
            matchFlag = 0;
        }
        return matchFlag;
    }

    /**
     * 获取所有敏感词
     * @param txt 待判断文本
     * @param matchType 匹配类型： 1 最小匹配原则；2 最大匹配原则
     * @return
     */
    public Set<String> getSensitiveWords(String txt, Integer matchType, Integer adcategory){
        Set<String> sensitiveWords = new HashSet<>();

        for(int i = 0;i < txt.length();i++){
            Integer length = checkSensitiveWords(txt, i, matchType, adcategory);
            if(length > 0){
                sensitiveWords.add(txt.substring(i, i + length));
                //循环i会+1，所以需-1
                i = i + length - 1;
            }
        }
        return sensitiveWords;
    }

    /**
     * 替换敏感词
     * @param txt 文本
     * @param matchType 匹配类型： 1 最小匹配原则；2 最大匹配原则
     * @param replaceStr 替换字符
     * @return 处理后的文本
     */
    public String replaceSensitiveWords(String txt, Integer matchType, String replaceStr, Integer adcategory){
        if(txt == null || "".equals(txt)){
            return txt;
        }
        //获取所有敏感词
        Set<String> sensitiveWords = getSensitiveWords(txt, matchType, adcategory);
        Iterator<String> iterator = sensitiveWords.iterator();
        String replaceString = "";
        while (iterator.hasNext()){
            String sWord = iterator.next();
            replaceString = getReplaceString(replaceStr, sWord.length());
            txt = txt.replaceAll(sWord, replaceString);
        }
        return txt;
    }

    private String getReplaceString(String replaceStr, Integer length) {
        if(replaceStr == null){
            replaceStr = "*";
        }
        StringBuffer replaceString = new StringBuffer();
        for(int i = 0;i < length;i++){
            replaceString.append(replaceStr);
        }
        return replaceString.toString();
    }

}
