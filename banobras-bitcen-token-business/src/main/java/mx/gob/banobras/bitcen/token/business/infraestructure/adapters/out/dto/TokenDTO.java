package mx.gob.banobras.bitcen.token.business.infraestructure.adapters.out.dto;

/**
 * TokenDTO.java:
 * 
 * Objeto que contiene los datos del Token.. 
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
public class TokenDTO {
	/** Variable que contiene el resultado de validacion del token */
	public boolean valid;
	/** Variable que contiene el tipo de gerneración del token, RSA o por keySecret */
	public String type;
	/** Variable que contiene el token */
	public String token;
	/** Variable que contiene el refresh-token */
	public String refreshToken;
	
	
	
}
