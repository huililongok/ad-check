package ad.home.words;

import java.util.Set;

public class SensitiveWordsTest {
    public static void main(String[] args) {
        String txt = "当初实习面试问的一个问题就是检索一段话中是否包含敏感词，当初只能想到双重循环蛮力实现，" +
                "现在工作近三年突然回想起，还是完成了敏感词替换代码。虽然感觉很操蛋，但还是很牛逼，简单列举几个敏感词，" +
                "你妹的别给假钞！";
        Boolean containsSensitiveWords = SensitiveWordsFilter.isContainsSensitiveWords(txt, 1);
        System.out.println("Is contains sensitive word : " + containsSensitiveWords);
        Set<String> sensitiveWords = SensitiveWordsFilter.getSensitiveWords(txt, 1);
        System.out.println("All sensitive words ： " + sensitiveWords);


//        Long beginTime = System.currentTimeMillis();
//        System.out.println("After replace sensitive words : " + SensitiveWordsFilter.replaceSensitiveWords(txt, 1, null));
//        Long endTime = System.currentTimeMillis();
//        System.out.println("cost : " + (endTime - beginTime));
    }


}
