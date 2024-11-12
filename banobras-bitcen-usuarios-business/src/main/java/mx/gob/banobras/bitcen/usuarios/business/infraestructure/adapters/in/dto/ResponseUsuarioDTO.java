package mx.gob.banobras.bitcen.usuarios.business.infraestructure.adapters.in.dto;
/**
 * ResponseUsuarioDTO.java:
 * 
 * Objeto que contiene los datos de respuesta de usuarios. 
 *  
 * @author Marcos Gonzalez
 * @version 1.0, 13/06/2024
 * @see Documento "MAR - Marco Arquitectonico de Referencia"
 * @since JDK 17
 */
import lombok.Data;

@Data
public class ResponseUsuarioDTO {
	/** Estatus de la peticion */
	private Integer statusCode;
	/** Mensaje de la peticion */
	private String message;
	/** Variable identificador de éxito */
	private boolean sucess;
	/** Código de respuesta HTTP */
	private int responseType;
	/** Objeto del modelo de respuesta */
	private Object contenido;
}


