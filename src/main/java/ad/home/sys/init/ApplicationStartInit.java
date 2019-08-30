package ad.home.sys.init;

import ad.home.service.words.WordsManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;

/**
 * 系统启动执行
 * 初始化执行
 */
@Slf4j
@Component
public class ApplicationStartInit implements ApplicationRunner {
    @Autowired
    private WordsManagerService wordsManagerService;

    /**
     * 系统启动加载未被禁用的黑词数据
     * @param args
     */
    public void run(ApplicationArguments args) {
        // 1.0 加载未被禁用的黑词数据
        loadBlockWords();
    }

    /**
     * 加载黑词
     */
    private void loadBlockWords() {
        long beginTime = System.currentTimeMillis();
        Map<Integer, Integer> resMap = wordsManagerService.loadAllBlockWords();
        Iterator<Integer> iterator = resMap.keySet().iterator();
        StringBuilder subLog = new StringBuilder();
        while (iterator.hasNext()) {
            Integer cate = iterator.next();
            Integer size = resMap.get(cate);
            subLog.append("|类型：").append(cate).append("，加载数量：").append(size);
        }
        long endtime = System.currentTimeMillis();
        log.info("【ApplicationStartInit】系统初始化加载到黑词情况：{}，耗时：{}毫秒。", subLog.toString(), (endtime - beginTime));
    }

}
