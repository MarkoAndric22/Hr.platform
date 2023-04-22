package com.hr.hrplatform.entities.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class CandidateDTO {
	
	@NotBlank(message = "Candidate name must not be blank")
	@Size(min = 2, max = 50, message = "Candidate name must be between {min} and {max} characters")
	private String name;

	@NotNull(message = "Date of birth must not be null")
	@Past(message = "Date of birth must be in the past")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date_of_birth;

	@NotNull(message = "Contact number must not be null")
	@Digits(integer = 10, fraction = 0, message = "Contact number must be 10 digits long")
	private Long contact_number;

	@NotBlank(message = "Email must not be blank")
	@Email(message = "Email must be valid")
	@Column(unique = true)
	private String email;

	public CandidateDTO() {
		super();
	}

	public CandidateDTO(
			@NotBlank(message = "Candidate name must not be blank") @Size(min = 2, max = 50, message = "Candidate name must be between {min} and {max} characters") String name,
			@NotNull(message = "Date of birth must not be null") @Past(message = "Date of birth must be in the past") Date date_of_birth,
			@NotNull(message = "Contact number must not be null") @Digits(integer = 10, fraction = 0, message = "Contact number must be 10 digits long") Long contact_number,
			@NotBlank(message = "Email must not be blank") @Email(message = "Email must be valid") String email) {
		super();
		this.name = name;
		this.date_of_birth = date_of_birth;
		this.contact_number = contact_number;
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate_of_birth() {
		return date_of_birth;
	}

	public void setDate_of_birth(Date date_of_birth) {
		this.date_of_birth = date_of_birth;
	}

	public Long getContact_number() {
		return contact_number;
	}

	public void setContact_number(Long contact_number) {
		this.contact_number = contact_number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "CandidateDTO [name=" + name + ", date_of_birth=" + date_of_birth + ", contact_number=" + contact_number
				+ ", email=" + email + "]";
	}
		

}
