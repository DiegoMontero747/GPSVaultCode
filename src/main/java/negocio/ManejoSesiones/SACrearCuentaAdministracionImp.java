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

		
		if(data.nullData()) {
			
			return new ResultContext(Evento.CREAR_CUENTA_ADM_ERROR_DATOS_NULOS,data);
		}
		
		if (data.datosVacios()) {
			return new ResultContext(Evento.CREAR_CUENTA_ADM_ERROR_DATOS_VACIOS,data);
		}
		
	
		
		if(validarDNI(data.getDni()) || validarNIE(data.getDni())) {
			List<Document> perfiles = db.readDocument(null,Collections.PERFIL);

			//iteramos sobre los perfiles buscando si ya hay alguno con ese DNI
			for(Document doc: perfiles) {
				if(doc.containsValue(data.getDni())) {
					return new ResultContext(Evento.CREAR_CUENTA_ADM_ERROR_DNI_ENCONTRADO,null);
				}
			}
			//comprobamos que el rol introducido es el correcto
			
			if(validarROL(data.getRol())) {
				Document nuevoperfil = new Document();
				nuevoperfil.append("nombre",data.getNombre());
				nuevoperfil.append("apellido",data.getApellido());
				nuevoperfil.append("rol",data.getRol());
				nuevoperfil.append("password",data.getConstrasenya());
				nuevoperfil.append("usuario",data.getUsuario());
				nuevoperfil.append("dni",data.getDni());
				nuevoperfil.append("telefono", data.getTelf());
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
	
// Validar DNI: 8 numeros y una letra
	private boolean validarDNI(String dni) {
	    String regexDNI = "^[0-9]{8}[A-Za-z]$";
	    return dni.matches(regexDNI);
	}

	// Validar NIE: Letra X/Y/Z, 7 numeros y una letra
	private boolean validarNIE(String nie) {
	    String regexNIE = "^[XYZ]\\d{7}[A-Za-z]$";
	    return nie.matches(regexNIE);
	}
	
	// Validar NIE: Letra X/Y/Z, 7 numeros y una letra
		private boolean validarROL(String rol) {
			String rolRegex = "(?i)^(servicios centrales|administracion|activo|pasivo)$";
		    return rol.matches(rolRegex);
		}

}
