package com.gm.wj.pojo;

import java.util.List;

/**
 * @program: wj
 * @description:
 * @author: Mr.wuj
 * @create: 2019-11-21 17:31
 **/
public final class TextPojo<T>{

    private Class<T> type;
    private String name;
    public  String sex;
    public  void  qwe(){
        System.out.println("1ad");
    }

    public TextPojo(Class<T> type){
        this.type = type;
    }

    class Criterion {

        private String column;
        private String operator;

        private Object value;
        private Object secondValue;

        private boolean noValue;
        private boolean betweenValue;
        private boolean listValue;

        Criterion(String column, String operator) {
            this.column = column;
            this.operator = operator;
            this.noValue = true;
        }

        Criterion(String column, String operator, Object value) {
            this.column = column;
            this.operator = operator;
            this.value = value;

            if (value instanceof List<?>)
                this.listValue = true;
        }

        Criterion(String column, String operator, Object value, Object secondValue) {
            this.column = column;
            this.operator = operator;
            this.value = value;
            this.secondValue = secondValue;
            this.betweenValue = true;
        }

        public String getColumn() { return column; }

        public String getOperator() { return operator; }

        public String getCondition() { return getColumn() + getOperator(); }

        public Object getValue() { return value; }

        public Object getSecondValue() { return secondValue; }

        public boolean isNoValue() { return noValue; }

        public boolean isBetweenValue() { return betweenValue; }

        public boolean isListValue() { return listValue; }

    }
}
