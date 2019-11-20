package com.gm.wj.dao;

import com.gm.wj.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoDao {
    /**
     * 根据姓名查询获得信息
     * @param name
     * @return
     */
    User getUserInfo(String name);

    /**
     * 修改用户信息
     * @param user
     */
    void update(User user);
}
