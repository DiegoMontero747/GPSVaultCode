package negocio;

import org.bson.Document;

import integracion.bbdd.*;
public class ManejoSesiones {

	public ManejoSesiones() {
		
	}
	
	
	public int InicioSesion(String usuario, String contrasenya,String rol) {
		MongoDBManager db = MongoDBManager.getInstance();
		
		//TODO Checkear si existe el usuario y que la contraseña coincide
		Document doc = new Document();
		doc.append("nombre", usuario);
		doc.append("contrasenya", contrasenya);
		doc.append("rol", rol);
		db.insertDocument("Usuario", doc);
		//TODO Crearlo en la base de datos
		return 0;
		
	}
	
}
