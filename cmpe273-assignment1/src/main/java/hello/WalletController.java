package hello;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;


import javax.servlet.Filter;
//import javax.servlet.Filter;
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




@RestController
public class WalletController {
	
	List<User> user_list=new ArrayList<User>();
	Map<String,List<IDCard>> m_idCard=new HashMap<String, List<IDCard>>();
	Map<String,List<WebLogin>> m_webLogin=new HashMap<String,List<WebLogin>>();
	Map<String,List<BankAccount>> m_bankAccount=new HashMap<String,List<BankAccount>>();
	
	public List<User> getuser_list() {
		return user_list;
	}

	public void setuser_list(List<User> user_list) {
		this.user_list = user_list;
	}

	@RequestMapping(value = "api/v1/users", method = RequestMethod.POST)
	@ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public User createUsers(@Valid @RequestBody User user) 
	{
	
		User new_user=new User(user.getEmail(),user.getPassword(),new Date().toLocaleString());
    	new_user.setName(user.getName());
    	new_user.setUpdated_at(user.getUpdated_at());
    	new_user.setUser_id("u-"+String.valueOf(User.getId()));
    	user_list.add(new_user);
    	return new_user;
		
    }
	
	@RequestMapping(value = "api/v1/users/{user_id}", method = RequestMethod.GET)
	@ResponseStatus(org.springframework.http.HttpStatus.OK)
    public User listUsers(@PathVariable(value="user_id") String user_id) 
	{
		ListIterator<User> iter=user_list.listIterator();
		User user1=null;
		while(iter.hasNext())
		{
			user1=(User) iter.next();
			if(user1.getUser_id().equals(user_id))
			{
				break;
			}
			else
				user1=null;
			
		}
		return user1;
	 }
		
	@RequestMapping(value = "api/v1/users/{user_id}", method = RequestMethod.PUT)
	@ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public User updateUsers(@Valid @RequestBody User user,@PathVariable(value="user_id") String user_id) {
		ListIterator<User> iter=user_list.listIterator();
		User user_update=null;
		while(iter.hasNext())
		{
			user_update=(User) iter.next();
			if(user_update.getUser_id().equals(user_id))
			{
				user_update.setPassword(user.getPassword());
				user_update.setEmail(user.getEmail());
				user_update.setUpdated_at(new Date().toLocaleString());
				break;
			}
			else
				user_update=null;
		}
		return user_update;
    }
	
	@RequestMapping(value = "api/v1/users/{user_id}/idcards", method = RequestMethod.POST)
	@ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public IDCard createIDcards(@Valid @RequestBody IDCard id,@PathVariable(value="user_id") String user_id) 
	{
		List<IDCard> idList=new ArrayList<IDCard>();
		IDCard new_id=new IDCard(id.getCard_number(),id.getCard_name(),id.getExpiration_date());
		new_id.setCard_id("c-"+String.valueOf(IDCard.getId()));
		if(m_idCard.containsKey(user_id))
		{
			idList=m_idCard.get(user_id);
			idList.add(new_id);
			m_idCard.put(user_id,idList);
		}
		else
		{
			idList.add(new_id);
			m_idCard.put(user_id,idList);
		}
		return new_id;
		
	}
	
	@RequestMapping(value = "api/v1/users/{user_id}/idcards",method = RequestMethod.GET)
	@ResponseStatus(org.springframework.http.HttpStatus.OK)
    public List<IDCard> viewIDCards(@PathVariable(value="user_id") String user_id) 
	{
		return m_idCard.get(user_id);
	}
	
	@RequestMapping(value = "api/v1/users/{user_id}/idcards/{card_id}",method = RequestMethod.DELETE)
	@ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    public void deleteIDCards(@PathVariable(value="user_id") String user_id,@PathVariable(value="card_id") String card_id) 
	{
		List<IDCard> id_list=m_idCard.get(user_id);
		ListIterator<IDCard> iter=id_list.listIterator();
		while(iter.hasNext())
		{
			IDCard remove_iD=iter.next();
			if(remove_iD.getCard_id().equals(card_id))
			{
				id_list.remove(remove_iD);
			}
		}
	}
	
