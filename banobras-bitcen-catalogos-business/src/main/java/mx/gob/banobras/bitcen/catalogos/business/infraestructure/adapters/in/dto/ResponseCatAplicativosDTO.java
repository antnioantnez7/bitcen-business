package mx.gob.banobras.bitcen.catalogos.business.infraestructure.adapters.in.dto;
/**
 * ResponseCatalogoDTO.java:
 * 
 * Objeto que contiene los datos de respuesta del catalogo de aplicativos. 
 *  
 * @author Antonio Antunez Barbosa
 * @version 1.0, 27/09/2024
 * @see Documento "MAR - Marco Arquitectonico de Referencia"
 * @since JDK 17
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCatAplicativosDTO {
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
