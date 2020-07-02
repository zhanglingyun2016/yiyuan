package com.xgs.hisystem.pojo.bo;

import java.util.List;

/**
 * @author xgs
 * @date 2019/4/2
 * @description:
 */
public class PageRspBO<T> {

    private long total;

    private List<T> rows;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
