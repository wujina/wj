package com.gm.wj.service;

import com.gm.wj.pojo.User;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public interface UserService {

    /**
     * 根据姓名获得用户信息
     * @param username 传入的名字
     * @param password
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    User get(String username, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException;


}
