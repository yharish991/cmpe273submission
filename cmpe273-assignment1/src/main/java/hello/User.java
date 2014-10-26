package hello;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;



public class User {
	private String user_id;
	//@JsonProperty("email")
	@NotBlank(message="Email should not be blank")
	private String email;
	@NotBlank(message="Password should not be blank")
	private String password;
	private String name;
	private String created_at;
	private String updated_at;
	private static int id=0;
	
	public User()
	{
	}
	
	public User(String email, String password,
			String created_at) {
		super();
		id++;
		this.email = email;
		this.password = password;
		this.created_at = created_at;
	}
	
	public static int getId() {
		return id;
	}

	public static void setId(int id) {
		User.id = id;
	}

	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	
		}
