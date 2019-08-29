package ad.home.controller.dic;

import ad.home.common.vo.ResultInfo;
import ad.home.pojo.dbentity.BlocklistDicEntity;
import ad.home.pojo.dbentity.UserEntity;
import ad.home.service.dic.BlocklistDicService;
import ad.home.web.solve.SessionResolve;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 黑词处理
 */
@RestController
@RequestMapping(value = "/bdic")
public class BlocklistDicController {

    @Autowired
    private BlocklistDicService blocklistDicService;

    /**
     * 后台操作
     * 用户系统内添加黑词
     */
    @RequestMapping(value = "/a/one", method = {RequestMethod.PUT, RequestMethod.POST})
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

}
