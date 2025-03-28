package negocio.Tarjetas;

public class TTarjeta {
    
		private String nombreCompleto;
	    private String tipoDocumento; // DNI o NIE
	    private String numeroDocumento;
	    private String numeroCuenta;
	    private String tipoTarjeta; // Debito o credito
		private String estado; // Activa, Bloqueada, etc.
	    private double limiteCredito; // Solo para crédito

    public TTarjeta(String nombreCompleto, String tipoDocumento, String numeroDocumento, String numeroCuenta) {
        this.nombreCompleto = nombreCompleto;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.numeroCuenta = numeroCuenta;
        this.tipoTarjeta = "Debito";
        this.estado = "Activa";
        this.limiteCredito = 0.0; // No en debito
    }

    public TTarjeta(String nombreCompleto, String tipoDocumento, String numeroDocumento, String numeroCuenta, double limiteCredito) {
        this.nombreCompleto = nombreCompleto;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.numeroCuenta = numeroCuenta;
        this.tipoTarjeta = "Credito";
        this.estado = "Activa";
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
    
    public void setTipoTarjeta(String tipoTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}

	public double getLimiteCredito() {
		return limiteCredito;
	}

	public void setLimiteCredito(double limiteCredito) {
		this.limiteCredito = limiteCredito;
	}
	
	@Override
	public String toString() {
		return "TTarjeta{" + "nombreCompleto='" + nombreCompleto + '\'' + ", tipoDocumento='" + tipoDocumento + '\''
				+ ", numeroDocumento='" + numeroDocumento + '\'' + ", numeroCuenta='" + numeroCuenta + '\''
				+ ", tipoTarjeta='" + tipoTarjeta + '\'' + ", estado='" + estado + '\'' + ", limiteCredito="
				+ limiteCredito + '}';
	}
}
