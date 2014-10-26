package hello;

import org.hibernate.validator.constraints.NotBlank;

public class WebLogin {
	
	private String login_id;
	@NotBlank(message="URL should not be blank")
	private String url;
	@NotBlank(message="Login should not be blank")
	private String login;
	@NotBlank(message="Password should not be blank")
	private String password;
	private static int id=0;
	
	public static int getId() {
		return id;
	}

	public static void setId(int id) {
		WebLogin.id = id;
	}

	public WebLogin(String url, String login, String password) {
		super();
		id++;
		this.url = url;
		this.login = login;
		this.password = password;
	}
	
	public WebLogin()	{		
	}
	
	public String getLogin_id() {
		return login_id;
	}
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	

}
