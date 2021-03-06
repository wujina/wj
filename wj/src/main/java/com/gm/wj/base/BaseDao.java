package com.gm.wj.base;

import com.gm.wj.base.Model;

/**
 * 数据访问对象，假定实体编号类型为长整型
 * 
 * @author ecmybatis
 * @param <T> 实体对象类型
 */
public interface BaseDao<T extends Model<Long>> extends Dao<T, Long> {

}
