package main;

import integracion.bbdd.MongoDBManager;
import presentacion.Controller.Context;
import presentacion.Controller.Controller;
import presentacion.Controller.Evento;

public class Main {
	public static void main(String[] args) {

		try {
			 MongoDBManager.initialize();
		} catch (Exception e) {

			e.printStackTrace();

		}

		Controller.getInstance().handleRequest(new Context(Evento.GUI_INICIO_SESION, null));
	}
}
