package mx.gob.banobras.bitcen.common.infraestructure.adapters.out.dto;
/**
 * HttpErrorExceptionDTO.java:
 * 
 * Objeto que contiene los datos para el mensaje de error de http. 
 *  
 * @author Marcos Gonzalez
 * @version 1.0, 13/06/2024
 * @see Documento "MAR - Marco Arquitectonico de Referencia"
 * @since JDK 17
 */


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HttpErrorExceptionDTO {
	/** Variable para el estatus de error, de la petición */
	private String status;
	/** Variable para el detalle de error, de la petición */
	private String trace;
	/** Variable para el error de error, de la petición */
	private String error;
	/** Variable para la ruta de error, de la petición */
	private String path;
	
	
}
