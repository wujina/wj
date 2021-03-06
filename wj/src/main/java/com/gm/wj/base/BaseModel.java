package com.gm.wj.base;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.mapping.ResultMapping;

import com.gm.wj.base.ResultMappings;

/**
 * 所有的实体对象继承此类，假定所有的实体对象编号的类型为长整型
 * 
 * @author ecmybatis
 */
public abstract class BaseModel implements Model<Long> {
  
  private Long id = -1L;

  // All properties mapping but id
  private List<ResultMappingValue> mappingValues = new ArrayList<>();
  
  public BaseModel() {
    List<ResultMapping> mappings = ResultMappings.getMappings(getClass());
      for (ResultMapping mapping : mappings) {
        if (!mapping.getProperty().equals("id")) {
          this.mappingValues.add(new ResultMappingValue(mapping));
        }
      }
  }

  List<ResultMappingValue> getValues() {
    try {
      for (ResultMappingValue mapping : mappingValues) {
        ResultMapping rm = mapping.getMapping();
        String property = rm.getProperty();
        Field field = FieldUtils.getField(getClass(), property, true);
        mapping.value(field.get(this));
      }
      return Collections.unmodifiableList(mappingValues);
    } catch (IllegalAccessException e) {
      throw new IllegalStateException("Cannot get field value of type " + getClass().getName());
    }
  }
  
  @Override
  public Long getId() {
    return this.id;
  }
  
  @Override
  public void setId(Long id) {
    this.id = id;
  }
  
  @Override
  public int hashCode() {
    return this.id.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    
    if (obj == null || this.getClass() != obj.getClass())
      return false;
    
    BaseModel that = (BaseModel) obj;
    
    return this.id.equals(that.id);
  }
  
  /**
   * 
   * @author ecmybatis
   */
  class ResultMappingValue {
    
    private ResultMapping mapping;
    private Object value;
    
    public ResultMappingValue(ResultMapping mapping) {
      this.mapping = mapping;
    }
    
    public String property() {
      return mapping.getProperty();
    }
    
    public String column() {
      return mapping.getColumn();
    }

    public Object getValue() {
      return value;
    }

    public void value(Object value) {
      this.value = value;
    }

    public ResultMapping getMapping() {
      return mapping;
    }
    
  }

}
