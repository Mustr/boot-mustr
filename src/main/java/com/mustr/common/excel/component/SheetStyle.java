package com.mustr.common.excel.component;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;


public class SheetStyle {

    /**
     * 表头的背景
     */
    private IndexedColors headBGColor;


    /**
     * 表头的字体
     */
    private Font headFont;

    /**
     * 内容的背景
     */
    private IndexedColors contentBGColor;
    
    /**
     * 内容的字体
     */
    private Font contentFont;

    /**
     * 第一行表头的背景
     */
    private IndexedColors firstHeadBGColor;
    
    /**
     * 第一行标有的字体
     */
    private Font firstHeadFont;
    
    /**
     * 第一行的对齐方式
     */
    private HorizontalAlignment firstHeadAlignment;
    
    /**
     * 第一行的行高
     */
    private Float firstHeadRowHeight;
    
    /**
     * 表体单元格格式是否默认为文本类型
     */
    private boolean bodyText;
    
    public IndexedColors getHeadBGColor() {
        return headBGColor;
    }

    public void setHeadBGColor(IndexedColors headBGColor) {
        this.headBGColor = headBGColor;
    }

    public Font getHeadFont() {
        return headFont;
    }

    public void setHeadFont(Font headFont) {
        this.headFont = headFont;
    }

    public Font getContentFont() {
        return contentFont;
    }

    public void setContentFont(Font contentFont) {
        this.contentFont = contentFont;
    }

    public IndexedColors getContentBGColor() {
        return contentBGColor;
    }

    public void setContentBGColor(IndexedColors contentBGColor) {
        this.contentBGColor = contentBGColor;
    }
    
    public boolean isBodyText() {
        return bodyText;
    }

    public void setBodyText(boolean bodyText) {
        this.bodyText = bodyText;
    }

    public IndexedColors getFirstHeadBGColor() {
        return firstHeadBGColor;
    }

    public void setFirstHeadBGColor(IndexedColors firstHeadBGColor) {
        this.firstHeadBGColor = firstHeadBGColor;
    }

    public Font getFirstHeadFont() {
        return firstHeadFont;
    }

    public void setFirstHeadFont(Font firstHeadFont) {
        this.firstHeadFont = firstHeadFont;
    }

    public HorizontalAlignment getFirstHeadAlignment() {
        return firstHeadAlignment;
    }

    public void setFirstHeadAlignment(HorizontalAlignment firstHeadAlignment) {
        this.firstHeadAlignment = firstHeadAlignment;
    }
    
    public Float getFirstHeadRowHeight() {
        return firstHeadRowHeight;
    }

    public void setFirstHeadRowHeight(Float firstHeadRowHeight) {
        this.firstHeadRowHeight = firstHeadRowHeight;
    }

}
