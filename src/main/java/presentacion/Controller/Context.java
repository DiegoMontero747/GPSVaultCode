package presentacion.Controller;

public class Context {

	private Evento evento;
	private Object datos;
	
	public Evento getEvento() {
		return evento;
	}

	public Object getDato() {
		return datos;
	}
	
	public Context(Evento evento, Object datos)
	{
		this.evento = evento;
		this.datos = datos;
	}
}
