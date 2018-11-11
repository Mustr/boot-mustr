package com.mustr.common.utils;

import java.io.Serializable;
import java.util.List;

import com.github.pagehelper.Page;

public class Pagination<T> implements Serializable {
    private static final long serialVersionUID = 4042805817112356424L;

    private int code;
    private String msg;
    private long count;
    private int limit;
    private List<T> data;

    public Pagination() {
    }

    public Pagination(List<T> data) {
        this.data = data;
        this.code = 0;
    }

    public Pagination(List<T> data, int pageNum, int pageSize) {
        this.code = 0;
        this.limit = pageSize;
        if (data != null) {
            this.count = data.size();
        }
        if (pageSize > 0) {
            int startInx = (pageNum - 1) * pageSize;
            int endInx = startInx + pageSize;

            if (this.count > endInx) {
                this.data = data.subList(startInx, endInx);
            } else if (this.count > startInx) {
                this.data = data.subList(startInx, (int) this.count);
            }
        } else {
            this.data = data;
        }
    }
    
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public static <T> Pagination<T> builderPage(Page<T> page) {
        Pagination<T> res = new Pagination<T>(page);
        res.setCount(page.getTotal());
        res.setLimit(page.getPageSize());
        return res;
    }

    @Override
    public String toString() {
        return "Pagination [code=" + code + ", msg=" + msg + ", count=" + count + ", limit=" + limit + ", data=" + data
                + "]";
    }
}
