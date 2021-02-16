package io.akash.accountaggregator.fipsimulator.model.entities;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document(collection="users")
@JsonIgnoreProperties(ignoreUnknown = true)
@TypeAlias("Customer")
public class Customer {
    @Field("_id")
    private String username;
    @Field("email")
    private String email;
    @Field("password")
    private String password;
    @Field("aadhar")
    private String aadhar;
    @Field("phone")
    private String phone;
    @Field("pan")
    private String pan;
    @Field("crn")
    private String crn;
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAadhar() {
		return aadhar;
	}
	public void setAadhar(String aadhar) {
		this.aadhar = aadhar;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public String getCrn() {
		return crn;
	}
	public void setCrn(String crn) {
		this.crn = crn;
	}
	@Override
	public String toString() {
		return "Customer [username=" + username + ", email=" + email + ", password=" + password + ", aadhar=" + aadhar
				+ ", phone=" + phone + ", pan=" + pan + ", crn=" + crn + "]";
	}
}
