package negocio.Cuentas;

public class TCuenta {
	
	private String username;
	private String psswd;
	private String rol;
	
	public TCuenta(String username, String psswd, String rol) {
		this.username = username;
		this.psswd = psswd;
		this.rol = rol;
	}
	
	public TCuenta() {
		// TODO Auto-generated constructor stub
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
}
