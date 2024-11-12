package mx.gob.banobras.bitcen.token.business.aplication.port.out;
/**
 * ITokenClient.java:
 * 
 * Interfaz para consumir los servicios del Api Tokenizer
 * 
 * @author Marcos Gonzalez
 * @version 1.0, 13/06/2024
 * @see Documento "MAR - Marco Arquitectonico de Referencia"
 * @since JDK 17
 */


import mx.gob.banobras.bitcen.token.business.infraestructure.adapters.out.dto.HeaderDTO;
import mx.gob.banobras.bitcen.token.business.infraestructure.adapters.out.dto.TokenizerResponseDTO;

public interface ITokenClient {
	
	
	/**
	 * Metodo para validar el token.
	 * 
	 * @param securityAuthDTO componente que conciten los datos del token.
	 * @return TokenizerResponseDTO objeto que contiene los datos de validacion del token. 
	 * 
	 * @throws Exception .
	 */
	public TokenizerResponseDTO validToken(HeaderDTO headerDTO) throws Exception;
	
	
}
