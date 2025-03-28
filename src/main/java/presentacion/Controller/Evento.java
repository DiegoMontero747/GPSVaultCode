package presentacion.Controller;

public enum Evento {


        // ----------------- EVENTOS COMUNES -----------------

    ERROR_CADENA_NO_ALFABETICA, // La cadena no contiene SOLO caracteres alfabeticos
    ERROR_TIPO_DOCUMENTO_INVALIDO, // Tipo de documento no valido


    // ----------------- INICIO DE SESION -----------------
    GUI_INICIO_SESION,
    INICIA_CUENTA,
    
    // Errores en el inicio de sesion
    INICIO_SESION_ERROR_USUARIO_INEXISTENTE,
    INICIO_SESION_ERROR_CONTRASENYA_INCORRECTA,
    INICIO_SESION_ERROR_USUARIO_INCOMPLETO,
    INICIO_SESION_ERROR_CONTRASENYA_INCOMPLETA,
    
    // Inicio de sesion exitoso
    INICIO_SESION_OK,

    // ----------------- CREACION DE TARJETA DE DEBITO -----------------
    GUI_CREAR_TARJETA_DEBITO, // Evento para abrir la vista de creacion de tarjeta

    CREAR_TARJETA_DEBITO, // Evento para ejecutar la creacion de la tarjeta

    // Errores en la creacion de tarjeta de debito
    CREAR_TARJETA_ERROR_TARJETA_NULL, //La tarjeta no es valida (es null)
    CREAR_TARJETA_ERROR_DATOS_INCOMPLETOS, // Faltan datos obligatorios
    CREAR_TARJETA_ERROR_DATOS_NULOS, //Datos nulos
    CREAR_TARJETA_ERROR_CUENTA_INEXISTENTE, // La cuenta IBAN no existe en la base de datos
    CREAR_TARJETA_ERROR_DB, // Error al insertar la tarjeta en la base de datos
    
    // Tarjeta creada con exito
    CREAR_TARJETA_OK,    
 // ----------------- CREACION DE CUENTA BANCARIA -----------------
    GUI_CREAR_CUENTA_BANCARIA, // Evento para abrir la vista de creacion de tarjeta

    CREAR_CUENTA_BANCARIA, // Evento para ejecutar la creacion de la tarjeta

    // Errores en la creacion de tarjeta de debito
    CREAR_CUENTA_BANCARIA_ERROR_CUENTA_NULL, //La cuenta no es valida (es null)
    CREAR_CUENTA_BANCARIA_ERROR_DATOS_INCOMPLETOS, // Faltan datos obligatorios
    CREAR_CUENTA_BANCARIA_ERROR_DATOS_NULOS, // Datos nulos
    CREAR_CUENTA_BANCARIA_ERROR_DB, // Error al insertar la cuenta en la base de datos
    
    // Tarjeta creada con exito
    CREAR_CUENTA_BANCARIA_OK;
}
