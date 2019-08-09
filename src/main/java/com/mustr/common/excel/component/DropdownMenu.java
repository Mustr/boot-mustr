package com.mustr.common.excel.component;

import java.util.List;

public class DropdownMenu {

    private int firstRow;
    private int lastRow;
    private int firstCol;
    private int lastCol;
    private List<String> menuItems;

    public DropdownMenu() {
        super();
    }

    public DropdownMenu(int firstRow, int lastRow, int firstCol, int lastCol, List<String> menuItems) {
        super();
        this.firstRow = firstRow;
        this.lastRow = lastRow;
        this.firstCol = firstCol;
        this.lastCol = lastCol;
        this.menuItems = menuItems;
    }

    public int getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(int firstRow) {
        this.firstRow = firstRow;
    }

    public int getLastRow() {
        return lastRow;
    }

    public void setLastRow(int lastRow) {
        this.lastRow = lastRow;
    }

    public int getFirstCol() {
        return firstCol;
    }

    public void setFirstCol(int firstCol) {
        this.firstCol = firstCol;
    }

    public int getLastCol() {
        return lastCol;
    }

    public void setLastCol(int lastCol) {
        this.lastCol = lastCol;
    }

    public List<String> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<String> menuItems) {
        this.menuItems = menuItems;
    }
    
    public void setRange(int firstRow, int lastRow, int firstCol, int lastCol){
        this.firstRow = firstRow;
        this.lastRow = lastRow;
        this.firstCol = firstCol;
        this.lastCol = lastCol;
    }

}
