package mx.gob.banobras.bitcen.usuarios.business.aplication.port.in;
/**
 * IUsuarioCasoUsoService.java:
 * 
 * Interface para el caso de uso del servicio de usuarios.
 * 
 * @author Marcos Gonzalez
 * @version 1.0, 13/06/2024
 * @see Documento "MAR - Marco Arquitectonico de Referencia"
 * @since JDK 17
 */

import mx.gob.banobras.bitcen.token.business.infraestructure.adapters.out.dto.HeaderDTO;
import mx.gob.banobras.bitcen.usuarios.business.infraestructure.adapters.in.dto.BitacoraConsultaDTO;
import mx.gob.banobras.bitcen.usuarios.business.infraestructure.adapters.in.dto.BitacoraUsuarioDTO;
import mx.gob.banobras.bitcen.usuarios.business.infraestructure.adapters.in.dto.ResponseUsuarioDTO;

public interface IUsuarioCasoUsoService {
	/**
	 * Metodo para registrar movimientos en la bitacora de usuarios.
	 * 
	 * @param headerDTO Header de entrada
	 * @param bitacoraUsuarioDTO Objeto que contiene los datos de la bitacora de usuarios
	 * @return ResponseUsuarioDTO
	 * @throws Exception
	 */
	ResponseUsuarioDTO registrar(HeaderDTO headerDTO, BitacoraUsuarioDTO bitacoraUsuarioDTO) throws Exception;
	/**
	 * Metodo para consultar movimientos de la bitacora de usuarios.
	 * 
	 * @param headerDTO Header de entrada
	 * @param bitacoraConsultaDTO Objeto que contiene los parametros de busqueda de la bitacora de usuarios
	 * @return ResponseUsuarioDTO
	 * @throws Exception
	 */
	ResponseUsuarioDTO consultar(HeaderDTO headerDTO, BitacoraConsultaDTO bitacoraConsultaDTO) throws Exception;
}
