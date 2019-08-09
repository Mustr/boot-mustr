package com.mustr.common.excel.component;

import org.apache.poi.ss.usermodel.IndexedColors;

public class Font {

    /**
     * 字体
     */
    private String fontName;

    /**
     * 字体大小
     */
    private Short fontHeightInPoints;

    /**
     * 加粗
     */
    private Short Boldweight;

    /**
     * 字体颜色
     */
    private IndexedColors color;

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public Short getFontHeightInPoints() {
        return fontHeightInPoints;
    }

    public void setFontHeightInPoints(Short fontHeightInPoints) {
        this.fontHeightInPoints = fontHeightInPoints;
    }

    public Short getBoldweight() {
        return Boldweight;
    }

    public void setBoldweight(Short boldweight) {
        Boldweight = boldweight;
    }

    public IndexedColors getColor() {
        return color;
    }

    public void setColor(IndexedColors color) {
        this.color = color;
    }

}
