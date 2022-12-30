package com.example.crm;


import com.example.validation.FieldMatch;
import com.example.validation.ValidEmail;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;


@FieldMatch.List({
    @FieldMatch(first = "password", second = "matchingPassword", message = "The password fields must match")
})
@Validated
public class CrmUser {

	@NotNull(message = "username is required")
	@Size(min = 1, message = "username is required")
	private String userName;

	@NotNull(message = "password is required")
	@Size(min = 1, message = "password is required")
	private String password;
	
	@NotNull(message = "password is required")
	@Size(min = 1, message = "matching password is required")
	private String matchingPassword;

	@NotNull(message = "firstname is required")
	@Size(min = 1, message = "firstname is required")
	private String firstName;

	@NotNull(message = "lastname is required")
	@Size(min = 1, message = "lastname is required")
	private String lastName;

	@ValidEmail
	@NotNull(message = "email is required")
	@Size(min = 1, message = "email is required")
	private String email;
	
	
	@NotNull(message = "image is required")
	@Size(min = 1, message = "image is required")
	private String image;

	@NotNull(message = "city is required")
	@Size(min = 1, message = "city is required")
	private String city;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMatchingPassword() {
		return matchingPassword;
	}
	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
}
