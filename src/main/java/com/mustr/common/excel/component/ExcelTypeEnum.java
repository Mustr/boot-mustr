package com.mustr.common.excel.component;

/**
 * excel类型
 * @author mustr
 */
public enum ExcelTypeEnum {
    XLS(".xls"), XLSX(".xlsx");
    
    private String value;

    private ExcelTypeEnum(String value) {
        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
