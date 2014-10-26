package hello;

import java.net.UnknownHostException;
import java.util.Date;

import org.neo4j.cypher.internal.compiler.v2_1.planner.logical.steps.applyOptional;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;


public class UserManager {
	
	private String username="yayiharish";
	private char password[]="mongolab123".toCharArray();
	
	public User createUsers(User user) throws UnknownHostException{
	MongoClient client =
            new MongoClient(new ServerAddress("ds047050.mongolab.com", 47050));
	client.setWriteConcern(WriteConcern.NORMAL);
    DB database = client.getDB("mongodb");
    database.authenticate(username, password);
    DBCollection collection = database.getCollection("wallet");
    BasicDBObject doc=new BasicDBObject("_id",user.getUser_id())
    					.append("email_id",user.getEmail())
    					.append("password",user.getPassword())
    					.append("name",user.getName())
    					.append("created_at",user.getCreated_at());
    collection.insert(doc);
    return user;
    
    }
	
	public User listUsers(String user_id) throws UnknownHostException
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
	    while(cursor.hasNext()){
	    DBObject obj=cursor.next();
	    user.setUser_id((String) obj.get("_id"));
	    user.setEmail((String) obj.get("email_id"));
	    user.setName((String) obj.get("name"));
	    user.setPassword((String) obj.get("password"));
	    user.setCreated_at((String) obj.get("created_at"));
	    //System.out.println(cursor.next());
	    }
	    return user;
	 }
	public User updateUsers(User Uuser,String user_id) throws UnknownHostException
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
	    while(cursor.hasNext()){
	    	DBObject obj=cursor.next();
	    	
	    	obj.put("email_id",Uuser.getEmail());
		    obj.put("password",Uuser.getPassword());
		    obj.put("name",Uuser.getName());
		    obj.put("updated_at",new Date().toLocaleString());
		    Uuser.setUpdated_at((String) obj.get("updated_at"));
		    Uuser.setCreated_at((String) obj.get("created_at"));
		    Uuser.setUser_id((String) obj.get("_id"));
		    collection.save(obj);
		    
	    }
	    cursor.close();
	    
	    return Uuser;
	}
}
