package com.xgs.hisystem.pojo.vo;

/**
 * @author xgs
 * @date 2019/4/3
 * @description:
 */
public class BasePageReqVO {

    private int pageSize;

    private int pageNumber;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
