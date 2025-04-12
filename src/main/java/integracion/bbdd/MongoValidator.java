package integracion.bbdd;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.Set;

import org.bson.Document;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;
import java.util.Set;
import java.util.regex.Pattern;

public class MongoValidator {
    /*
     * Esta clase valida los documentos que se insertan en las colecciones de MongoDB según un esquema predefinido.
     * Cada colección tiene un esquema que define los campos y sus tipos.
     * Esta clase se puede utilizar como guía de el esquema de la base de datos para los SA.
     */
	
	
    // Esquema definido para cada colección (campos y tipos)
    private static final Map<String, Map<String, Class<?>>> COLLECTION_SCHEMAS = new HashMap<>();
    
    static {
        // Esquema para CLIENTE
        Map<String, Class<?>> clienteSchema = new HashMap<>();
        clienteSchema.put("_id", ObjectId.class); // ID único generado por MongoDB
        clienteSchema.put("DNI", String.class);
        clienteSchema.put("Nombre", String.class);
        clienteSchema.put("Apellidos", String.class);
        clienteSchema.put("Telefono", Integer.class);
        clienteSchema.put("Dir", String.class);
        clienteSchema.put("Cod-postal", Integer.class);
        COLLECTION_SCHEMAS.put("CLIENTE", clienteSchema);
        
        // Esquema para CUENTABANC
        Map<String, Class<?>> cuentaBancSchema = new HashMap<>();
        cuentaBancSchema.put("_id", ObjectId.class); // ID único generado por MongoDB
        cuentaBancSchema.put("IBAN", String.class);
        cuentaBancSchema.put("Titular", String.class);
        cuentaBancSchema.put("Fondos",Float.class);
        COLLECTION_SCHEMAS.put("CUENTABANC", cuentaBancSchema);
        
        // Esquema para TARJETA
        Map<String, Class<?>> tarjetaSchema = new HashMap<>();
        tarjetaSchema.put("_id", ObjectId.class); // ID único generado por MongoDB
        tarjetaSchema.put("Nombre_tarjeta", String.class);
        tarjetaSchema.put("CVV", Integer.class);
        tarjetaSchema.put("Caducidad", String.class);
        tarjetaSchema.put("IBAN", String.class); // Referencia a cuenta bancaria
        COLLECTION_SCHEMAS.put("TARJETA", tarjetaSchema);
        
        // Esquema para PERFIL
        Map<String, Class<?>> perfilSchema = new HashMap<>();
        perfilSchema.put("_id", ObjectId.class); // ID único generado por MongoDB
        perfilSchema.put("DNI", String.class);
        perfilSchema.put("Nombre", String.class);
        perfilSchema.put("Apellidos", String.class);
        perfilSchema.put("Rol", String.class);
        perfilSchema.put("Contrasenya", String.class);
        perfilSchema.put("Nombre_usuario", String.class); // Referencia a cuenta bancaria
        perfilSchema.put("Telefono", Integer.class);
        COLLECTION_SCHEMAS.put("PERFIL", perfilSchema);
        
        //ESQUEMA PARA TEST_BD
        // Este esquema es necesario para las pruebas de la BD de MongoDB NO CORRESPONDE A NINGUN SA
        Map<String, Class<?>> testSchema = new HashMap<>();
        testSchema.put("_id", ObjectId.class); // ID único generado por MongoDB
        testSchema.put("nombre", String.class);
        testSchema.put("peso", Integer.class);
        testSchema.put("altura", Integer.class);
        testSchema.put("edad", Integer.class);
        COLLECTION_SCHEMAS.put("TEST_BD", testSchema);
    }
    
    /**
     * Valida un documento contra el esquema de su colección
     * @param collectionName Nombre de la colección
     * @param document Documento a validar
     * @return true si el documento es válido
     * @throws IllegalArgumentException si la validación falla
     */
    public static boolean validateDocument(String collectionName, Document document) {
        if (!COLLECTION_SCHEMAS.containsKey(collectionName)) {
            throw new IllegalArgumentException("Colección desconocida: " + collectionName);
        }
        
        Map<String, Class<?>> schema = COLLECTION_SCHEMAS.get(collectionName);
        Set<String> documentFields = document.keySet();
        
        // 1. Verificar campos requeridos
        for(String docField : documentFields) {
			if (!schema.containsKey(docField)) {
				throw new IllegalArgumentException(
					String.format("Campo '%s' no permitido en colección %s", 
					docField, collectionName));
			}
		}
        
        
        // 2. Verificar tipos de datos
        for (Map.Entry<String, Object> entry : document.entrySet()) {
            String field = entry.getKey();
            Object value = entry.getValue();
               
            Class<?> expectedType = schema.get(field);
            
            // Manejo especial para tipos numéricos (Integer/Double)
            if (expectedType == Integer.class && value instanceof Number) {
                continue; // Aceptamos cualquier número como entero
            }
            
            if (value == null || !expectedType.isInstance(value)) {
                throw new IllegalArgumentException(
                    String.format("Tipo incorrecto para campo '%s'. Esperado: %s, Obtenido: %s", 
                    field, expectedType.getSimpleName(), 
                    value != null ? value.getClass().getSimpleName() : "null"));
            }
            
           
        }
        
        return true;
    }
    
    
}
