package com.xgs.hisystem.pojo.vo.getAddress;

import java.util.List;

/**
 * @author xgs
 * @Description:
 * @date 2019/3/23
 */
public class ContentVO {

    private String address;

    private AddressDetailVO address_detail;

    private PointVO point;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public AddressDetailVO getAddress_detail() {
        return address_detail;
    }

    public void setAddress_detail(AddressDetailVO address_detail) {
        this.address_detail = address_detail;
    }

    public PointVO getPoint() {
        return point;
    }

    public void setPoint(PointVO point) {
        this.point = point;
    }
}
