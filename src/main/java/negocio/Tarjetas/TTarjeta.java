package negocio.Tarjetas;

public class TTarjeta {
    private String numTarjeta;
    private String iban;
    private int cvv;
    private String caducidad;
    private String tipoTarjeta; // Siempre "Debito"
    private String estado;      // Siempre "Activa"
    private String numeroDocumento;

    // Constructor completo
    public TTarjeta(String numTarjeta, String iban, int cvv, String caducidad, String numeroDocumento) {
        this.numTarjeta = numTarjeta;
        this.iban = iban;
        this.cvv = cvv;
        this.caducidad = caducidad;
        this.tipoTarjeta = "Debito";
        this.estado = "Activa";
        this.numeroDocumento = numeroDocumento;
    }

    // Constructor vacío
    public TTarjeta() {
        this.tipoTarjeta = "Debito";
        this.estado = "Activa";
    }

    // Getters
    public String getNumTarjeta() {
        return numTarjeta;
    }

    public String getIban() {
        return iban;
    }

    public int getCvv() {
        return cvv;
    }

    public String getCaducidad() {
        return caducidad;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public String getEstado() {
        return estado;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    // Setters
    public void setNumTarjeta(String numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public void setCaducidad(String caducidad) {
        this.caducidad = caducidad;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    @Override
    public String toString() {
        return "TTarjeta{" +
                "numTarjeta='" + numTarjeta + '\'' +
                ", iban='" + iban + '\'' +
                ", cvv=" + cvv +
                ", caducidad='" + caducidad + '\'' +
                ", tipoTarjeta='" + tipoTarjeta + '\'' +
                ", estado='" + estado + '\'' +
                ", numeroDocumento='" + numeroDocumento + '\'' +
                '}';
    }
}
