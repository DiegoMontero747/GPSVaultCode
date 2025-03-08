package MongoDB;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.bson.Document;
import org.junit.Test;

import com.mongodb.client.FindIterable;

import integracion.bbdd.Collections;
import integracion.bbdd.MongoDBManager;
public class MongoDBTest {

	private MongoDBManager mongo;
    
    @Test
    public void testConnection() {
        // Check that the MongoDBManager is not null
    	this.mongo = MongoDBManager.getInstance();
        assertNotNull("MongoClient should not be null", mongo);
        // Add more assertions as needed to check the connection status or perform other checks
        
        mongo.closeConnection();
    }
    
    @Test
    public void CRUD() {
    	this.mongo = MongoDBManager.getInstance();
    	mongo.getInstance();
    	Document d1 = new Document("nombre", "Juan").append("contraseña", "1234a")
    			.append("rol", "SC");
    	Document d2 = new Document("nombre", "Marcos").append("contraseña", "MeGusta")
    			.append("rol", "P");
    	Document d3 = new Document("nombre", "Juan").append("contraseña", "1234a")
    			.append("rol", "SC");
    	mongo.insertDocument(Collections.PERFIL, d1);
    	mongo.insertDocument(Collections.PERFIL, d2);
    	mongo.insertDocument(Collections.PERFIL, d3);
    	
    	 // Case 1: Read documents from the collection based on the query
        ArrayList<Document> result = mongo.readDocument(d1, Collections.PERFIL);

        // Count the number of retrieved documents
        int count = result.size();

        // Assert that the count matches the expected result
        assertEquals(1, count);

        // Case 2: Query for document with peso 28
        Document queryDocument2 = new Document("peso", 28);
        // Read documents from the collection based on the query
        ArrayList<Document> result2 = mongo.readDocument(queryDocument2, Collections.PERFIL);
        // Count the number of retrieved documents
        int count2 = result2.size();
        // Assert that the count matches the expected result
        assertEquals(2, count2);
        
        mongo.closeConnection();
    }
}

