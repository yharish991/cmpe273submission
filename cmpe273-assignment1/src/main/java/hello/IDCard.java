package hello;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

public class IDCard{

	private String card_id;
	@NotBlank(message="Card Number should not be blank")
	private String card_number;
	@NotBlank(message="Card Name should not be blank")
	private String card_name;
	private String expiration_date;
	private static int id=0;
		
	public static int getId() {
		return id;
	}

	public static void setId(int id) {
		IDCard.id = id;
	}

	public IDCard(){}
	
	public IDCard(String card_number, String card_name,
			String expiration_date) {
		super();
		id++;
		this.card_number = card_number;
		this.card_name = card_name;
		this.expiration_date = expiration_date;
	}
	
	public String getCard_id() {
		return card_id;
	}
	public void setCard_id(String card_id) {
		this.card_id = card_id;
	}
	public String getCard_number() {
		return card_number;
	}
	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}
	public String getCard_name() {
		return card_name;
	}
	public void setCard_name(String card_name) {
		this.card_name = card_name;
	}
	public String getExpiration_date() {
		return expiration_date;
	}
	public void setExpiration_date(String expiration_date) {
		this.expiration_date = expiration_date;
	}
	
	
}
