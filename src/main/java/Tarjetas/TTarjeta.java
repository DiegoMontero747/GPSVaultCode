package Tarjetas;

public class TTarjeta {
    
		private String nombreCompleto;
	    private String tipoDocumento; // DNI / NIE
	    private String numeroDocumento;
	    private String numeroCuenta; // IBAN
	    private String tipoTarjeta; // "Debito" o "Credito"
	    private String estado; // Activa, Bloqueada, etc.
	    private double limiteCredito; // Solo para crédito

    public TTarjeta(String nombreCompleto, String tipoDocumento, String numeroDocumento, String numeroCuenta) {
        this.nombreCompleto = nombreCompleto;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.numeroCuenta = numeroCuenta;
        this.tipoTarjeta = "Debito"; // Por defecto, débito
        this.estado = "Activa"; // Se crea activa por defecto
        this.limiteCredito = 0.0; // No aplica a débito
    }

    // Constructor para tarjeta de crédito
    public TTarjeta(String nombreCompleto, String tipoDocumento, String numeroDocumento, String numeroCuenta, double limiteCredito) {
        this.nombreCompleto = nombreCompleto;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.numeroCuenta = numeroCuenta;
        this.tipoTarjeta = "Credito"; // Específico para crédito
        this.estado = "Activa"; // Se crea activa por defecto
        this.limiteCredito = limiteCredito;
    }
    
    // Constructor vacío
    public TTarjeta() {}

    // Getters
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public String getEstado() {
        return estado;
    }

    // Setters
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
