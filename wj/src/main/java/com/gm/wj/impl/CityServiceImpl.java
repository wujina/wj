package com.gm.wj.impl;

import com.gm.wj.dao.CityDao;
import com.gm.wj.pojo.City;
import com.gm.wj.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDao cityDao;

    @Override
    public void save(City city) {

        cityDao.saveOne(city);
    }
}
