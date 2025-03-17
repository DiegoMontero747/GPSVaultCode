package negocio.Factory;

import presentacion.Controller.Evento;

public class ResultContext {
	private Evento evento;
	private Object datos;
	
	public Evento getEvento() {
		return evento;
	}

	public Object getDato() {
		return datos;
	}
	
	public ResultContext(Evento evento, Object datos)
	{
		this.evento = evento;
		this.datos = datos;
	}
}
