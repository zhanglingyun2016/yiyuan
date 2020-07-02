package com.xgs.hisystem.pojo.vo.getAddress;

import com.xgs.hisystem.pojo.vo.getAddress.ContentVO;

import java.util.List;

/**
 * @author xgs
 * @Description:
 * @date 2019/3/23
 */
public class GetAddressRspVO {

    private String address;

    private ContentVO content;

    private int status;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ContentVO getContent() {
        return content;
    }

    public void setContent(ContentVO content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
