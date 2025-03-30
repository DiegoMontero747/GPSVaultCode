package negocio.Tarjetas;

public class TTarjeta {
    private String nombre;
    private String apellidos;
    private String tipoDocumento; // DNI o NIE
    private String numeroDocumento;
    private String numeroCuenta;
    private String tipoTarjeta; // Debito o Credito
    private String estado; // Activa, Bloqueada, etc.
    private double limiteCredito; // Solo para crédito
    private String direccion;
    private String telefono;
    private String fechaNacimiento;

    // Constructor para tarjeta de débito
    public TTarjeta(String nombre, String apellidos, String tipoDocumento, String numeroDocumento, 
    		String numeroCuenta, String direccion, String telefono, String fechaNacimiento) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.numeroCuenta = numeroCuenta;
        this.tipoTarjeta = "Debito";
        this.estado = "Activa";
        this.limiteCredito = 0.0; // No aplica en débito
        this.direccion = direccion;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Constructor para tarjeta de crédito
    public TTarjeta(String nombre, String apellidos, String tipoDocumento, String numeroDocumento, 
    		String numeroCuenta, double limiteCredito, String direccion, String telefono, String fechaNacimiento) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.numeroCuenta = numeroCuenta;
        this.tipoTarjeta = "Credito";
        this.estado = "Activa";
        this.limiteCredito = limiteCredito;
        this.direccion = direccion;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Constructor vacío
    public TTarjeta() {}

    // Getters
    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public String getTipoDocumento() { return tipoDocumento; }
    public String getNumeroDocumento() { return numeroDocumento; }
    public String getNumeroCuenta() { return numeroCuenta; }
    public String getTipoTarjeta() { return tipoTarjeta; }
    public String getEstado() { return estado; }
    public double getLimiteCredito() { return limiteCredito; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }
    public String getFechaNacimiento() { return fechaNacimiento; }

    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }    
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setTipoTarjeta(String tipoTarjeta) { this.tipoTarjeta = tipoTarjeta; }
    public void setLimiteCredito(double limiteCredito) { this.limiteCredito = limiteCredito; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    @Override
    public String toString() {
        return "TTarjeta{" + "nombre='" + nombre + "apellidos=" + apellidos + '\'' + ", tipoDocumento='" + tipoDocumento + '\'' + ", numeroDocumento='" + numeroDocumento + '\'' + ", numeroCuenta='" + numeroCuenta + '\'' + ", tipoTarjeta='" + tipoTarjeta + '\'' + ", estado='" + estado + '\'' + ", limiteCredito=" + limiteCredito + ", direccion='" + direccion + '\'' + ", telefono='" + telefono + '\'' + ", fechaNacimiento='" + fechaNacimiento + '\'' + '}';
    }
}
