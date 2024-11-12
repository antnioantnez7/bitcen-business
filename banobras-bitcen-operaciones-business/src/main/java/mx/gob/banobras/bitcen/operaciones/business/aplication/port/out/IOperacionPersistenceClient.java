package mx.gob.banobras.bitcen.operaciones.business.aplication.port.out;
/**
 * IOperacionPersistenceClient.java:
 * 
 * Interface para el cliente de conexion a la capa de pesistencia de operacion.
 * 
 * @author Marcos Gonzalez
 * @version 1.0, 13/06/2024
 * @see Documento "MAR - Marco Arquitectonico de Referencia"
 * @since JDK 17
 */

import mx.gob.banobras.bitcen.operaciones.business.infraestructure.adapters.in.dto.BitacoraConsultaDTO;
import mx.gob.banobras.bitcen.operaciones.business.infraestructure.adapters.in.dto.BitacoraOperacionDTO;
import mx.gob.banobras.bitcen.operaciones.business.infraestructure.adapters.in.dto.ResponseOperacionDTO;

import mx.gob.banobras.bitcen.token.business.infraestructure.adapters.out.dto.HeaderDTO;


public interface IOperacionPersistenceClient  {
	/**
	 * Metodo para registrar movimientos en la bitacora de operaciones.
	 * 
	 * @param headerDTO Datos de entrada.
	 * @param bitacoraOperacionDTO Objeto que contiene los datos de la bitacora de operaciones
	 * @return ResponseOperacionDTO 
	 * @throws Exception
	 */
	ResponseOperacionDTO registrar(HeaderDTO headerDTO, BitacoraOperacionDTO bitacoraOperacionDTO) 
			throws Exception; 
	/**
	 * Metodo para consultar movimientos de la bitacora de operaciones.
	 * 
	 * @param headerDTO Header de entrada
	 * @param bitacoraConsultaDTO Objeto que contiene los parametros de busqueda de la bitacora de operaciones
	 * @return ResponseOperacionDTO
	 * @throws Exception
	 */
	ResponseOperacionDTO consultar(HeaderDTO headerDTO, BitacoraConsultaDTO bitacoraConsultaDTO) throws Exception;
}