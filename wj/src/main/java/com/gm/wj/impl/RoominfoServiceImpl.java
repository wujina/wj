package com.gm.wj.impl;

import com.gm.wj.dao.RoominfoDao;
import com.gm.wj.pojo.Roominfo;
import com.gm.wj.service.RoominfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoominfoServiceImpl implements RoominfoService {

    @Autowired
    private RoominfoDao roominfoDao;

    @Override
    public void update(Roominfo roominfo) {





    }
}
