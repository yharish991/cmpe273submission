package hello;

import org.hibernate.validator.constraints.NotBlank;

public class BankAccount {
	
	private String ba_id;
	private String account_name;
	@NotBlank(message="Account number should not be blank")
	private String account_number;
	@NotBlank(message="Routing number should not be blank")
	private String routing_number;
	private static int id=0;
	
	public static int getId() {
		return id;
	}

	public static void setId(int id) {
		BankAccount.id = id;
	}

	private BankAccount(){}
	
	public BankAccount(String account_name,
			String account_number, String routing_number) {
		super();
		id++;
		this.account_name = account_name;
		this.account_number = account_number;
		this.routing_number = routing_number;
	}
		
	public String getBa_id() {
		return ba_id;
	}
	public void setBa_id(String ba_id) {
		this.ba_id = ba_id;
	}
	public String getAccount_name() {
		return account_name;
	}
	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}
	public String getAccount_number() {
		return account_number;
	}
	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}
	public String getRouting_number() {
		return routing_number;
	}
	public void setRouting_number(String routing_number) {
		this.routing_number = routing_number;
	}
	
	

}
