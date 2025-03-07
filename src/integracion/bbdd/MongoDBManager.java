package integracion.bbdd;

import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;

public class MongoDBManager {
	private static MongoDBManager instance;
	private static MongoClient mongoClientv;
	private static MongoDatabase database;

	public MongoDBManager() {
		ServerApi serverApi = ServerApi.builder()
				.version(ServerApiVersion.V1)
				.build();
		// Replace the uri string with your MongoDB deployment's connection string
		String uri = "mongodb+srv://general:n2fAhuactRAJYxB9@cluster0.ogxis.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
		MongoClientSettings settings = MongoClientSettings.builder()
				.applyConnectionString(new ConnectionString(uri))
				.serverApi(serverApi)
				.build();

		MongoDBManager.mongoClientv = MongoClients.create(settings);
		try {
			// Send a ping to confirm a successful connection
			MongoDBManager.database = MongoDBManager.mongoClientv.getDatabase("VaultCode");
			MongoDBManager.database.runCommand(new Document("ping", 1));
			System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}

	public static synchronized MongoDBManager getInstance() {
		if (instance == null) {
			instance = new MongoDBManager();
		}
		return instance;
	}

	/*
	 * creatQuery(Document d) passing a document creates a query with only the
	 * present fields in the document
	 * once the query is created it will be returned to the caller function
	 */
	private Bson createQuery(Document d) {
		Bson query = null;
		for (Map.Entry<String, Object> entry : d.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			Bson filter;
			// goes through all the keys one by one and creates a BSON document w ich acts as
			// the query
			if (value != null) {
				filter = Filters.eq(key, value);
				query = (query == null) ? filter : Filters.and(query, filter);
			}
		}

		return query;
	}

	// CRUD operations
	public void insertDocument(String collectionName, Document d) {
		database.getCollection(collectionName).insertOne(d);
	}

	public void insertManyDocument(String CollectionName, List<Document> dList) {
		database.getCollection(CollectionName).insertMany(dList);
	}

	public void deleteDocument(String collectionName, Document d) {
		database.getCollection(collectionName).deleteOne(d);
	}

	/*
	 * readDocument(Document d, String CollectionName) reads documents in a
	 * collection with name CollectionName
	 * The function has a couple useful functionalities:
	 * if(d == null) returns the entire collection basically performs a readAll
	 * if(d != null) creates a query from d and returns all the documents from the
	 * queried collection
	 * these could be many documents or just one depending on the fields present
	 * qhen creating the query
	 */
	public ArrayList<Document> readDocument(Document d, String CollectionName) {
		FindIterable<Document> list;
		ArrayList<Document> result = new ArrayList<Document>();
		if (d != null) {
			Bson query = createQuery(d);
			list = database.getCollection(CollectionName).find(query);
		} else {
			list = database.getCollection(CollectionName).find();
		}
		
		for (Document document : list) {
			result.add(document);
		}
		
		return result;
	}

	public void updateDocument(String CollectionName, Document d, Document dUp) {
		
		BasicDBObject updateObject = new BasicDBObject();
		updateObject.put("$set", dUp); // (3)

		database.getCollection(CollectionName).updateOne(d, updateObject);
	}

	// Add other methods for database operations as needed

	public void closeConnection() {
		if (mongoClientv != null) {
			mongoClientv.close();
			System.out.println("Connection to MongoDB is CLOSED");
		}
	}

	public static void open() {
		ServerApi serverApi = ServerApi.builder()
				.version(ServerApiVersion.V1)
				.build();
		// Replace the uri string with your MongoDB deployment's connection string1q
		String uri = "mongodb+srv://general:n2fAhuactRAJYxB9@cluster0.ogxis.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
		MongoClientSettings settings = MongoClientSettings.builder()
				.applyConnectionString(new ConnectionString(uri))
				.serverApi(serverApi)
				.build();

		mongoClientv = MongoClients.create(settings);
		try {
			// Send a ping to confirm a successful connection
			database = mongoClientv.getDatabase("VaultCode");
			database.runCommand(new Document("ping", 1));
			System.out.println("Pinged your deployment. You successfully connected to MongoDB!");
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}

	public static MongoCollection<Document> getCollection(String collectionName) {
		return database.getCollection(collectionName);
	}

	
	public Document getDocumentByNombre(String collectionName, String nombre) {
		  Document document = null;
		  try {
		  MongoCollection<Document> collection =
		  database.getCollection(collectionName);
		  Bson filter = Filters.eq("nombre", nombre);
		  document = collection.find(filter).first();
		  } catch (Exception e) {
		  System.err.
		  println("Error al obtener el documento por nombre de la colección: " + e);
		  }
		  return document;
		  
	}  
		  
	  public List<Document> getAllDocuments(String collectionName) {
		  List<Document> documents = new ArrayList<>();
		  	try {
		  		MongoCollection<Document> collection = database.getCollection(collectionName);
		  		FindIterable<Document> iterable = collection.find();
		  		iterable.forEach(documents::add);
		  	} catch (Exception e) {
		  		System.err.println("Error al obtener todos los documentos de la colección: " + e);
		  }
		  return documents;
	  }
		  
	

	/*
	 * public List<Document> getAllDocuments(String collectionName) {
	 * List<Document> documents = new ArrayList<>();
	 * try {
	 * MongoCollection<Document> collection =
	 * database.getCollection(collectionName);
	 * FindIterable<Document> iterable = collection.find();
	 * iterable.forEach(documents::add);
	 * } catch (Exception e) {
	 * System.err.println("Error al obtener todos los documentos de la colección: "
	 * + e);
	 * }
	 * return documents;
	 * }
	 * 
	 * public Document getDocumentByNombre(String collectionName, String nombre) {
	 * Document document = null;
	 * try {
	 * MongoCollection<Document> collection =
	 * database.getCollection(collectionName);
	 * Bson filter = Filters.eq("nombre", nombre);
	 * document = collection.find(filter).first();
	 * } catch (Exception e) {
	 * System.err.
	 * println("Error al obtener el documento por nombre de la colección: " + e);
	 * }
	 * return document;
	 * }
	 * 
	 * public Document getDocumentByID(String collectionName, ObjectId nombre) {
	 * Document document = null;
	 * try {
	 * MongoCollection<Document> collection =
	 * database.getCollection(collectionName);
	 * Bson filter = Filters.eq("_id", nombre);
	 * document = collection.find(filter).first();
	 * } catch (Exception e) {
	 * System.err.
	 * println("Error al obtener el documento por nombre de la colección: " + e);
	 * }
	 * return document;
	 * }
	 * 
	 * public List<Document> readDocumentListarRutinas(ObjectId nombre, String
	 * collectionName){
	 * 
	 * List<Document> documents = new ArrayList<>();
	 * try {
	 * MongoCollection<Document> collection =
	 * database.getCollection(collectionName);
	 * Bson filter = Filters.eq("idPerfil", nombre);
	 * FindIterable<Document> iterable = collection.find(filter);
	 * iterable.forEach(documents::add);
	 * } catch (Exception e) {
	 * System.err.println("Error al obtener todos los documentos de la colección: "
	 * + e);
	 * }
	 * return documents;
	 * }
	 * 
	 */

}
