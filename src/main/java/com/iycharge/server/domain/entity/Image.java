package com.iycharge.server.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.iycharge.server.domain.entity.utils.serializer.ImageJsonSerializer;

import javax.persistence.*;

@Entity
@Table(name = "images")
public class Image extends BaseEntity {

    /*
     * 图片存储的相对路径
     */
    @JsonView(Summary.class)
    private String src;

    /*
     * 原始图片宽度
     */
    @JsonView(Summary.class)
    private Integer width;

    /*
     * 原始图片高度
     */
    @JsonView(Summary.class)
    private Integer height;

    /*
     * 排序顺序
     */
    @JsonIgnore
    private Integer position = 9999;

    public Image() {
    }

    public Image(String src) {
        this.src = src;
    }

    @JsonSerialize(using= ImageJsonSerializer.class)
    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