	@RequestMapping(value = "api/v1/users/{user_id}/weblogins", method = RequestMethod.POST)
	@ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public WebLogin createLoginIDs(@Valid @RequestBody WebLogin id,@PathVariable(value="user_id") String user_id) 
	{
		List<WebLogin> webLogin_list=new ArrayList<WebLogin>();
		WebLogin newWebLogin=new WebLogin(id.getUrl(),id.getLogin(),id.getPassword());
		newWebLogin.setLogin_id("l-"+String.valueOf(WebLogin.getId()));
		if(m_webLogin.containsKey(user_id))
		{
			webLogin_list=m_webLogin.get(user_id);
			webLogin_list.add(newWebLogin);
			m_webLogin.put(user_id,webLogin_list);
		}
		else
		{
			webLogin_list.add(newWebLogin);
			m_webLogin.put(user_id,webLogin_list);
		}
		return newWebLogin;
	}
	
	@RequestMapping(value = "api/v1/users/{user_id}/weblogins",method = RequestMethod.GET)
	@ResponseStatus(org.springframework.http.HttpStatus.OK)
    public List<WebLogin> viewWebLogins(@PathVariable(value="user_id") String user_id) 
	{
		return m_webLogin.get(user_id);
	}
	
	@RequestMapping(value = "api/v1/users/{user_id}/weblogins/{login_id}",method = RequestMethod.DELETE)
	@ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    public void deleteWebLogins(@PathVariable(value="user_id") String user_id,@PathVariable(value="login_id") String login_id) 
	{
		List<WebLogin> webLogin_list=m_webLogin.get(user_id);
		ListIterator<WebLogin> iter=webLogin_list.listIterator();
		while(iter.hasNext())
		{
			WebLogin remove_login=iter.next();
			if(remove_login.getLogin_id().equals(login_id))
			{
				webLogin_list.remove(remove_login);
			}
		}
	}
		
	@RequestMapping(value = "api/v1/users/{user_id}/bankaccounts", method = RequestMethod.POST)
	@ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public BankAccount createBankAccount(@Valid @RequestBody BankAccount bank,@PathVariable(value="user_id") String user_id) 
	{
		List<BankAccount> bankAccount_list=new ArrayList<BankAccount>();
		BankAccount newBank_account=new BankAccount(bank.getAccount_name(),bank.getAccount_number(),bank.getRouting_number());
		newBank_account.setBa_id("b-"+String.valueOf(BankAccount.getId()));
		if(m_bankAccount.containsKey(user_id))
		{
			bankAccount_list=m_bankAccount.get(user_id);
			bankAccount_list.add(newBank_account);
			m_bankAccount.put(user_id,bankAccount_list);
		}
		else
		{
			bankAccount_list.add(newBank_account);
			m_bankAccount.put(user_id,bankAccount_list);
		}
		return newBank_account;
	}
	
	@RequestMapping(value = "api/v1/users/{user_id}/bankaccounts",method = RequestMethod.GET)
	@ResponseStatus(org.springframework.http.HttpStatus.OK)
    public List<BankAccount> viewBankAccounts(@PathVariable(value="user_id") String user_id) 
	{
		return m_bankAccount.get(user_id);
	} 
	
	@RequestMapping(value = "api/v1/users/{user_id}/bankaccounts/{ba_id}",method = RequestMethod.DELETE)
	@ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    public void deleteBankAccount(@PathVariable(value="user_id") String user_id,@PathVariable(value="ba_id") String ba_id) 
	{
		List<BankAccount> bankAccount_list=m_bankAccount.get(user_id);
		ListIterator<BankAccount> iter=bankAccount_list.listIterator();
		while(iter.hasNext())
		{
			BankAccount removeBankAccount=iter.next();
			if(removeBankAccount.getBa_id().equals(ba_id))
			{
				bankAccount_list.remove(removeBankAccount);
			}
		}
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
	
    public Filter  etagFilter() {
		ShallowEtagHeaderFilter shallowEtagHeaderFilter = new ShallowEtagHeaderFilter();
		return shallowEtagHeaderFilter;
    }

	}
