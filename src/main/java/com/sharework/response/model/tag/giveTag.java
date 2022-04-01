package com.sharework.response.model.tag;

import com.sharework.base.model.BaseTagSub;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@ApiModel
@Getter
@Setter
@RequiredArgsConstructor
public class giveTag {
    public giveTag(long id, String category,List<BaseTagSub> tagList) {
        this.id = id;
        this.category = category;
        this.tagList = tagList;
    }

    private long id;
    private String category;
    private List<BaseTagSub> tagList;
}

