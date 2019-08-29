package ad.home.dao.mapper.dic;

import ad.home.pojo.appentity.PageInfo;
import ad.home.pojo.dbentity.BlocklistDicEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * 黑词管理
 */
@Transactional
@Repository("blocklistDicMapper")
public interface BlocklistDicMapper extends BaseMapper<BlocklistDicEntity> {
    /**
     * 条件分页查询黑词数据
     * @param page
     * @param params
     * @return
     */
    PageInfo<BlocklistDicEntity> getDicByConditionPage(PageInfo<BlocklistDicEntity> page,
                                                @Param("params") Map<String, Object> params);

    /**
     * 根据黑词内容查询黑词信息
     * @param word
     * @return
     */
    BlocklistDicEntity queryBlocklistDicByWord(String word);


}
