package mx.gob.banobras.bitcen.token.business.infraestructure.adapters.out.dto;
/**
 * HeaderDTO.java:
 * 
 * Objeto DTO que contien los datos informados en el header de la peticion.
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
public class HeaderDTO {
	/** Variable que contiene las credenciales del usuario (usuario y password) encriptados */
	private String credentials;
	/** Variable que contiene el usuario  */
	private String userName;
	/** Variable que contiene el password  */
	private String password;
	/** Variable que contiene el token de autorizacion  */
	private String tokenAuth;
	/** Variable que contiene el nombre del apiicativo */
	private String appName;
	/** Variable que contine el nombre de la capa o aplicativo */
	private String consumerId;
	/** Variable que contiene la funcionlidad del aplicativo  */
	private String functionalId;
	/** Variable que contiene el id de transaccion del aplicativo  */
	private String transactionId;
	/** Variable que contiene el refresh token  */
	private String timeRefreshToken;

}
