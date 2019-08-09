package com.mustr.common.excel.writer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.poi.ss.util.CellRangeAddress;

import com.mustr.common.excel.component.CellMerge;
import com.mustr.common.excel.component.Sheet;

/**
 * excel页面的数据处理类
 * @author mustr
 */
public class SheetHead {
    private com.mustr.common.excel.component.Sheet sheet;

    /**
     * 表头数据集合
     */
    private List<List<String>> head = new ArrayList<List<String>>();

    public SheetHead(Sheet sheet) {
        this.sheet = sheet;
        initHead();
    }

    private void initHead() {
        // 集合
        if (sheet.getHead() != null && !sheet.getHead().isEmpty()) {
            head.addAll(sheet.getHead());
        }
    }

    public List<CellRangeAddress> getCellRangeAddress() {
        List<CellRangeAddress> rangs = new ArrayList<CellRangeAddress>();
        if (sheet.isAutoCellMerge()) {
            for (int i = 0; i < head.size(); i++) {// 行
                List<String> rowValues = head.get(i);
                for (int j = 0; j < rowValues.size(); j++) {// 列
                    int lastRow = getLastRangRow(rowValues.get(j), i, j);
                    int lastColumn = getLastRangColumn(j, rowValues.get(j), rowValues, i);
                    if (lastRow >= 0 && lastColumn >= 0 && (lastRow > 0 || lastColumn > 0)) {
                        rangs.add(new CellRangeAddress(i, lastRow + i, j, lastColumn + j));
                    }
                }
            }
        }
        List<CellMerge> cellMerges = sheet.getCellMerges();
        if (isSet(cellMerges)) {
            for (CellMerge cellMerge : cellMerges) {
                if (cellMerge.validity()) {
                    rangs.add(new CellRangeAddress(cellMerge.getFirstRow(), cellMerge.getLastRow(), cellMerge
                        .getFirstCol(), cellMerge.getLastCol()));
                }
            }
        }
        return rangs;
    }

    private int getLastRangRow(String value, int rowNum, int colNum) {
        if (value == null || getRowNum() <= rowNum + 1) {
            return -1;
        }
        int nullNum = 0;
        return iteratorRow(nullNum, rowNum, colNum);
    }

    private int iteratorRow(int num, int rowNum, int colNum) {
        rowNum++;
        if (getRowNum() > rowNum) {
            List<String> rowData = getRowByRowIdx(rowNum);
            if (rowData.size() > colNum) {
                if (rowData.get(colNum) == null) {
                    num++;
                    return iteratorRow(num, rowNum, colNum);
                }
            }
            return 0;
        }
        return num;
    }

    private boolean isSet(Collection<?> value) {
        return value != null && !value.isEmpty();
    }

    private int getLastRangColumn(int j, String value, List<String> rowValues, int rowNum) {
        if (value == null) {
            return -1;
        }
        int nullNum = 0;
        if (rowValues.size() > j + 1) {
            return iteratorCol(nullNum, j, rowValues, rowNum);
        }
        return 0;
    }

    private int iteratorCol(int num, int j, List<String> rowValues, int rowNum) {
        j++;
        if (rowValues.size() > j) {
            String previous = null;
            if (rowNum > 0) {
                List<String> previouRow = getRowByRowIdx(rowNum - 1);
                if (previouRow.size() > j) {
                    previous = previouRow.get(j);
                }
            }
            if (rowValues.get(j) == null && previous == null) {
                num++;
                return iteratorCol(num, j, rowValues, rowNum);
            }
        }
        return num;
    }
    
    /**
     * 获取指定的表头行
     * 
     * @param rowIdx
     * @return
     */
    public List<String> getRowByRowIdx(int rowIdx) {
        if (head.size() > rowIdx) {
            return head.get(rowIdx);
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * 获取总的表头行
     * 
     * @return
     */
    public int getRowNum() {
        return head.size();
    }

    /**
     * 判断表头是否为空
     * 
     * @return
     */
    public boolean isEmpty() {
        return head.isEmpty();
    }

}
