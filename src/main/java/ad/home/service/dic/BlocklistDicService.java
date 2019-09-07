package ad.home.service.dic;

import ad.home.dao.mapper.dic.BlocklistDicMapper;
import ad.home.pojo.appentity.PageInfo;
import ad.home.pojo.dbentity.BlocklistDicEntity;
import ad.home.pojo.fixed.BlocklistDicFixedProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("blocklistDicService")
public class BlocklistDicService {
    @Autowired
    private BlocklistDicMapper blocklistDicMapper;

    /**
     * 多条件分页查询数据
     * @param params
     * @return
     */
    public PageInfo<BlocklistDicEntity> loadBlocklistDicForPage(PageInfo<BlocklistDicEntity> pageInfo, Map<String, Object> params) {
        blocklistDicMapper.getDicByConditionPage(pageInfo, params);
        return pageInfo;
    }

    /**
     * 添加黑词
     * @param blocklistDicEntity
     */
    public String addBlocklistDic(BlocklistDicEntity blocklistDicEntity) {
        // 1.0 首先判断这个黑词是不是在系统已经存在了
        BlocklistDicEntity entity = blocklistDicMapper.queryBlocklistDicByWord(blocklistDicEntity.getDicWords());
        // 状态正常，类型相同的词不能重复
        if (entity != null && entity.getDicStatus().intValue() == BlocklistDicFixedProperty.STATUS_OK.getValue() && entity.getCategoryId().intValue() == blocklistDicEntity.getCategoryId().intValue()) {
            // 系统已经存在
            return "存在";
        } else {
            // 系统没有-添加
            Date now = new Date();
            blocklistDicEntity.setCreateTime(now);
            blocklistDicEntity.setDicSource(BlocklistDicFixedProperty.SOURCE_SYS_ITHIS.getValue());
            blocklistDicEntity.setDicStatus(BlocklistDicFixedProperty.STATUS_OK.getValue());
            blocklistDicEntity.setUpdateTime(now);
            this.blocklistDicMapper.insert(blocklistDicEntity);
            return null;
        }
    }

    /**
     * 修改黑词
     * @param blocklistDicEntity
     */
    public void updateBlocklistDic(BlocklistDicEntity blocklistDicEntity) {
        // 先把这个Id的黑词查询出来
        BlocklistDicEntity entity = blocklistDicMapper.selectById(blocklistDicEntity.getDicId());
        if (entity != null) {
            entity.setUpdateTime(new Date());
            entity.setDicStatus(blocklistDicEntity.getDicStatus());
            entity.setDicSource(blocklistDicEntity.getDicSource());
            entity.setCreateUserId(blocklistDicEntity.getCreateUserId());
            entity.setCategoryId(blocklistDicEntity.getCategoryId());
            entity.setDicDesc(blocklistDicEntity.getDicDesc());
            entity.setDicLevel(blocklistDicEntity.getDicLevel());
            entity.setDicWords(blocklistDicEntity.getDicWords());
            this.blocklistDicMapper.updateById(entity);
        }
    }

    /**
     * 根据ID删除多个黑词
     * @param dicIds
     * @return
     */
    public int deleteBlocklistDicByIds(List<Integer> dicIds) {
        if (dicIds != null && dicIds.size() > 0) {
            return this.blocklistDicMapper.deleteBatchIds(dicIds);
        }
        return 0;
    }


}
