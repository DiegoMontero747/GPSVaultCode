package negocio.Cuentas;

public class TCuenta {
    private String tipoDoc;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String telefono;
    private String dni;
    private String cod_postal;

    public TCuenta(String tipoDoc, String nombre, String apellidos, String DNI, String direccion, String telefono) {
        this.tipoDoc = tipoDoc;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.dni = DNI;
    }

    public TCuenta() {}

    public String getTipoDoc() { return tipoDoc; }
    public void setTipoDoc(String tipoDoc) { this.tipoDoc = tipoDoc; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    
    public String getNumTarjetas() { return dni; }
    public void setNumTarjetas(String dni) { this.dni = dni; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getDNI() { return dni; }
    public void setDNI(String dni) { this.dni = dni; }
    
    public String getCodPostal() { return cod_postal; }
    public void setCodPostal(String cod) { this.cod_postal = cod; }
    }
