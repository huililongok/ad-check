package ad.home.controller.bg.dic;

import ad.home.common.vo.ResultInfo;
import ad.home.pojo.appentity.PageInfo;
import ad.home.pojo.dbentity.BlocklistDicEntity;
import ad.home.pojo.dbentity.UserEntity;
import ad.home.service.dic.BlocklistDicService;
import ad.home.web.solve.SessionResolve;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * 黑词处理
 */
@RestController
@RequestMapping(value = "/b/dic")
public class BlocklistDicController {

    @Autowired
    private BlocklistDicService blocklistDicService;

    /**
     * 后台操作
     * 用户系统内添加黑词
     */
    @RequestMapping(value = "/i/one", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResultInfo<BlocklistDicEntity> userAddBlocklistDic(BlocklistDicEntity blocklistDic) {
        ResultInfo<BlocklistDicEntity> result = null;
        if (blocklistDic != null && StringUtils.isNotBlank(blocklistDic.getDicWords())) {
            // 当前用户
            UserEntity user = SessionResolve.getInstance().getSecuritySessionUser();
            blocklistDic.setCreateUserId(user.getUserId());
            blocklistDicService.addBlocklistDic(blocklistDic);

            result = ResultInfo.getSuccessResult("添加成功", blocklistDic);
        } else {
            result = ResultInfo.getFailResult("参数错误");
        }
        return result;
    }

    //public ResultInfo<BlocklistDicEntity> userBatchAddBlocklistDic()

    /**
     * 删除 - 必须具有删除权限
     * @param idList
     * @return
     */
    @RequestMapping(value = "/d/more", method = {RequestMethod.DELETE, RequestMethod.POST})
    public ResultInfo<String> userDelBlocklistWords(List<Integer> idList) {
        ResultInfo<String> result = ResultInfo.getFailResult("参数错误");
        if (idList != null && idList.size() > 0) {
            int delCount = blocklistDicService.deleteBlocklistDicByIds(idList);
            result = ResultInfo.getSuccessResult("成功删除条数：" + delCount);
        }
        return result;
    }

    /**
     * 分页管理黑词
     * @param pageInfo
     * @param parameter
     * @return
     */
    @RequestMapping(value = "/r/page", method = {RequestMethod.GET})
    public ResultInfo<PageInfo<BlocklistDicEntity>> pageQueryBlocklistWords(PageInfo<BlocklistDicEntity> pageInfo, Map<String, Object> parameter) {
        pageInfo = blocklistDicService.loadBlocklistDicForPage(pageInfo, parameter);
        ResultInfo<PageInfo<BlocklistDicEntity>> result = ResultInfo.getSuccessResult("OK", pageInfo);
        return result;
    }

    /**
     * 用户修改黑词信息
     * @param blocklistDicEntity
     * @return
     */
    @RequestMapping(value = "/u/one", method = {RequestMethod.OPTIONS, RequestMethod.POST})
    public ResultInfo<BlocklistDicEntity> userUpdBlocklistWords(BlocklistDicEntity blocklistDicEntity) {
        ResultInfo<BlocklistDicEntity> result = ResultInfo.getFailResult("参数错误");
        if (blocklistDicEntity != null) {
            this.blocklistDicService.updateBlocklistDic(blocklistDicEntity);
            result.getSelfSuccessResult();
        }
        return result;
    }

}
