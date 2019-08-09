package com.mustr.common.excel.component;

import java.util.List;

public class Sheet {

    /**
     * 页的编号
     */
    private int sheetNo;

    /**
     * 页名
     */
    private String sheetName;

    /**
     * 表头集合
     */
    private List<List<String>> head;
    
    /**
     * 表头是否自动合并单元格（为null的就合并了）
     */
    private boolean autoCellMerge = true;

    
    /**
     * 是否包含案例，包含的话，除了第一行 。其他行第一列都会为null
     */
    private boolean hasExample;
    
    private SheetStyle style;
    
    /**
     * 自定义合并单元格
     */
    List<CellMerge> cellMerges;
    
    List<DropdownMenu> dropdownMenus;
    
    public Sheet(int sheetNo) {
        this.sheetNo = sheetNo;
    }

    public Sheet(int sheetNo, String sheetName) {
        this.sheetNo = sheetNo;
        this.sheetName = sheetName;
    }

    public int getSheetNo() {
        return sheetNo;
    }

    public void setSheetNo(int sheetNo) {
        this.sheetNo = sheetNo;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<List<String>> getHead() {
        return head;
    }

    public void setHead(List<List<String>> head) {
        this.head = head;
    }

    public SheetStyle getStyle() {
        return style;
    }

    public void setStyle(SheetStyle style) {
        this.style = style;
    }

    public boolean isHasExample() {
        return hasExample;
    }

    public void setHasExample(boolean hasExample) {
        this.hasExample = hasExample;
    }
    
    public List<CellMerge> getCellMerges() {
        return cellMerges;
    }

    public void setCellMerges(List<CellMerge> cellMerges) {
        this.cellMerges = cellMerges;
    }

    public boolean isAutoCellMerge() {
        return autoCellMerge;
    }

    public void setAutoCellMerge(boolean autoCellMerge) {
        this.autoCellMerge = autoCellMerge;
    }

    public List<DropdownMenu> getDropdownMenus() {
        return dropdownMenus;
    }

    public void setDropdownMenus(List<DropdownMenu> dropdownMenus) {
        this.dropdownMenus = dropdownMenus;
    }

    @Override
    public String toString() {
        return "Sheet [sheetNo=" + sheetNo + ", sheetName=" + sheetName + "]";
    }
}
