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

public class IDCardManager {
	
	private String username="yayiharish";
	private char password[]="mongolab123".toCharArray();

	public IDCard createIDCard(IDCard id,String user_id) throws UnknownHostException
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
	    	BasicDBObject doc=new BasicDBObject("card_id",id.getCard_id())
			.append("card_name", id.getCard_name())
			.append("card_number",id.getCard_number())
			.append("expiration_date",id.getExpiration_date());
	    	if(obj.get("idcards")==null)
	    	{
	    		ArrayList list=new ArrayList();
		    	list.add(doc);
		    	obj.put("idcards",list);
		    
	    	}
	    	else
	    	{
	    		ArrayList list=(ArrayList)obj.get("idcards");
	    		list.add(doc);
	    		obj.put("idcards",list);
	    	}
	    	collection.save(obj);
	    }
	    return id;
	    
	}
	public List<IDCard> viewIDCards(String user_id) throws UnknownHostException
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
	    	if(obj.get("idcards")==null)
	    	{
	    		list=new ArrayList();
	    	}
	    	else
	    	{
	    		list=(ArrayList)obj.get("idcards");
	    	}
	    }
	    return list;
	}
	
	public void deleteIDCards(String user_id,String card_id) throws UnknownHostException
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
	    	ArrayList list=(ArrayList)obj.get("idcards");
	    	for(int i=0;i<list.size();i++)
	    	{
	    		DBObject obj1=(DBObject) list.get(i);
	    		if(obj1.get("card_id").equals(card_id))
	    		{
	    			list.remove(i);
	    		}
	    	}
	    	obj.put("idcards",list);
	    	collection.save(obj);
	    }
	    
	}
}
