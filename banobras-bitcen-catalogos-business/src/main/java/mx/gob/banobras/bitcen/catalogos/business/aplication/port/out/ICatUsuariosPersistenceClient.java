package mx.gob.banobras.bitcen.catalogos.business.aplication.port.out;
import mx.gob.banobras.bitcen.catalogos.business.infraestructure.adapters.in.dto.CatalogoUsuarioDTO;
import mx.gob.banobras.bitcen.catalogos.business.infraestructure.adapters.in.dto.ResponseCatUsuariosDTO;
/**
 * ICatAplicativoPersistenceClient.java:
 * 
 * Interface para el cliente de conexion a la capa de pesistencia de catalogo.
 * 
 * @author Marcos Gonzalez
 * @version 1.0, 27/09/2024
 * @see Documento "MAR - Marco Arquitectonico de Referencia"
 * @since JDK 17
 */
import mx.gob.banobras.bitcen.token.business.infraestructure.adapters.out.dto.HeaderDTO;


public interface ICatUsuariosPersistenceClient  {

	/**
	 * Metodo para guardar un usuario en el catalogo
	 * 
	 * @param headerDTO Datos de entrada.
	 * @param catalogoUsuarioDTO Objeto que contiene los datos del Catalogo
	 * @return ResponseCatUsuariosDTO 
	 * @throws Exception
	 */
	ResponseCatUsuariosDTO guardarUsuario(HeaderDTO headerDTO, CatalogoUsuarioDTO catalogoUsuarioDTO) 
			throws Exception; 
	/**
	 * Metodo para eliminar un usuario  del catalogo
	 * 
	 * @param headerDTO Header de entrada
	 * @param identificador entero que contiene el identificador del Catalogo
	 * @return ResponseCatUsuariosDTO
	 * @throws Exception
	 */
	ResponseCatUsuariosDTO eliminarUsuario(HeaderDTO headerDTO, int identificador) throws Exception;
	/**
	 * Metodo para consultar los usuarios en el catalogo
	 * 
	 * @param headerDTO Datos de entrada.
	 * @param catalogoUsuarioDTO Objeto que contiene los datos del Catalogo
	 * @return ResponseCatUsuariosDTO 
	 * @throws Exception
	 */
	ResponseCatUsuariosDTO consultarUsuarios(HeaderDTO headerDTO, CatalogoUsuarioDTO catalogoUsuarioDTO) 
			throws Exception; 
	
}