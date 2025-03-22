package negocio.ManejoSesiones;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bson.Document;

import integracion.bbdd.Collections;
import integracion.bbdd.MongoDBManager;
import negocio.Factory.ResultContext;
import presentacion.Controller.Evento;

public class SACrearCuentaAdministracionImp implements SACrearCuentaAdministracion{

	private MongoDBManager db;
	
	public SACrearCuentaAdministracionImp() {
		db = MongoDBManager.getInstance();
	}
	
	public SACrearCuentaAdministracionImp(MongoDBManager db) {
		this.db = db;
	}
	
	@Override
	public ResultContext crearCuenta(TCrearCuentaAdm data) {
		//expresion regular para comprobar la validez del dni tiene que tener 8 numeros mas una letra de entre las que
		//estan en el patron
		String nifRegex = "/^[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]$/i";
		//compilamos el regex
		Pattern patron = Pattern.compile(nifRegex);
		//comprobamos que el dni tenga formato correcto
		Matcher matcher = patron.matcher(data.getDni());
		boolean nif_correct = matcher.find();
		if(nif_correct) {
			List<Document> perfiles = db.getAllDocuments(Collections.PERFIL);
			//iteramos sobre los perfiles buscando si ya hay alguno con ese DNI
			for(Document doc: perfiles) {
				if(doc.containsValue(data.getDni())) {
					return new ResultContext(Evento.CREAR_CUENTA_ADM_ERROR_DNI_ENCONTRADO,null);
				}
			}
			//comprobamos que el rol introducido es el correcto
			String rolRegex = "(?i)^(administracion|activo|pasivo)$";
			if(data.getRol().matches(rolRegex)) {
				Document nuevoperfil = new Document();
				nuevoperfil.append("nombre",data.getNombre());
				nuevoperfil.append("apellido",data.getApellido());
				nuevoperfil.append("rol",data.getRol());
				nuevoperfil.append("contrasenya",data.getConstrasenya());
				nuevoperfil.append("usuario",data.getUsuario());
				nuevoperfil.append("dni",data.getDni());
				db.insertDocument(Collections.PERFIL, nuevoperfil);
				//devuelve data para devolver los datos introducidos por si hacen falta
				return new ResultContext(Evento.CREAR_CUENTA_ADM_EXITO,data);
				
			}
			else {
				return new ResultContext(Evento.CREAR_CUENTA_ADM_ERROR_ROL_INCORRECTO,null);
			}
			
		}
		else {
			return new ResultContext(Evento.CREAR_CUENTA_ADM_ERROR_FORMATO_DNI,null);
		}
		
	}

}
