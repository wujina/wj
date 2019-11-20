package com.gm.wj.dao;

import com.gm.wj.pojo.IdentityInformation;

/**
 * 
 * @author ecmybatis
 */
public interface IdentityInformationDao extends com.gm.wj.base.Dao<IdentityInformation, Long> {

    IdentityInformation select_id_card_num(String id_card_num);

    IdentityInformation select_phone(String phone);
  // You can type your methods here
  
}