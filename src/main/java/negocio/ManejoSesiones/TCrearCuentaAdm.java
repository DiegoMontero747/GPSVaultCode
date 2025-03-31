package negocio.ManejoSesiones;

public class TCrearCuentaAdm {

	private String nombre;
	private String apellido;
	private String contrasenya;
	private String dni;
	private String usuario;
	private String rol;
	private String telf;
	
	public TCrearCuentaAdm(String nombre, String apellido, String password,
			String DNI, String usuario, String rol, String telf ) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.contrasenya = password;
		this.dni = DNI;
		this.usuario = usuario;
		this.rol = rol;
		this.telf = telf;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getConstrasenya() {
		return contrasenya;
	}
	public void setConstrasenya(String constrasenya) {
		this.contrasenya = constrasenya;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	public String getTelf() {
		return this.telf;
	}
	public void setTelf(String telf) {
		this.telf = telf;
	}
	
	public boolean nullData() {
		if(nombre == null || usuario == null || dni == null || rol == null || contrasenya == null || apellido == null || telf == null)
		{
			return true;
		}
		
		return false;
	}
	
	public boolean datosVacios() {
		if(!nullData()) {
			return nombre.isBlank() || usuario.isBlank() || dni.isBlank() || rol.isBlank() || contrasenya.isBlank()
					|| apellido.isBlank() || telf.isBlank();
		}
		//devuelve falso si los datos son nulos
		return false;
	}
}
