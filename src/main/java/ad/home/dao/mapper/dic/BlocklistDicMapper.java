package ad.home.dao.mapper.dic;

import ad.home.pojo.dbentity.BlocklistDicEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 黑词管理
 */
@Transactional
@Repository("blocklistDicMapper")
public interface BlocklistDicMapper extends BaseMapper<BlocklistDicEntity> {

}
