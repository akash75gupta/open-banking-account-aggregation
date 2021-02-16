package io.akash.accountaggregator.fipsimulator.model.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Document(collection="user")
@JsonIgnoreProperties(ignoreUnknown = true)
@TypeAlias("User")
public class User {
	@Id
	private String id;
	private String username;
	private String password;
	private String role;
	private String type;

	public User() {}
	
	public User(String username, String password, String role) {
	    this.username = username;
	    this.password = password;
	    this.role = role;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPassword(String password) { 
		this.password = password; 
	}

	public String getPassword() { 
		return password; 
	}

	public void setUsername(String username) { 
		this.username = username; 
	}

	public String getUsername() {
		  return username; 
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
