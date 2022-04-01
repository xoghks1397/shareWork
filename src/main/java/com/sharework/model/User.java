package com.sharework.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.util.Assert;
import com.sun.istack.NotNull;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "users")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "email")
	@NotNull
	@ApiModelProperty(value = "email", position = 2, example = "sharework@sharework.com")
	private String email;

	@Column(name = "name")
	@NotNull
	@ApiModelProperty(value = "name", position = 3, example = "김쉐어")
	private String name;

	@Column(name = "phone_number")
	@NotNull
	@ApiModelProperty(value = "phone_number", position = 4, example = "01012345465")
	private String phoneNumber;

	@Column(name = "user_type")
	@ApiModelProperty(value = "user_type", position = 5, example = "worker || giver")
	private String userType;

	@Type(type = "jsonb")
	@NotNull
	@Column(name = "resident_number", columnDefinition = "jsonb")
	@ApiModelProperty(value = "resident_number", position = 6)
	private ResidentNumberJsonb residentNumber;

	@Column(name = "profile_img")
	@ApiModelProperty(value = "profile_img", position = 7, example = "1234.jpg || 1234.png || 1234.gif")
	private String profileImg;

	@Column(name = "jwt_token")
	@ApiModelProperty(value = "jwt", position = 8, example = "aaa.bbb.ccc")
	private String jwt;

	// 회원가입 시 무조건 들어오는 값
	@Column(name = "firebase_uid")
	@ApiModelProperty(value = "uid", position = 9, example = "uid")
	private String uid;

	@Builder
	public User(String email, String name, String phoneNumber, ResidentNumberJsonb residentNumber, String userType,
			String refreshToken) {
		Assert.hasText(email, "이메일이 없습니다.");
		Assert.hasText(name, "이름이 없습니다.");
		Assert.hasText(phoneNumber, "핸드폰 번호가 없습니다.");
		Assert.hasText(residentNumber.getFront(), "주민번호가 없습니다.");
		Assert.hasText(residentNumber.getRear(), "주민번호가 없습니다.");

		this.email = email;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.residentNumber = residentNumber;
		this.userType = userType;
		this.jwt = refreshToken;
	}
}
