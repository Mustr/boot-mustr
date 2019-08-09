package com.mustr.common.excel.component;

public class CellMerge {

    private int firstRow;//起始行
    private int lastRow;//结束行
    private int firstCol;//起始列
    private int lastCol;//结束列
    
    public CellMerge() {
        super();
    }

    public CellMerge(int firstRow, int lastRow, int firstCol, int lastCol) {
        super();
        this.firstRow = firstRow;
        this.lastRow = lastRow;
        this.firstCol = firstCol;
        this.lastCol = lastCol;
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
    
    public boolean validity() {
        return (firstRow < lastRow || firstCol < lastCol) && firstRow <= lastRow && firstCol <= lastCol;
    }
}
