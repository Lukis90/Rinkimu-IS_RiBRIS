package lt.javainiai.security;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Admin {

//	public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

	@Id
	@GeneratedValue
	private Long id;

	private String name;
	
	@JsonIgnore
	private String password;

	private String[] roles;

	protected Admin() {}

	public Admin(String name, String password, String... roles) {

		this.name = name;
//		this.setPassword(password);
		this.roles = roles;
	}

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}
	
	public String getPassword() {
		return password;
	}
	
//	public void setPassword(String password) {
//		this.password = PASSWORD_ENCODER.encode(password);
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}