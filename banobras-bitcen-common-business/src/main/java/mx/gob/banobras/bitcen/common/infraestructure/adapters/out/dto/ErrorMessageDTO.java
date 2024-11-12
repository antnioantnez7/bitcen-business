package mx.gob.banobras.bitcen.common.infraestructure.adapters.out.dto;

/**
 * ErrorMessageDTO.java:
 * 
 * Objeto que contiene los datos para el mensaje de error. 
 *  
 * @author Marcos Gonzalez
 * @version 1.0, 13/06/2024
 * @see Documento "MAR - Marco Arquitectonico de Referencia"
 * @since JDK 17
 */

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorMessageDTO {
	  /** Variable para el estatus de error, de la petición */
	  private int statusCode;
	  /** Variable para la fecha de error, de la petición */
	  private Date timestamp;
	  /** Variable del mensaje de error, de la petición */
	  private String message;
	  /** Variable del detalle de error, de la petición */
	  private String detail;
	  
	 /**
	  * Contructor principal de la clase
	  * 
	  * @param statusCode
	  * @param timestamp
	  * @param message
	  * @param detail
	  */
	public ErrorMessageDTO(int statusCode, Date timestamp, String message, String detail) {
		super();
		this.statusCode = statusCode;
		this.timestamp = timestamp;
		this.message = message;
		this.detail = detail;
	}
	  
	/**
	 * Contructor de la clase
	 * 
	 * @param statusCode
	 * @param timestamp
	 * @param message
	 */
	public ErrorMessageDTO(int statusCode, Date timestamp, String message) {
		super();
		this.statusCode = statusCode;
		this.timestamp = timestamp;
		this.message = message;
		
	}
	  
	  
	  
}
