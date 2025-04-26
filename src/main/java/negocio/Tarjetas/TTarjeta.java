package negocio.Tarjetas;

public class TTarjeta {
	private String documentoCliente;// del formulario
	private String numTarjeta;// vacio en inicio
	private String iban;// del formulario
	private int cvv;// -1 en inicio
	private String caducidad;// vacio en inicio
	private String tipoTarjeta;// de la GUI de CrearTarjetadebito
	private String estado;// Siempre "Activa"

	// Constructor completo
	public TTarjeta(String documentoCliente, String iban, String tipoTarjeta) {
		this.documentoCliente = documentoCliente;
		this.numTarjeta = "";
		this.iban = iban;
		this.cvv = -1;
		this.caducidad = "";
		this.tipoTarjeta = tipoTarjeta;
		this.estado = "Activa";
	}

	// Constructor vacío
	public TTarjeta() {
		this.tipoTarjeta = "Debito";
		this.estado = "Activa";
	}

	// Getters
	public String getDocCliente() {
		return documentoCliente;
	}

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

	// Setters
	public void setDocCliente(String docCliente) {
		this.documentoCliente = docCliente;
	}

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

	public void setTipoTarjeta(String tipoTarjeta) {
		this.tipoTarjeta = tipoTarjeta;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "TTarjeta{" + "numTarjeta='" + numTarjeta + "', iban='" + iban + "', cvv='" + cvv + "', caducidad='"
				+ caducidad + "', tipoTarjeta='" + tipoTarjeta + "', estado='" + estado + "'}";
	}
}
