package mx.gob.banobras.bitcen.token.business.infraestructure.adapters.out.dto;
/**
 * TokenizerResponseDTO.java:
 * 
 * Objeto que contiene los datos de respuesta de generacion del Token. 
 *  
 * @author Marcos Gonzalez
 * @version 1.0, 13/06/2024
 * @see Documento "MAR - Marco Arquitectonico de Referencia"
 * @since JDK 17
 */
import lombok.Data;
import mx.gob.banobras.bitcen.common.infraestructure.adapters.out.dto.ErrorMessageDTO;

@Data
public class TokenizerResponseDTO {
	/** Variable que contiene el status de la peticion. */
	private Integer statusCode;
	/** Variable que contiene los datos del token. */
	private TokenDTO tokenDTO;
	/** Variable que contiene los datos del mensaje de error. */
	private ErrorMessageDTO errorMessageDTO;
}


