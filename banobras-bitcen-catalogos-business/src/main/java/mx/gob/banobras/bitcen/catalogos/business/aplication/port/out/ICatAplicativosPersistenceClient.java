package mx.gob.banobras.bitcen.catalogos.business.aplication.port.out;
import mx.gob.banobras.bitcen.catalogos.business.infraestructure.adapters.in.dto.CatalogoAplicativoDTO;
import mx.gob.banobras.bitcen.catalogos.business.infraestructure.adapters.in.dto.ResponseCatAplicativosDTO;
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


public interface ICatAplicativosPersistenceClient  {

	/**
	 * Metodo para guardar un aplicativo en el catalogo
	 * 
	 * @param headerDTO Datos de entrada.
	 * @param catalogoAplicativoDTO Objeto que contiene los datos del Catalogo
	 * @return ResponseCatAplicativosDTO 
	 * @throws Exception
	 */
	ResponseCatAplicativosDTO registrarAplicativo(HeaderDTO headerDTO, CatalogoAplicativoDTO catalogoAplicativoDTO) 
			throws Exception; 
	/**
	 * Metodo para actualizar un aplicativo en el catalogo
	 * 
	 * @param headerDTO Datos de entrada.
	 * @param catalogoAplicativoDTO Objeto que contiene los datos del Catalogo
	 * @return ResponseCatAplicativosDTO 
	 * @throws Exception
	 */
	ResponseCatAplicativosDTO actualizarAplicativo(HeaderDTO headerDTO, CatalogoAplicativoDTO catalogoAplicativoDTO) 
			throws Exception; 
	/**
	 * Metodo para eliminar un aplicativo  del catalogo
	 * 
	 * @param headerDTO Header de entrada
	 * @param aplicativoId cadena que contiene el identificador del Catalogo
	 * @return ResponseCatalogoDTO
	 * @throws Exception
	 */
	ResponseCatAplicativosDTO eliminarAplicativo(HeaderDTO headerDTO, String aplicativoId) throws Exception;
	/**
	 * Metodo para consultar los aplicativos en el catalogo
	 * 
	 * @param headerDTO Datos de entrada.
	 * @return ResponseCatAplicativosDTO 
	 * @throws Exception
	 */
	ResponseCatAplicativosDTO consultarAplicativos(HeaderDTO headerDTO) 
			throws Exception; 
	
}