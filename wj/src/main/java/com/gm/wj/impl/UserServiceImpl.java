package com.gm.wj.impl;
import com.gm.wj.dao.UserInfoDao;
import com.gm.wj.pojo.User;
import com.gm.wj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

import static com.gm.wj.utillity.MD5Util.validPasswd;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoDao userInfoDao;



    @Override
    public User get(String username, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        User user=userInfoDao.getUserInfo(username);

        if (validPasswd(password,user.getPassword())){
            user.setLast_updated_date(LocalDateTime.now());
            userInfoDao.update(user);
            return user;
        }
        return null;
    }

}
