package MongoDB;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import integracion.bbdd.Collections;
import integracion.bbdd.MongoDBManager;

public class MongoDBTest {

    private MongoDBManager mongo;

    @Before
    public void setUp() {
        mongo = MongoDBManager.getInstance();
    }

    @Test
    public void testConnection() {
//        assertNotNull("MongoDBManager instance should not be null", mongo);
    }

    @Test
    public void testCRUD() {
    	this.mongo = MongoDBManager.getInstance();
    	Document d1 = new Document("nombre", "Juan").append("peso", 28)
    			.append("altura", 145).append("edad", 48);
    	Document d2 = new Document("nombre", "Marcos").append("peso", 34)
    			.append("altura", 145).append("edad", 48);
    	Document d3 = new Document("nombre", "Juan").append("peso", 28)
    			.append("altura", 145).append("edad", 37);
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
        
        mongo.deleteDocument(Collections.PERFIL, d1);
        mongo.deleteDocument(Collections.PERFIL, d2);
        mongo.deleteDocument(Collections.PERFIL, d3);
                
        //mongo.closeConnection();
    }
 
}