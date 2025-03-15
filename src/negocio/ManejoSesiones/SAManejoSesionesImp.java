package negocio.ManejoSesiones;

import org.bson.Document;

import integracion.bbdd.Collections;
import integracion.bbdd.MongoDBManager;
import negocio.Factory.ResultContext;
import presentacion.Controller.Evento;

public class SAManejoSesionesImp implements SAManejoSesiones {
	
	private MongoDBManager db;
	// necesario implementar dos constructores para poder hacer pruebas con mockito
	public SAManejoSesionesImp() {
		db = MongoDBManager.getInstance();
	}
	
	public SAManejoSesionesImp(MongoDBManager db) {
		this.db = db;
	}
    @Override
    public ResultContext inicioSesion(TSesion ses) {
    	String username = ses.getUsername();
    	String password = ses.getPsswd();
    	
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
        	
            return new ResultContext(Evento.INICIAR_SESSION_KO_ERROR_3, null); // Datos incompletos
        }
       
       
        Document doc = db.getDocumentByNombre(Collections.PERFIL, username);
        if(doc == null) {
        	 return new ResultContext(Evento.INICIAR_SESSION_ERROR_1, null); //No existe el usuario
        }
        if(!doc.get("password").toString().equals(password)) {
        	 return new ResultContext(Evento.INICIAR_SESSION_KO_ERROR_2, null); //contrasenya incorrecta
        }
        
        
        return new ResultContext(Evento.INICIAR_SESSION_OK, doc.get("rol")); // Inicio de sesión válido
    }
}