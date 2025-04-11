package negocio.ManejoSesiones;

import java.util.ArrayList;

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
    	
        if (username == null || username.isBlank()) {
            return new ResultContext(Evento.INICIO_SESION_ERROR_USUARIO_INCOMPLETO, null); // Usuario incompleto
        }
        
        if (password == null || password.isBlank()) {
        	return new ResultContext(Evento.INICIO_SESION_ERROR_CONTRASENYA_INCOMPLETA, null); // Contrasenya incompletos
        }
       
        ArrayList<Document> docList = db.readDocument(new Document().append("Nombre_usuario", username), Collections.PERFIL);
        if (docList.isEmpty()) {
            return new ResultContext(Evento.INICIO_SESION_ERROR_USUARIO_INEXISTENTE, null);
        }else {
        
        Document doc = docList.get(0); // Tomamos el primer documento encontrado

        if (!password.equals(doc.getString("Contrasenya"))) {
            return new ResultContext(Evento.INICIO_SESION_ERROR_CONTRASENYA_INCORRECTA, null);
        }
        
        return new ResultContext(Evento.INICIO_SESION_OK, doc.getString("rol"));
        }
    }
    
    
}