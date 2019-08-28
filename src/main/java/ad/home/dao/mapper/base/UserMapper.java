package ad.home.dao.mapper.base;

import ad.home.pojo.appentity.PageInfo;
import ad.home.pojo.dbentity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Transactional
@Repository("userMapper")
public interface UserMapper extends BaseMapper<UserEntity> {
    /**
     * 根据用户名查询用户信息
     * @param userName
     * @return
     */
    UserEntity queryUserByName(@Param("userName")String userName);

    /**
     *
     * getUserByConditionPage：根据条件进行分页查询
     *
     * @param page
     * @return
     *
     * @see <参见的内容>
     */
    PageInfo<UserEntity> getUserByConditionPage(PageInfo<UserEntity> page,
                                                @Param("params") Map<String, Object> params);

    /**
     *
     * 根据用户ID获取数据
     *
     * @param userId
     * @return
     *
     * @see <参见的内容>
     */
    UserEntity getUserbyUserId(Integer userId);

}
