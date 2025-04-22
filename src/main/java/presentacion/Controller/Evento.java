package presentacion.Controller;

public enum Evento {

    // ----------------- EVENTOS COMUNES -----------------
    ERROR_CADENA_NO_ALFABETICA, // La cadena no contiene SOLO caracteres alfabéticos
    ERROR_TIPO_DOCUMENTO_INVALIDO,
    ERROR_NUMERO_TELEFONO_INVALIDO,

    // ----------------- INICIO DE SESIÓN -----------------
    GUI_INICIO_SESION,
    INICIA_CUENTA,

    // Errores en el inicio de sesión
    INICIO_SESION_ERROR_USUARIO_INEXISTENTE,
    INICIO_SESION_ERROR_CONTRASENYA_INCORRECTA,
    INICIO_SESION_ERROR_USUARIO_INCOMPLETO,
    INICIO_SESION_ERROR_CONTRASENYA_INCOMPLETA,

    // Inicio de sesión exitoso
    INICIO_SESION_OK,

    // Si el usuario es un administrador
    GUI_VISTAROLADMIN,

    // ----------------- CREACIÓN DE CUENTA ADMINISTRACIÓN -----------------
    GUI_CREAR_CUENTA_ADMINISTRACION,
    CREAR_CUENTA_ADM,

    // Errores en la creación de cuenta de administración
    CREAR_CUENTA_ADM_ERROR_FORMATO_DNI,
    CREAR_CUENTA_ADM_ERROR_DNI_ENCONTRADO,
    CREAR_CUENTA_ADM_ERROR_PASSWD_INCORRECTA,
    CREAR_CUENTA_ADM_ERROR_TEL_INCORRECTO,
    CREAR_CUENTA_ADM_ERROR_DATOS_NULOS,
    CREAR_CUENTA_ADM_ERROR_DATOS_VACIOS,

    // Cuenta de administración creada con éxito
    CREAR_CUENTA_ADM_EXITO,

    // ----------------- CREACIÓN DE TARJETA DE DÉBITO -----------------
    GUI_CREAR_TARJETA_DEBITO, // Evento para abrir la vista de creación de tarjeta
    CREAR_TARJETA_DEBITO, // Evento para ejecutar la creación de la tarjeta

    // Errores en la creación de tarjeta de débito
    CREAR_TARJETA_ERROR_TARJETA_NULL,
    CREAR_TARJETA_ERROR_DATOS_INCOMPLETOS,
    CREAR_TARJETA_ERROR_DATOS_NULOS,
    CREAR_TARJETA_ERROR_MAX_TARJETAS, // Error cuando el cliente tiene 5 tarjetas activas
    CREAR_TARJETA_ERROR_CUENTA_INEXISTENTE, // La cuenta IBAN no existe en la base de datos
    CREAR_TARJETA_ERROR_DB, // Error al insertar la tarjeta en la base de datos
    CREAR_TARJETA_ERROR_TARJETA_EXISTENTE, // Error al insertar la tarjeta en la base de datos

    // Tarjeta creada con éxito
    CREAR_TARJETA_OK,
    // Se presiona el boton de menu en el header
    BOTON_MENU,

    GUI_PRINCIPAL,

    // ----------------- CREACIÓN DE CUENTA BANCARIA -----------------
    GUI_CREAR_CUENTA_BANCARIA, // Evento para abrir la vista de creación de cuenta
    CREAR_CUENTA_BANCARIA, // Evento para ejecutar la creación de la cuenta

    // Errores en la creación de cuenta bancaria
    CREAR_CUENTA_BANCARIA_ERROR_CUENTA_NULL,
    CREAR_CUENTA_BANCARIA_ERROR_DATOS_INCOMPLETOS,
    CREAR_CUENTA_BANCARIA_ERROR_DATOS_NULOS,
    CREAR_CUENTA_BANCARIA_ERROR_DB, // Error al insertar la cuenta en la base de datos
    CREAR_CUENTA_BANCARIA_ERROR_TEL_INCORRECTO,

    // Cuenta bancaria creada con éxito
    CREAR_CUENTA_BANCARIA_OK, 
    ERROR_FORMATO_NUMERO_TELEFONO;
}
