package com.xgs.hisystem.pojo.vo;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author xgs
 * @date 2019/4/11
 * @description:
 */
public class AnnouncementVO {

    private String id;

    @Length(max = 20, message = "标题不超过20个字符")
    @NotBlank(message = "标题不能为空")
    private String title;

    @Length(max = 500, message = "内容不超过500个字符")
    @NotBlank(message = "内容不能为空")
    private String contents;

    private Integer annStatus;

    private String annDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Integer getAnnStatus() {
        return annStatus;
    }

    public void setAnnStatus(Integer annStatus) {
        this.annStatus = annStatus;
    }

    public String getAnnDate() {
        return annDate;
    }

    public void setAnnDate(String annDate) {
        this.annDate = annDate;
    }
}
