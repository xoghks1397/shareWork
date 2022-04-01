package com.sharework.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.springframework.util.Assert;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user_checklist")
@NoArgsConstructor
public class UserChecklist {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(hidden = true)
	@Column(name = "id")
	private long id;

	@ApiModelProperty(value = "userId", example = "30")
	@NotNull
	@Column(name = "user_id")
	private long userId;

	@NotNull
	@Column(name = "contents")
	private String contents;

	@Builder
	public UserChecklist(long userId, String contents) {
		Assert.hasText(contents, "contents이 없습니다.");
		this.userId = userId;
		this.contents = contents;
	}
}
