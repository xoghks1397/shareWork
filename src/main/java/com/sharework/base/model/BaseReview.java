package com.sharework.base.model;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "base_review")
public class BaseReview {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "contents")
	private String contents;

	@Column(name = "user_type")
	private String userType;

	@Builder
	public BaseReview(String contents, String userType) {
		this.contents = contents;
		this.userType = userType;
	}
}
