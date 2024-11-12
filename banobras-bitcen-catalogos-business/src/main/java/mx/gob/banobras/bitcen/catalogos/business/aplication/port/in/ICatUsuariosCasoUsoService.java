package mx.gob.banobras.bitcen.catalogos.business.aplication.port.in;
/**
 * ICatUsuariosCasoUsoService.java:
 * 
 * Interface para el caso de uso del servicio de catalogos de usuarios.
 * 
 * @author Antonio Antunez Barbosa
 * @version 1.0, 27/09/2024
 * @see Documento "MAR - Marco Arquitectonico de Referencia"
 * @since JDK 17
 */

import mx.gob.banobras.bitcen.catalogos.business.infraestructure.adapters.in.dto.CatalogoUsuarioDTO;
import mx.gob.banobras.bitcen.catalogos.business.infraestructure.adapters.in.dto.ResponseCatUsuariosDTO;
import mx.gob.banobras.bitcen.token.business.infraestructure.adapters.out.dto.HeaderDTO;


public interface ICatUsuariosCasoUsoService {
	
	/**
	 * Metodo para guardar un usuario en el catalogo
	 * 
	 * @param headerDTO Header de entrada
	 * @param catalogoUsuarioDTO Objeto que contiene los datos del Catalogo
	 * @return ResponseCatUsuariosDTO
	 * @throws Exception
	 */
	ResponseCatUsuariosDTO guardarUsuario(HeaderDTO headerDTO, CatalogoUsuarioDTO catalogoUsuarioDTO) throws Exception;
	/**
	 * Metodo para eliminar un usuario en el catalogo
	 * 
	 * @param headerDTO Header de entrada
	 * @param identificador entero que contiene el identificador del Catalogo
	 * @return ResponseCatUsuariosDTO
	 * @throws Exception
	 */
	ResponseCatUsuariosDTO eliminarUsuario(HeaderDTO headerDTO, int identificador) throws Exception;
	/**
	 * Metodo para consultar los usuarios en el Catalogo
	 * 
	 * @param headerDTO Header de entrada
	 * @param catalogoUsuarioDTO Objeto que contiene los datos del Catalogo
	 * @return ResponseCatUsuariosDTO
	 * @throws Exception
	 */
	ResponseCatUsuariosDTO consultarUsuarios(HeaderDTO headerDTO, CatalogoUsuarioDTO catalogoUsuarioDTO) throws Exception;

}
