package negocio.Cuentas;

public class TCuenta {
    private String tipoDoc;
    private String nombre;
    private String apellidos;
    private String direccion;
    private String telefono;
    private int numTarjetas;

    public TCuenta(String tipoDoc, String nombre, String apellidos, String direccion, String telefono, int numTarjetas) {
        this.tipoDoc = tipoDoc;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.telefono = telefono;
        this.numTarjetas = numTarjetas;
    }

    public TCuenta() {}

    public String getTipoDoc() { return tipoDoc; }
    public void setTipoDoc(String tipoDoc) { this.tipoDoc = tipoDoc; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    
    public int getNumTarjetas() { return numTarjetas; }
    public void setNumTarjetas(int numTarjetas) { this.numTarjetas = numTarjetas; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
