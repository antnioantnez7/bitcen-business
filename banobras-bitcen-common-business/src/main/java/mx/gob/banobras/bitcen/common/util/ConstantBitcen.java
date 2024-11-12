package mx.gob.banobras.bitcen.common.util;


/**
 * ConstantBitcen.java:
 * 
 * Objeto que contiene las constantes del aplicativo. 
 *  
 * @author Marcos Gonzalez
 * @version 1.0, 13/06/2024
 * @see Documento "MAR - Marco Arquitectonico de Referencia"
 * @since JDK 17
 */
public enum ConstantBitcen {
	
	/** Constante para el mensaje de error 500 */
	MSG_ERROR_500 ("El servicio no response, contactar al administrador del sistema."),
	/** Constante servicio no disponible de usuarios */
	MSG_SERVICE_NO_AVAILABLE_USER ("Servicio no disponible - Usuarios."),
	/**  Constante servicio no disponible de productos */
	MSG_SERVICE_NO_AVAILABLE_PRODCUCT ("Servicio no disponible - Productos."),
	/**  Constante servicio no disponible de accesos */
	MSG_SERVICE_NO_AVAILABLE_ACCESS ("Servicio no disponible - Accesos."),
	/**  Constante servicio no disponible de accesos */
	MSG_SERVICE_NO_AVAILABLE_OPERATIONS ("Servicio no disponible - Operaciones."),
	/**  Constante servicio no disponible de accesos */
	MSG_SERVICE_NO_AVAILABLE_CATALOGS ("Servicio no disponible - Catalogos."),
	/** Constante para el mensaje de credenciales incorrectas */
	MSG_CREDENTIALS_INVALID ("Credenciales incorrectas."),
	/** Constante servicio no diponible token */
	MSG_SERVICE_TOKEN_NO_AVAILABLE ("Servicio no disponible - Token."),
	/** Constante para el HTTPS*/
	HTTPS ("HTTPS"),
	/** Constante para el HTTPS*/
	SSL ("SSL"),
	/** Constante para el HTTPS*/
	FIND_BY_ID ("/findById/"),
	/** Constante para el HTTPS*/
	SAVE ("/save"),
	;
	/** Variable con la descrpcion de la constante. **/
	private String name;

	/**
	 * Constructor de la clase.
	 * @param name
	 */
	private ConstantBitcen(String name) {
		this.name = name;
	}

	/**
	 * Metodo para obtener la descripcion de la constante.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

}
