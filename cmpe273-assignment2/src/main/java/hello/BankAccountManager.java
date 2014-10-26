package hello;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

public class BankAccountManager {
	
	private String username="yayiharish";
	private char password[]="mongolab123".toCharArray();

	public BankAccount createBankAccount(BankAccount bank,String user_id) throws UnknownHostException
	{
		MongoClient client =
	            new MongoClient(new ServerAddress("ds047050.mongolab.com", 47050));
		client.setWriteConcern(WriteConcern.NORMAL);
	    DB database = client.getDB("mongodb");
	    database.authenticate(username, password);
	    DBCollection collection = database.getCollection("wallet");
	    BasicDBObject query = new BasicDBObject("_id",user_id);
	    DBCursor cursor = collection.find(query);
	    while(cursor.hasNext())
	    {
	    	DBObject obj=cursor.next();
	    	BasicDBObject doc=new BasicDBObject("ba_id",bank.getBa_id())
			.append("account_name", bank.getAccount_name())
			.append("account_number",bank.getAccount_number())
			.append("routing_number",bank.getRouting_number());
	    	if(obj.get("bankaccounts")==null)
	    	{
	    		ArrayList list=new ArrayList();
		    	list.add(doc);
		    	obj.put("bankaccounts",list);
	    	}
	    	else
	    	{
	    		ArrayList list=(ArrayList)obj.get("bankaccounts");
	    		list.add(doc);
	    		obj.put("bankaccounts",list);
	    	}
	    	collection.save(obj);
	    }
	    return bank;
	    
	}
	public List<BankAccount> viewBankAccounts(String user_id) throws UnknownHostException
	{
		MongoClient client =
	            new MongoClient(new ServerAddress("ds047050.mongolab.com", 47050));
		client.setWriteConcern(WriteConcern.NORMAL);
	    DB database = client.getDB("mongodb");
	    database.authenticate(username, password);
	    DBCollection collection = database.getCollection("wallet");
	    BasicDBObject query = new BasicDBObject("_id",user_id);
	    DBCursor cursor = collection.find(query);
	    ArrayList list=null;
	    while(cursor.hasNext())
	    {
	    	DBObject obj=cursor.next();
	    	if(obj.get("bankaccounts")==null)
	    	{
	    		list=new ArrayList();
	    	}
	    	else
	    	{
	    		list=(ArrayList)obj.get("bankaccounts");
	    	}
	    }
	    return list;
	}
	
	public void deleteBankAccount(String user_id,String ba_id) throws UnknownHostException
	{
		MongoClient client =
	            new MongoClient(new ServerAddress("ds047050.mongolab.com", 47050));
		client.setWriteConcern(WriteConcern.NORMAL);
	    DB database = client.getDB("mongodb");
	    database.authenticate(username, password);
	    DBCollection collection = database.getCollection("wallet");
	    BasicDBObject query = new BasicDBObject("_id",user_id);
	    DBCursor cursor = collection.find(query);
	    while(cursor.hasNext())
	    {
	    	DBObject obj=cursor.next();
	    	ArrayList list=(ArrayList)obj.get("bankaccounts");
	    	for(int i=0;i<list.size();i++)
	    	{
	    		DBObject obj1=(DBObject) list.get(i);
	    		if(obj1.get("ba_id").equals(ba_id))
	    		{
	    			list.remove(i);
	    		}
	    	}
	    	obj.put("bankaccounts",list);
	    	collection.save(obj);
	    }
	    
	}


}
