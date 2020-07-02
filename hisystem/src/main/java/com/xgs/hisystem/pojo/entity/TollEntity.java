package com.xgs.hisystem.pojo.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author XueGuiSheng
 * @date 2020/4/10
 * @description: 收费记录表
 */
@Entity
@Table(name = "his_toll")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TollEntity extends BaseEntity {


}
