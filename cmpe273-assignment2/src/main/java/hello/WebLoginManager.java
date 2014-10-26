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

public class WebLoginManager {
	
	private String username="yayiharish";
	private char password[]="mongolab123".toCharArray();

	public WebLogin createLoginIDs(WebLogin id,String user_id) throws UnknownHostException
	{
		MongoClient client =
	            new MongoClient(new ServerAddress("ds047050.mongolab.com", 47050));
		client.setWriteConcern(WriteConcern.NORMAL);
	    DB database = client.getDB("mongodb");
	    database.authenticate(username, password);
	    DBCollection collection = database.getCollection("wallet");
	    BasicDBObject query = new BasicDBObject("_id",user_id);
	    DBCursor cursor = collection.find(query);
	    User user=new User();
	    while(cursor.hasNext())
	    {
	    	DBObject obj=cursor.next();
	    	BasicDBObject doc=new BasicDBObject("login_id",id.getLogin_id())
			.append("url", id.getUrl())
			.append("login",id.getLogin())
			.append("password",id.getPassword());
	    	if(obj.get("weblogins")==null)
	    	{
	    		ArrayList list=new ArrayList();
		    	list.add(doc);
		    	obj.put("weblogins",list);
	    	}
	    	else
	    	{
	    		ArrayList list=(ArrayList)obj.get("weblogins");
	    		list.add(doc);
	    		obj.put("weblogins",list);
	    	}
	    	collection.save(obj);
	    }
	    return id;
	    
	}
	public List<WebLogin> viewWebLogins(String user_id) throws UnknownHostException
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
	    	if(obj.get("weblogins")==null)
	    	{
	    		list=new ArrayList();
	    	}
	    	else
	    	{
	    		list=(ArrayList)obj.get("weblogins");
	    	}
	    }
	    return list;
	}
	
	public void deleteWebLogins(String user_id,String login_id) throws UnknownHostException
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
	    	ArrayList list=(ArrayList)obj.get("weblogins");
	    	for(int i=0;i<list.size();i++)
	    	{
	    		DBObject obj1=(DBObject) list.get(i);
	    		if(obj1.get("login_id").equals(login_id))
	    		{
	    			list.remove(i);
	    		}
	    	}
	    	obj.put("weblogins",list);
	    	collection.save(obj);
	    }
	    
	}

}
