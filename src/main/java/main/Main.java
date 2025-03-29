package main;

import integracion.bbdd.MongoDBManager;
import presentacion.Controller.Context;
import presentacion.Controller.Controller;
import presentacion.Controller.Evento;
import presentacion.GUI.ApplicationContainer;

public class Main {
	public static  ApplicationContainer appCon;
	public static void main(String[] args) {

		try {
			MongoDBManager.initialize();
		} catch (Exception e) {

			e.printStackTrace();

		}

		ApplicationContainer.getInstance();
	}
}
