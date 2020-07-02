package com.xgs.hisystem.pojo.entity;

import com.xgs.hisystem.util.DateUtil;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author xgs
 * @Description:
 * @date 2019/2/14
 */
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String id;

    @Column(name = "create_datetime", length = 20, nullable = false)
    private String createDatetime = DateUtil.getCurrentDateToString();// 创建时间 yyyy-MM-dd hh:mm:ss

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }

}