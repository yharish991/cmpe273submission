package hello;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.Filter;
import javax.validation.Valid;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.ShallowEtagHeaderFilter;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;




@RestController
public class WalletController {
	
	
	@RequestMapping(value = "api/v1/users", method = RequestMethod.POST)
	@ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public User createUsers(@Valid @RequestBody User user) throws UnknownHostException 
	{
	
		User new_user=new User(user.getEmail(),user.getPassword(),new Date().toLocaleString());
    	new_user.setName(user.getName());
    	new_user.setUser_id("u-"+String.valueOf(User.getId()));
    	return new UserManager().createUsers(new_user);
		
    }
	
	@RequestMapping(value = "api/v1/users/{user_id}", method = RequestMethod.GET)
	@ResponseStatus(org.springframework.http.HttpStatus.OK)
    public User listUsers(@PathVariable(value="user_id") String user_id) throws UnknownHostException 
	{
		return new UserManager().listUsers(user_id);
	 }
		
	@RequestMapping(value = "api/v1/users/{user_id}", method = RequestMethod.PUT)
	@ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public User updateUsers(@Valid @RequestBody User user,@PathVariable(value="user_id") String user_id) throws UnknownHostException {
		return new UserManager().updateUsers(user, user_id);
	 }
	
	@RequestMapping(value = "api/v1/users/{user_id}/idcards", method = RequestMethod.POST)
	@ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public IDCard createIDcards(@Valid @RequestBody IDCard id,@PathVariable(value="user_id") String user_id) throws UnknownHostException 
	{
		IDCard new_id=new IDCard(id.getCard_number(),id.getCard_name(),id.getExpiration_date());
		new_id.setCard_id("c-"+String.valueOf(IDCard.getId()));
		return new IDCardManager().createIDCard(new_id,user_id);
	}
	
	@RequestMapping(value = "api/v1/users/{user_id}/idcards",method = RequestMethod.GET)
	@ResponseStatus(org.springframework.http.HttpStatus.OK)
    public List<IDCard> viewIDCards(@PathVariable(value="user_id") String user_id) throws UnknownHostException 
	{
		return new IDCardManager().viewIDCards(user_id);
	}
	
	@RequestMapping(value = "api/v1/users/{user_id}/idcards/{card_id}",method = RequestMethod.DELETE)
	@ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    public void deleteIDCards(@PathVariable(value="user_id") String user_id,@PathVariable(value="card_id") String card_id) throws UnknownHostException 
	{
		new IDCardManager().deleteIDCards(user_id, card_id);		
	}
	
	@RequestMapping(value = "api/v1/users/{user_id}/weblogins", method = RequestMethod.POST)
	@ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public WebLogin createLoginIDs(@Valid @RequestBody WebLogin id,@PathVariable(value="user_id") String user_id) throws UnknownHostException 
	{
		List<WebLogin> webLogin_list=new ArrayList<WebLogin>();
		WebLogin newWebLogin=new WebLogin(id.getUrl(),id.getLogin(),id.getPassword());
		newWebLogin.setLogin_id("l-"+String.valueOf(WebLogin.getId()));
		return new WebLoginManager().createLoginIDs(newWebLogin, user_id);
	}
	
	@RequestMapping(value = "api/v1/users/{user_id}/weblogins",method = RequestMethod.GET)
	@ResponseStatus(org.springframework.http.HttpStatus.OK)
    public List<WebLogin> viewWebLogins(@PathVariable(value="user_id") String user_id) throws UnknownHostException 
	{
		return new WebLoginManager().viewWebLogins(user_id);
	}
	
	@RequestMapping(value = "api/v1/users/{user_id}/weblogins/{login_id}",method = RequestMethod.DELETE)
	@ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    public void deleteWebLogins(@PathVariable(value="user_id") String user_id,@PathVariable(value="login_id") String login_id) throws UnknownHostException 
	{
		new WebLoginManager().deleteWebLogins(user_id, login_id);
	}
		
	@RequestMapping(value = "api/v1/users/{user_id}/bankaccounts", method = RequestMethod.POST)
	@ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public BankAccount createBankAccount(@Valid @RequestBody BankAccount bank,@PathVariable(value="user_id") String user_id) throws UnknownHostException, UnirestException 
	{
		BankAccount newBank_account=new BankAccount(bank.getAccount_name(),bank.getAccount_number(),bank.getRouting_number());
		HttpResponse<JsonNode> response=Unirest.get("http://www.routingnumbers.info/api/data.json")
				.field("rn",bank.getRouting_number()).asJson();
		JsonNode body =response.getBody();
		if(String.valueOf(body.getObject().get("code")).equals("200"))
		{
			newBank_account.setAccount_name(String.valueOf(body.getObject().get("customer_name")));
		}
		else
			throw new UnirestException("");
		newBank_account.setBa_id("b-"+String.valueOf(BankAccount.getId()));
		return new BankAccountManager().createBankAccount(newBank_account, user_id);
	}
	
	@RequestMapping(value = "api/v1/users/{user_id}/bankaccounts",method = RequestMethod.GET)
	@ResponseStatus(org.springframework.http.HttpStatus.OK)
    public List<BankAccount> viewBankAccounts(@PathVariable(value="user_id") String user_id) throws UnknownHostException 
	{
		return new BankAccountManager().viewBankAccounts(user_id);
	} 
	
	@RequestMapping(value = "api/v1/users/{user_id}/bankaccounts/{ba_id}",method = RequestMethod.DELETE)
	@ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    public void deleteBankAccount(@PathVariable(value="user_id") String user_id,@PathVariable(value="ba_id") String ba_id) throws UnknownHostException 
	{
		new BankAccountManager().deleteBankAccount(user_id, ba_id);
	}
	@ExceptionHandler
    @ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
    @ResponseBody
    ErrorMessage handleException(Exception ex) {
		List<FieldError> fieldErrors = ((MethodArgumentNotValidException) ex).getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = ((MethodArgumentNotValidException) ex).getBindingResult().getGlobalErrors();
        List<String> errors = new ArrayList<String>(fieldErrors.size()+globalErrors.size());
        String error;
        for (FieldError fieldError : fieldErrors) {
            error = fieldError.getField() + ", " + fieldError.getDefaultMessage();
            errors.add(error);
        }
        for (ObjectError objectError : globalErrors) {
            error = objectError.getObjectName() + ", " + objectError.getDefaultMessage();
            errors.add(error);
        }
        return new ErrorMessage(errors);
    }
	@ExceptionHandler
    @ResponseStatus(org.springframework.http.HttpStatus.BAD_REQUEST)
    @ResponseBody
    void handleException1(UnirestException ex) {
				
    }
    public Filter  etagFilter() {
		ShallowEtagHeaderFilter shallowEtagHeaderFilter = new ShallowEtagHeaderFilter();
		return shallowEtagHeaderFilter;
    }

	}
