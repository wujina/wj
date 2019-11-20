package com.gm.wj.pojo;

import com.gm.wj.base.BaseModel;
import lombok.Getter;
import lombok.Setter;

/**
 * The IdentityInformation
 *
 * @author ecmybatis
 */
@Getter
@Setter
public class IdentityInformation extends BaseModel {

  private Integer ID; //
  private String NAME; //姓名
  private String SEX; //性别
  private String NATION; //民族
  private String BIRTHDAY; //出生年月
  private String ADDRESS; //地址
  private String ID_CARD_NUMBER; //身份证号码
  private Integer AGE; //年龄
  private String PHONE; //手机号
  private String ALIPAY; //支付宝
  private String WECHAT; //微信
  private String SCHOOL; //学校
  private String ADMISSION_DATE; //入学时间
  private String UPLOAD_DATE; //上传时间
  private String LAST_UPDATE; //更新时间
  private String PROVINCE_CITY; //省份城市
  private String LONGITUDE; //经度
  private String LATITUDE; //纬度

}
