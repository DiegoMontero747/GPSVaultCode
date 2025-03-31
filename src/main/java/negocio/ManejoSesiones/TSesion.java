package negocio.ManejoSesiones;

import org.bson.Document;

public class TSesion {
	
	private String username;
	private String psswd;
	private String rol;
	
	public TSesion(String username, String psswd, String rol) {
		this.username = username;
		this.psswd = psswd;
		this.rol = rol;
	}
	
	public TSesion() {
		// Constructor vacio para poder hacer mockito
	}

	public String getUsername() {
		return this.username;
	}
	
	public String getPsswd() {
		return this.psswd;
	}
	public String getRol() {
		return this.rol;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPsswd(String psswd) {
		this.psswd = psswd;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}

	public Document toDocument() {
		Document doc = new Document();
	    if (username != null) doc.append("username", username);
	    if (psswd != null) doc.append("psswd", psswd);
	    if (rol != null) doc.append("rol", rol);
	    return doc;
	}
}
