package com.xgs.hisystem.pojo.vo.outpatient;

/**
 * @author xgs
 * @date 2019-5-12
 * @description:
 */
public class DrugRspVO {

    private String specification;  //规格：多少

    private double price;  //售价

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
