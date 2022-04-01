package com.sharework.response.model.job;

import com.sharework.model.Job;
import com.sharework.model.JobTag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import com.sharework.response.model.user.userProfileImg;

import java.time.LocalDateTime;
import java.util.List;

@ApiModel
@Getter
@Setter
@NoArgsConstructor
public class JobClusterDetailJob {
    @ApiModelProperty(value = "id", position = 1)
    private long id;

    @ApiModelProperty(value = "title", position = 2)
    private String title;

    @ApiModelProperty(value = "starAt", position = 3)
    private LocalDateTime startAt;

    @ApiModelProperty(value = "endAt", position = 4)
    private LocalDateTime endAt;

    @ApiModelProperty(value = "payType", position = 5)
    private String payType;

    @ApiModelProperty(value = "pay", position = 6)
    private int pay;

    @ApiModelProperty(value = "contents", position = 7)
    private String contents;

    @ApiModelProperty(value = "distance", position = 8)
    private String distance;

    @ApiModelProperty(value = "user", position = 9)
    private userProfileImg user;

    @ApiModelProperty(value = "JobTags", position = 10)
    private List<JobTagContents> jobTags;

    @Builder
    public JobClusterDetailJob(long id, String title, LocalDateTime startAt, LocalDateTime endAt, String payType, int pay, String contents,
                               String distance, userProfileImg user, List<JobTagContents> jobTags) {
        this.id = id;
        this.title = title;
        this.startAt = startAt;
        this.endAt = endAt;
        this.payType = payType;
        this.pay = pay;
        this.contents = contents;
        this.distance = distance;
        this.user = user;
        this.jobTags = jobTags;
    }
}
