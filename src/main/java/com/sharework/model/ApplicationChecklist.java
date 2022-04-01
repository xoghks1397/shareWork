package com.sharework.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "application_checklist")
public class ApplicationChecklist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "application_id")
	@NotNull
	private long applicationId;

	@Column(name = "job_checklist_id")
	@NotNull
	private long jobChecklistId;

	@Builder
	public ApplicationChecklist(long applicationId, long jobChecklistId) {
		this.applicationId = applicationId;
		this.jobChecklistId = jobChecklistId;
	}
}
